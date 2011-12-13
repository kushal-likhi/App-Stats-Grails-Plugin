package org.devunited.grails.plugin.appstats

import org.springframework.beans.BeanWrapper
import org.springframework.beans.PropertyAccessorFactory
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

class AppStatsController {
    def appStatsService

    def index = {
        List<DefaultGrailsControllerClass> controllers = grailsApplication.controllerClasses
        Map<String, List> controllerWithTheirActions = appStatsService.getAllControllersWithTheirActions(controllers)
        Date today = new Date()
        List<RequestLog> requestLogs = RequestLog.findAllByDateCreatedBetween(today - 1, today)
        Map<String, Long> controllerHits = appStatsService.getControllersHitCount(controllers, requestLogs)
        Map<String, Map> controllerActionHits = appStatsService.getControllersActionHitCount(controllerWithTheirActions, requestLogs)
        Map<String, Map> controllerAndActionForUniqueVisitor = appStatsService.getControllerAndActionHitsOfAVisitor(controllerWithTheirActions, requestLogs)
        Map<String, Map> averageTimeOnParticularAction = appStatsService.AverageTimeOnAParticularAction(controllerWithTheirActions, requestLogs)
        render "<br/>.......................Controller hits.............................................<br/>"
        render controllerHits
        render "<br/>......................Action hits..............................................<br/>"
        render controllerActionHits
        render "<br/>......................Unique visitors..............................................<br/>"
        render controllerAndActionForUniqueVisitor
        render "<br/>........................Average Time............................................<br/>"
        render averageTimeOnParticularAction
        render "<br/>..........................Bye..........................................<br/>"

    }

}
