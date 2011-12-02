package org.devunited.grails.plugin.appstats


class ServerInformationController {
    def serverInformationService

    def index = {
        IPInfo ipInfo = serverInformationService.getIPAddressAndHostName()
        List<List> cpuInfo = serverInformationService.getCPUInfo()
        List memInfo = serverInformationService.getMemoryInfo()
        ServerInformation serverInformation = serverInformationService.getServerInformation()
    }
}
