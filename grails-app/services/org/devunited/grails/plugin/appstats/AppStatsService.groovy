package org.devunited.grails.plugin.appstats

import org.springframework.beans.BeanWrapper
import org.springframework.beans.PropertyAccessorFactory
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass
import grails.converters.JSON

class AppStatsService {

    static scope = "session"
    static proxy = true
    static transactional = false


    private List<RequestLog> requestLogList

    private Integer reportMonth
    private Integer reportYear
    private String md5 = ""

    public List<RequestLog> getRequestLogs() {
        Date startDate, endDate
        (startDate, endDate) = getStartAndEndDateOfAMonth();
        if (!requestLogList || (md5 != ("${reportMonth}${reportYear}".encodeAsMD5()))) {
            requestLogList = RequestLog.findAllByDateCreatedBetween(startDate, endDate)
        }
        if (RequestLog.countByDateCreatedBetween(startDate, endDate) != requestLogList.size()) requestLogList = RequestLog.findAllByDateCreatedBetween(startDate, endDate)
        md5 = "${reportMonth}${reportYear}".encodeAsMD5()
        requestLogList
    }

    public initialize(params) {
        if (!params.date_month) {
            reportMonth = new Date().getMonth()
        } else {
            reportMonth = params.int('date_month') - 1
        }
        if (!params.date_year) {
            reportYear = new Date().getYear()
        } else {
            reportYear = params.int('date_year') - 1900
        }
    }

    Map<String, List> getAllControllersWithTheirActions(List<DefaultGrailsControllerClass> controllers) {
        List allActions = []
        Map<String, List> controllerWithTheirActions = [:]
        controllers.each {controllerClass ->
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(controllerClass.newInstance())
            for (descriptor in beanWrapper.propertyDescriptors) {
                String closureClassName = controllerClass.getPropertyOrStaticPropertyOrFieldValue(descriptor.name, Closure)?.class?.name
                if (closureClassName) {
                    allActions << descriptor.name
                }
            }
            controllerWithTheirActions[controllerClass.name] = allActions
            allActions = []
        }
        return controllerWithTheirActions
    }

    ApplicationStats summary(List<DefaultGrailsControllerClass> controllers) {
        ApplicationStats applicationStats = new ApplicationStats()
        Map<String, List> controllerWithTheirActions = getAllControllersWithTheirActions(controllers)
        applicationStats.controllerActionHits = getControllersActionHitCount(controllerWithTheirActions)
        applicationStats.controllerAndActionForUniqueVisitor = getControllerAndActionHitsOfAVisitor(controllerWithTheirActions)
        applicationStats.averageTimeOnParticularAction = averageTimeOnAParticularAction(controllerWithTheirActions)
        applicationStats.controllerHits = getControllersHitCount(controllers)
        return applicationStats
    }

    Map<String, Long> getControllersHitCount(List<DefaultGrailsControllerClass> controllers) {
        Long hitCount = 0
        String controllerName
        Map<String, Long> controllerHits = [:]
        controllers.each {controller ->
            controllerName = StringUtil.toLowerCaseFirstCharacter(controller.name)
            hitCount = requestLogs.grep {it.controller.equals(controllerName)}?.size()
            controllerHits[controller.name] = hitCount
        }
        return controllerHits
    }

    Map<String, Map> getControllersActionHitCount(Map<String, List> controllerWithTheirActions) {
        Long hitCount = 0
        String controllerName
        Map<String, Map> controllerActionHits = [:]
        Map<String, Integer> actionHits = [:]
        controllerWithTheirActions.each {controller, actions ->
            controllerName = StringUtil.toLowerCaseFirstCharacter(controller)
            actions.each {actionName ->
                hitCount = requestLogs.grep {it.controller.equals(controllerName) && it.action.equals(actionName)}?.size()
                actionHits[actionName] = hitCount
            }
            controllerActionHits[controller] = actionHits
            actionHits = [:]
        }
        return controllerActionHits
    }

    Map<String, Map> getControllerAndActionHitsOfAVisitor(Map<String, List> controllerWithTheirActions) {
        Long hitCount = 0
        String controllerName
        Map<String, Map> controllerAndActionForUniqueVisitor = [:]
        Map<String, Map> uniqueVisitorHits = [:]
        Map<String, Long> actionWithHitCountForUniqueVisitors = [:]
        List<String> uniqueIPs = uniqueVisitors();
        uniqueIPs.each {ip ->
            controllerWithTheirActions.each {controller, actions ->
                controllerName = StringUtil.toLowerCaseFirstCharacter(controller)
                actions.each {actionName ->
                    hitCount = requestLogs.grep {it.controller.equals(controllerName) && it.action.equals(actionName) && it.clientIP.equals(ip)}?.size()
                    actionWithHitCountForUniqueVisitors[actionName] = hitCount
                }
                controllerAndActionForUniqueVisitor[controller] = actionWithHitCountForUniqueVisitors
                actionWithHitCountForUniqueVisitors = [:]
            }
            uniqueVisitorHits[ip] = controllerAndActionForUniqueVisitor
            controllerAndActionForUniqueVisitor = [:]
        }
        return uniqueVisitorHits
    }

