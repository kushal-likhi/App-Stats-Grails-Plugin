package org.devunited.grails.plugin.appstats

import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

class AppStatsController {
    def serverInformationService
    def consoleService
    def appStatsService


    def index = {
        IPInfo ipInfo = serverInformationService.getIPAddressAndHostName()
        List<List> cpuInfo = serverInformationService.getCPUInfo()
        List memInfo = serverInformationService.getMemoryInfo()
        ServerInformation serverInformation = serverInformationService.getServerInformation()
        String presentWorkingDir = new File(".").getCanonicalPath();
        List<DefaultGrailsControllerClass> controllers = grailsApplication.controllerClasses
        List<RequestLog> requestLogs = RequestLog.list()
        ApplicationStats applicationStats = appStatsService.summary(controllers, requestLogs)
        VisitorStats totalVisitorStats = appStatsService.totalVisitorStats(requestLogs, applicationStats.controllerHits)
        println totalVisitorStats.noOfVisits
        render(view: 'index', model: [applicationStats: applicationStats, cpuInfo: cpuInfo, memInfo: memInfo, serverInformation: serverInformation, workingDir: presentWorkingDir, totalVisitorStats: totalVisitorStats])
    }

    def commandResult = {
        String output = consoleService.executeCommand(params.command)
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
    }


}
