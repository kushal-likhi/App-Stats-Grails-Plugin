package org.devunited.grails.plugin.appstats

class ServerInformationService {

    static transactional = false

    IPInfo getIPAddressAndHostName() {
        IPInfo ipInfo = new IPInfo()
        return ipInfo
    }

    List<List> getCPUInfo() {
        List<String> cpuInfo = getInfoFromCommand("cat /proc/cpuinfo")
        int countOfHyperThreadedProcessor = cpuInfo.findAll {it.contains("processor")}.size()
        return getCPUInfoSubList(cpuInfo, countOfHyperThreadedProcessor)
    }

    List getMemoryInfo() {
        List<String> memInfo = getInfoFromCommand("cat /proc/meminfo")
        return memInfo
    }

    List<String> getInfoFromCommand(String command) {
        SystemCommandExecutor commandExecutor = runCommandAndGetInfo(command)
        StringBuilder commandResult = commandExecutor.getStandardOutputFromCommand();
        List<String> info = commandResult.toString().tokenize("\n")
        info = info.collect {StringUtil.replaceExtraSpacesWithSingleSpace(it)}
        return info
    }

    SystemCommandExecutor runCommandAndGetInfo(String command) {
        List<String> cmd = []
        cmd.add("/bin/sh")
        cmd.add("-c")
        cmd.add(command);
        SystemCommandExecutor commandExecutor = new SystemCommandExecutor(cmd);
        int result = commandExecutor.executeCommand();
        return commandExecutor
    }

    List<List> getCPUInfoSubList(List cpuInfo, Integer count) {
        List<List> allCPUInfo = []
        Integer from = 0
        (1..count).each {
            Integer to = from + 24
            allCPUInfo.add(cpuInfo.subList(from, to))
            from = to
        }
        return allCPUInfo
    }

    ServerInformation getServerInformation() {
        ServerInformation serverInformation = new ServerInformation()
        serverInformation.virtualCPUCount = serverDetails("grep ^processor /proc/cpuinfo | wc -l") as Integer
        serverInformation.physicalCPUCount = serverDetails("grep 'physical id' /proc/cpuinfo | sort | uniq | wc -l") as Integer
        serverInformation.osName = serverDetails("uname -s")
        serverInformation.osVersion = serverDetails("uname -v")
        serverInformation.localDomainName = serverDetails("uname -n")
        serverInformation.kernelVersionNumber = serverDetails("uname -r")
        serverInformation.machineNumber = serverDetails("uname -m")
        serverInformation.serverDate = new Date()
        return serverInformation
    }

    String serverDetails(String command) {
        List<String> serverDetails = getInfoFromCommand(command)
        return serverDetails ? serverDetails?.first() : ""
    }
}


