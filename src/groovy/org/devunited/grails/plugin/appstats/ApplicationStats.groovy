package org.devunited.grails.plugin.appstats

class ApplicationStats {
    Map<String, Long> controllerHits
    Map<String, Map> controllerActionHits
    Map<String, Map> controllerAndActionForUniqueVisitor
    Map<String, Map> averageTimeOnParticularAction
}
