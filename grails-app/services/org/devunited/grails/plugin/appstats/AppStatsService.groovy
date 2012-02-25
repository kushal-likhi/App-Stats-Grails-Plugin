package org.devunited.grails.plugin.appstats

import org.springframework.beans.BeanWrapper
import org.springframework.beans.PropertyAccessorFactory
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

class AppStatsService {

    static scope = "session"
    static proxy = true
    static transactional = false


    private List<RequestLog> requestLogList

    private Integer reportMonth
    private Integer reportYear
    private String md5 = ""

    public String controllerName   //TODO remove

    Long hitCount = 0     //todo remove

    public List<RequestLog> getRequestLogs() {
        if (!requestLogList || (md5 != ("${reportMonth}${reportYear}".encodeAsMD5()))) {
            Date startDate, endDate
            (startDate, endDate) = getStartAndEndDateOfAMonth();
            requestLogList = RequestLog.findAllByDateCreatedBetween(startDate, endDate)
        }
        md5 = "${reportMonth}${reportYear}".encodeAsMD5()
        requestLogList
    }

    public initialize(params) {
        reportMonth = params.month
        reportYear = params.year
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
        hitCount = 0
        Map<String, Long> controllerHits = [:]
        controllers.each {controller ->
            controllerName = StringUtil.toLowerCaseFirstCharacter(controller.name)
            hitCount = requestLogs.grep {it.controller.equals(controllerName)}?.size()
            controllerHits[controller.name] = hitCount
        }
        return controllerHits
    }

    Map<String, Map> getControllersActionHitCount(Map<String, List> controllerWithTheirActions) {
        hitCount = 0
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
        hitCount = 0
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

    VisitorStats totalVisitorStats(List<DefaultGrailsControllerClass> controllers) {
        VisitorStats totalVisitorStats = new VisitorStats()
        totalVisitorStats.uniqueVisitorsCount = uniqueVisitors().size()
        totalVisitorStats.totalHits = totalHits(getControllersHitCount(controllers))
        totalVisitorStats.noOfVisits = noOfVisits()
        return totalVisitorStats
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
        return requestLogs.grep {it.clientIP}.clientIP.unique()
    }

    Integer totalHits(Map<String, Long> controllerHits) {
        hitCount = 0
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
        hitCount = 0
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

}
