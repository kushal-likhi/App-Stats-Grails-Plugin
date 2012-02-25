package org.devunited.grails.plugin.appstats

import grails.converters.JSON
import org.springframework.web.context.request.RequestContextHolder
import javax.servlet.http.HttpSession

class ProfilerService {

    static transactional = false

    public void submitRequestForProfiling(request) {
        if (!request.AS_RecordId) {
            request.AS_RecordId = new RequestLog(
                    userAgent: request.getHeader("user-agent"),
                    clientIP: request.getRemoteAddr(),
                    URL: request.forwardURI,
                    action: request.AS_hops.get(0).action,
                    controller: request.AS_hops.get(0).controller,
                    timeStart: request.AS_hops.get(0).startTime,
                    timeEnd: System.currentTimeMillis(),
                    hopsJSON: generateHopsJSON(request.AS_hops),
                    initialParams: request.AS_params
            ).save(flush: true, failOnError: true).id
        } else {
            RequestLog requestLog = RequestLog.get(request.AS_RecordId.toLong())
            requestLog.hopsJSON = generateHopsJSON(request.AS_hops)
            requestLog.timeEnd = System.currentTimeMillis()
            requestLog.save(flush: true)
        }
        setSessionForVisitsOfAPerson(request.getRemoteAddr())
    }


    public void setSessionForVisitsOfAPerson(String clientIP) {
        HttpSession session = getSession()
        if (session.userVisit == null) {
            session.userVisit = clientIP
            UserVisit userVisit = UserVisit.findByClientIP(clientIP)
            if (userVisit) {
                userVisit.visitCount = userVisit.visitCount + 1
                userVisit.save(flush: true)
            } else {
                userVisit = new UserVisit()
                userVisit.clientIP = clientIP
                userVisit.visitCount = 1
                userVisit.save(flush: true)
            }
        }
    }

    public void registerRequestToProfiler(request, String controllerName, String actionName, Map params) {
        if (request.isStatsProfiled) {
            request.AS_hops.add([
                    controller: controllerName,
                    action: actionName ?: 'defaultAction',
                    startTime: System.currentTimeMillis()
            ])
        } else {
            request.isStatsProfiled = true
            request.AS_RecordId = false
            request.AS_hops = [[
                    controller: controllerName,
                    action: actionName ?: 'defaultAction',
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

    private HttpSession getSession() {
        return RequestContextHolder.currentRequestAttributes().getSession()
    }

}

