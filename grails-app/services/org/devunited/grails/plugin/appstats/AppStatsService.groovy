package org.devunited.grails.plugin.appstats

import org.springframework.beans.BeanWrapper
import org.springframework.beans.PropertyAccessorFactory
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

class AppStatsService {

    static transactional = false
    public String controllerName
    Integer hitCount = 0

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

    Map<String, Long> getControllersHitCount(List<DefaultGrailsControllerClass> controllers, List<RequestLog> requestLogs) {
        Map<String, Long> controllerHits = [:]
        controllers.each {controller ->
            controllerName = StringUtil.toLowerCaseFirstCharacter(controller.name)
            hitCount = requestLogs.grep {it.controller.equals(controllerName)}?.size()
            controllerHits[controller.name] = hitCount
        }
        return controllerHits
    }

    Map<String, Map> getControllersActionHitCount(Map<String, List> controllerWithTheirActions, List<RequestLog> requestLogs) {
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
        Map<String, Map> controllerAndActionForUniqueVisitor = [:]
        Map<String, Map> uniqueVisitorHits = [:]
        Map<String, Long> actionWithHitCountForUniqueVisitors = [:]
        List<String> uniqueIPs = requestLogs.grep {it.clientIP}.clientIP.unique()
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


    Map<String, Map> AverageTimeOnAParticularAction(Map<String, List> controllerWithTheirActions, List<RequestLog> requestLogs) {
        Integer averageTime = 0
        Map<String, Map> averageTimeOnParticularAction = [:]
        Map<String, Integer> controllerActionTime = [:]
        List<RequestLog> filteredRequestLog = []
        controllerWithTheirActions.each {controller, actions ->
            controllerName = StringUtil.toLowerCaseFirstCharacter(controller)
            actions.each {actionName ->
                filteredRequestLog = requestLogs.grep {it.controller.equals(controllerName) && it.action.equals(actionName)}
                filteredRequestLog?.each {
                    hitCount = it.timeEnd - it.timeStart
                    averageTime = hitCount + averageTime
                }
                controllerActionTime[actionName] = averageTime
            }
            averageTimeOnParticularAction[controller] = controllerActionTime
            controllerActionTime = [:]
        }
        return averageTimeOnParticularAction
    }

}
