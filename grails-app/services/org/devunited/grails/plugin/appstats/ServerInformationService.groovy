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
        CommandResult result = runCommandAndGetInfo(command)
        StringBuilder commandResult = result.commandExecutor.getStandardOutputFromCommand();
        List<String> info = commandResult.toString().tokenize("\n")
        info = info.collect {StringUtil.replaceExtraSpacesWithSingleSpace(it)}
        return info
    }

    CommandResult runCommandAndGetInfo(String command) {
        List<String> cmd = []
        cmd.add("/bin/sh")
        cmd.add("-c")
        cmd.add(command);
        CommandResult commandResult = new CommandResult()
        commandResult.commandExecutor = new SystemCommandExecutor(cmd);
        commandResult.result = commandResult.commandExecutor.executeCommand();
        return commandResult
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

    String executeCommand(String command) {
        CommandResult commandResult = runCommandAndGetInfo(command)
        StringBuilder output = commandResult.result ? commandResult.commandExecutor.getStandardErrorFromCommand() : commandResult.commandExecutor.getStandardOutputFromCommand();
        return output
    }

    String getIpForUrl(String url) {
        executeCommand("ping -c1 ${url}").find(/(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)/)?:""
    }
}


