package org.devunited.grails.plugin.appstats

class ProfilerService {

    static transactional = false

    public void submitRequestForProfiling(request) {
       println request.AS_hops
    }

    def registerRequestToProfiler(request, String controllerName, String actionName, Map params) {
        if (request.AS_isProfiled) {
            request.AS_hops.add([
                    controller: controllerName,
                    action: actionName,
                    startTime: System.currentTimeMillis()
            ])
        } else {
            request.AS_isProfiled = true
            request.AS_hops = [[
                    controller: controllerName,
                    action: actionName,
                    startTime: System.currentTimeMillis()
            ]]
            request.AS_params = params
        }
    }
}
