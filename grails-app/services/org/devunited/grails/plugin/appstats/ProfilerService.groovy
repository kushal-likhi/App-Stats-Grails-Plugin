package org.devunited.grails.plugin.appstats

import grails.converters.JSON

class ProfilerService {

    static transactional = false

    public void submitRequestForProfiling(request) {
        new AS_RequestLog(
                userAgent: request.getHeader("user-agent"),
                clientIP: request.getRemoteAddr(),
                URL: request.getRequestURL().toString().replaceAll(/.dispatch$/, ''),
                action: request.AS_hops.get(0).action,
                controller: request.AS_hops.get(0).controller,
                timeStart: request.AS_hops.get(0).startTime,
                timeEnd: System.currentTimeMillis(),
                hopsJSON: generateHopsJSON(request.AS_hops)
        ).save(flush: true)
    }

    public void registerRequestToProfiler(request, String controllerName, String actionName, Map params) {
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

    private String generateHopsJSON(List hops) {
        List json = []
        boolean isEntry = true
        hops.each {hop ->
            if (isEntry) {
                json.add(
                        [
                                controller: hop.controller,
                                action: hop.action,
                                entryTime: hop.startTime,
                                exitTime: 0
                        ]
                )
            } else {
                json.last().exitTime = hop.startTime
            }
            isEntry = !isEntry
        }
        return (json as JSON)
    }
}
