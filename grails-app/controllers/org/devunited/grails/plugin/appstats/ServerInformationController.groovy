package org.devunited.grails.plugin.appstats


class ServerInformationController {
    def serverInformationService
    def consoleService

    def index = {
        IPInfo ipInfo = serverInformationService.getIPAddressAndHostName()
        List<List> cpuInfo = serverInformationService.getCPUInfo()
        List memInfo = serverInformationService.getMemoryInfo()
        ServerInformation serverInformation = serverInformationService.getServerInformation()
        String presentWorkingDir = new File(".").getCanonicalPath();
        render(view: 'index', model: [cpuInfo: cpuInfo, memInfo: memInfo, serverInformation: serverInformation, workingDir: presentWorkingDir])
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
        render "bye"
    }
}
