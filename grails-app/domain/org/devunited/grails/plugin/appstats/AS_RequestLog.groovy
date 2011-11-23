package org.devunited.grails.plugin.appstats

class AS_RequestLog {

    Date dateCreated

    String userAgent
    String clientIP
    String URL
    String action
    String controller
    String hopsJSON

    Long timeStart
    Long timeEnd

    static constraints = {

    }


}
