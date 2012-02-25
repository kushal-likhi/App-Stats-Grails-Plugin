package org.devunited.grails.plugin.appstats

import org.springframework.beans.BeanWrapper
import org.springframework.beans.PropertyAccessorFactory
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass
import com.sun.xml.internal.bind.v2.TODO

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
        if (!requestLogList || (md5 != ("${reportMonth}${reportYear}".encodeAsMD5()))) requestLogList = RequestLog.findByDateCreatedBetween()//Todo Implement
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

    ApplicationStats summary(List<DefaultGrailsControllerClass> controllers, List<RequestLog> requestLogs) {
        ApplicationStats applicationStats = new ApplicationStats()
        Map<String, List> controllerWithTheirActions = getAllControllersWithTheirActions(controllers)
        applicationStats.controllerActionHits = getControllersActionHitCount(controllerWithTheirActions, requestLogs)
        applicationStats.controllerAndActionForUniqueVisitor = getControllerAndActionHitsOfAVisitor(controllerWithTheirActions, requestLogs)
        applicationStats.averageTimeOnParticularAction = averageTimeOnAParticularAction(controllerWithTheirActions, requestLogs)
        applicationStats.controllerHits = getControllersHitCount(controllers, requestLogs)
        return applicationStats
    }

    Map<String, Long> getControllersHitCount(List<DefaultGrailsControllerClass> controllers, List<RequestLog> requestLogs) {
        hitCount = 0
        Map<String, Long> controllerHits = [:]
        controllers.each {controller ->
            controllerName = StringUtil.toLowerCaseFirstCharacter(controller.name)
            hitCount = requestLogs.grep {it.controller.equals(controllerName)}?.size()
            controllerHits[controller.name] = hitCount
        }
        return controllerHits
    }

    Map<String, Map> getControllersActionHitCount(Map<String, List> controllerWithTheirActions, List<RequestLog> requestLogs) {
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

    Map<String, Map> getControllerAndActionHitsOfAVisitor(Map<String, List> controllerWithTheirActions, List<RequestLog> requestLogs) {
        hitCount = 0
        Map<String, Map> controllerAndActionForUniqueVisitor = [:]
        Map<String, Map> uniqueVisitorHits = [:]
        Map<String, Long> actionWithHitCountForUniqueVisitors = [:]
        List<String> uniqueIPs = uniqueVisitors(requestLogs);
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

    VisitorStats totalVisitorStats(List<RequestLog> requestLogs, Map<String, Long> controllerHits) {
        VisitorStats totalVisitorStats = new VisitorStats()
        totalVisitorStats.uniqueVisitorsCount = uniqueVisitors(requestLogs).size()
        totalVisitorStats.totalHits = totalHits(controllerHits)
        println "..........................................."
        println noOfVisits()
        totalVisitorStats.noOfVisits = noOfVisits()
        return totalVisitorStats
    }

    Map<String, VisitorStats> forEveryMonthVisitorStats() {
        Date date = new Date()

    }

    VisitorStats monthlyVisitorStats(List<RequestLog> requestLogs, Map<String, Long> controllerHits) {
        VisitorStats totalVisitorStats = new VisitorStats()
        totalVisitorStats.uniqueVisitorsCount = uniqueVisitors(requestLogs).size()
        totalVisitorStats.totalHits = totalHits(controllerHits)
        /*totalVisitorStats.noOfVisits = noOfVisits()*/
        return totalVisitorStats
    }


    List<String> uniqueVisitors(List<RequestLog> requestLogs) {
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

    Map<String, Map> averageTimeOnAParticularAction(Map<String, List> controllerWithTheirActions, List<RequestLog> requestLogs) {
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
}
