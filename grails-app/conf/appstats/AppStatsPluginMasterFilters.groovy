package appstats

class AppStatsPluginMasterFilters {

    def profilerService

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                profilerService.registerRequestToProfiler(
                        request,
                        controllerName,
                        actionName,
                        params
                )
            }
            after = {
                profilerService.registerRequestToProfiler(
                        request,
                        controllerName,
                        actionName,
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