    Map<String, VisitorStats> forEveryMonthVisitorStats() {
        Date date = new Date()

    }

    VisitorStats monthlyVisitorStats(Map<String, Long> controllerHits) {
        VisitorStats totalVisitorStats = new VisitorStats()
        totalVisitorStats.uniqueVisitorsCount = uniqueVisitors().size()
        totalVisitorStats.totalHits = totalHits(controllerHits)
        /*totalVisitorStats.noOfVisits = noOfVisits()*/
        return totalVisitorStats
    }


    List<String> uniqueVisitors() {
        return requestLogs?.groupBy {it.clientIP}?.keySet()?.toList()
    }

    Integer totalHits(Map<String, Long> controllerHits) {
        Long hitCount = 0
        controllerHits.each {
            hitCount = hitCount + it.value
        }
        return hitCount
    }

    Integer noOfVisits() {
        List<String> uniqueClientSessionIds = RequestLog.createCriteria().list {
            projections {
                groupProperty("sessionId")
            }
        }
        return uniqueClientSessionIds ? uniqueClientSessionIds.size() : 0
    }

    Map<String, Map> averageTimeOnAParticularAction(Map<String, List> controllerWithTheirActions) {
        Double averageTime = 0
        Long hitCount = 0
        String controllerName
        Map<String, Map> averageTimeOnParticularAction = [:]
        Map<String, Double> controllerActionTime = [:]
        List<RequestLog> filteredRequestLog = []
        controllerWithTheirActions.each {controller, actions ->
            controllerName = StringUtil.toLowerCaseFirstCharacter(controller)
            actions.each {actionName ->
                filteredRequestLog = requestLogs.grep {it.controller.equals(controllerName) && it.action.equals(actionName)}
                filteredRequestLog?.each {
                    hitCount = it.timeEnd - it.timeStart
                    averageTime = hitCount + averageTime
                }
                controllerActionTime[actionName] = averageTime / filteredRequestLog.size()
            }
            averageTimeOnParticularAction[controller] = controllerActionTime
            controllerActionTime = [:]
        }
        return averageTimeOnParticularAction
    }

    List<Date> getStartAndEndDateOfAMonth() {
        Date endDate = new Date(reportYear, reportMonth, 31)
        [
                new Date(reportYear, reportMonth, 1),
                endDate.getDate() < 5 ? endDate - endDate.getDate() : endDate
        ]
    }

    VisitorStats totalVisitorStats(List<DefaultGrailsControllerClass> controllers) {
        VisitorStats totalVisitorStats = new VisitorStats()
        totalVisitorStats.uniqueVisitorsCount = uniqueVisitors().size()
        totalVisitorStats.totalHits = totalHits(getControllersHitCount(controllers))
        totalVisitorStats.noOfVisits = noOfVisits()
        return totalVisitorStats
    }

    List<PageInfo> getAllPagesInformation() {
        List<PageInfo> pageInfoList = []
        requestLogs.groupBy {it.URL}.each {String url, List<RequestLog> logs ->
            PageInfo pageInfo = new PageInfo()
            pageInfo.pageURL = url
            pageInfo.noOfVisits = logs.clone().unique {it.sessionId}.size()
            pageInfo.uniqueVisitor = logs.clone().unique {it.clientIP}.size()
            pageInfo.totalHits = logs.size()
            pageInfoList.add(pageInfo)
        }
        return pageInfoList
    }

    List<PageInfo> getAllControllerInformation() {
        List<PageInfo> pageInfoList = []
        requestLogs.groupBy {it.controller}.each {String controller, List<RequestLog> logs ->
            PageInfo pageInfo = new PageInfo()
            pageInfo.pageURL = controller
            pageInfo.noOfVisits = logs.clone().unique {it.sessionId}.size()
            pageInfo.uniqueVisitor = logs.clone().unique {it.clientIP}.size()
            pageInfo.totalHits = logs.size()
            pageInfoList.add(pageInfo)
        }
        return pageInfoList
    }

    List<PageInfo> getAllControllerAndActionInformation() {
        List<PageInfo> pageInfoList = []
        requestLogs.groupBy {"Controller: ${it.controller}, Action: ${it.action}"}.each {String controllerAndAction, List<RequestLog> logs ->

            List hops = logs.collect {
                def json = JSON.parse(it.hopsJSON)
                (json.last().exitTime.toLong()) - (json.first().entryTime.toLong())
            }
            PageInfo pageInfo = new PageInfo()
            pageInfo.maxTime = hops.max() as Long
            pageInfo.minTime = hops.min() as Long
            pageInfo.avgTime = (hops.sum() / hops.size())
            pageInfo.pageURL = controllerAndAction
            pageInfo.noOfVisits = logs.clone().unique {it.sessionId}.size()
            pageInfo.uniqueVisitor = logs.clone().unique {it.clientIP}.size()
            pageInfo.totalHits = logs.size()
            pageInfoList.add(pageInfo)
        }
        return pageInfoList
    }

}
