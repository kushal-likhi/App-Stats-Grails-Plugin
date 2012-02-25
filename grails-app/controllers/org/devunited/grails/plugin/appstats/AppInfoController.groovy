package org.devunited.grails.plugin.appstats


class AppInfoController {


    def serverInformationService
    def appStatsService

    static defaultAction = "index"

    def index = {
        if (!params.month) params.month = new Date().getMonth()
        if (!params.month) params.month = new Date().getYear()
        appStatsService.initialize(params)
        render view: 'index', model: [
                applicationStats: appStatsService.summary(grailsApplication.controllerClasses, RequestLog.list()),
                cpuInfo: serverInformationService.getCPUInfo(),
                memInfo: serverInformationService.getMemoryInfo(),
                serverInformation: serverInformationService.getServerInformation(),
                workingDir: new File(".").getCanonicalPath(),
                totalVisitorStats: appStatsService.totalVisitorStats(RequestLog.list(), appStatsService.summary(grailsApplication.controllerClasses, RequestLog.list()).controllerHits),
                ipInfo: serverInformationService.getIPAddressAndHostName()
        ]
    }

    def commandResult = {
        String output = serverInformationService.executeCommand(params.command)
        output.eachLine {
            render "${it}<br/>"
        }
    }

    def abc1 = {
        redirect(action: "cde1")
    }
    def cde1 = {
        render "hi"
    }
    def abc2 = {
        forward(action: "cde2")
    }
    def cde2 = {
        session.invalidate()
    }

    def test = {
        List<Integer> ls = RequestLog.getAll().hopsJSON*.size().sort()
        RequestLog requestLog = RequestLog.createCriteria().get {
            sizeGe("hopsJSON", 206)
        }
    }


}
