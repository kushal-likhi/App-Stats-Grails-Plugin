package org.devunited.grails.plugin.appstats


class ServerInformationController {
    def serverInformationService
    def consoleService

    def index = {
        IPInfo ipInfo = serverInformationService.getIPAddressAndHostName()
        List<List> cpuInfo = serverInformationService.getCPUInfo()
        List memInfo = serverInformationService.getMemoryInfo()
        ServerInformation serverInformation = serverInformationService.getServerInformation()
    }


    def console = {

    }

    def commandResult = {
        String output = consoleService.executeCommand(params.command)
        render output
    }
}
