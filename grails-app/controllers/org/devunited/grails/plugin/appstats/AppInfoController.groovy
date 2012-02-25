package org.devunited.grails.plugin.appstats


class AppInfoController {


    def serverInformationService
    def appStatsService

    static defaultAction = "index"

    def index = {
        appStatsService.initialize(params)
        render view: 'index', model: [
                applicationStats: appStatsService.summary(grailsApplication.controllerClasses as List),
                totalVisitorStats: appStatsService.totalVisitorStats(grailsApplication.controllerClasses as List),
                cpuInfo: serverInformationService.getCPUInfo(),
                memInfo: serverInformationService.getMemoryInfo(),
                serverInformation: serverInformationService.getServerInformation(),
                workingDir: new File(".").getCanonicalPath(),
                ipInfo: serverInformationService.getIPAddressAndHostName(),
                pageInfoList: appStatsService.getAllPagesInformation(),
                controllerInfoList: appStatsService.getAllControllerInformation(),
                controllerAndActionList: appStatsService.getAllControllerAndActionInformation()
        ]
    }

    def commandResult = {
        String output = serverInformationService.executeCommand(params.command)
        output.eachLine {
            render "${it}<br/>"
        }
    }

    def ipOfUrl = {
        render serverInformationService.getIpForUrl(params.url)
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
        List<PageInfo> pageInfoList = []
        appStatsService.requestLogs.groupBy {it.URL}.each {String url, List<RequestLog> logs ->
            PageInfo pageInfo = new PageInfo()
            pageInfo.pageURL = url
            pageInfo.noOfVisits = logs.clone().unique {it.sessionId}.size()
            pageInfo.uniqueVisitor = logs.clone().unique {it.clientIP}.size()
            pageInfo.totalHits = logs.size()
            pageInfoList.add(pageInfo)
        }
        render "yo"
        render pageInfoList*.totalHits
    }

}
