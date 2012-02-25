package org.devunited.grails.plugin.appstats

class RequestLog {

    Date dateCreated

    String userAgent
    String clientIP
    String URL
    String action
    String controller
    String hopsJSON
    Map initialParams
    String sessionId

    Long timeStart
    Long timeEnd

    static constraints = {
        hopsJSON(size: 0..10000)
    }


}
