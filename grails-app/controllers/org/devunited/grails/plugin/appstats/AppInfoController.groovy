package org.devunited.grails.plugin.appstats


class AppInfoController {


    def serverInformationService
    def appStatsService

    static defaultAction = "index"

    def index = {
        if (!params.month) params.month = new Date().getMonth()
        if (!params.year) params.year = new Date().getYear()
        appStatsService.initialize(params)
        render view: 'index', model: [
                applicationStats: appStatsService.summary(grailsApplication.controllerClasses as List),
                totalVisitorStats: appStatsService.totalVisitorStats(grailsApplication.controllerClasses as List),
                cpuInfo: serverInformationService.getCPUInfo(),
                memInfo: serverInformationService.getMemoryInfo(),
                serverInformation: serverInformationService.getServerInformation(),
                workingDir: new File(".").getCanonicalPath(),
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
