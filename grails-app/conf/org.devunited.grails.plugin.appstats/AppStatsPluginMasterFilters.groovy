package org.devunited.grails.plugin.appstats

class AppStatsPluginMasterFilters {

    def profilerService

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                def controllerClass = grailsApplication.controllerClasses.find {it.logicalPropertyName == controllerName}
                String currentAction = actionName ?: controllerClass.defaultActionName
                profilerService.registerRequestToProfiler(
                        request,
                        controllerName,
                        currentAction,
                        params
                )
            }
            after = {
                def controllerClass = grailsApplication.controllerClasses.find {it.logicalPropertyName == controllerName}
                String currentAction = actionName ?: controllerClass.defaultActionName
                profilerService.registerRequestToProfiler(
                        request,
                        controllerName,
                        currentAction,
                        params
                )
            }
            afterView = {
                profilerService.submitRequestForProfiling(
                        request
                )
            }
        }
    }

}
