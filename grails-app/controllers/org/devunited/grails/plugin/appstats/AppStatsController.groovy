package org.devunited.grails.plugin.appstats

class AppStatsController {

    def index = {
        render "ok"
        redirect(action: 'hop')
    }

    def hop = {
        render "hopped"
        AS_RequestLog.list().each {AS_RequestLog rl ->
            render "<br>${rl.hopsJSON}"
        }
    }

    def test = {

    }

}
