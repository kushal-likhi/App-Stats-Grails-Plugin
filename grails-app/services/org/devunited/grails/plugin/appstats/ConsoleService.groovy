package org.devunited.grails.plugin.appstats

class ConsoleService {
    def serverInformationService
    static transactional = false

    String executeCommand(String command) {
        CommandResult commandResult = serverInformationService.runCommandAndGetInfo(command)
        StringBuilder output = commandResult.result ? commandResult.commandExecutor.getStandardErrorFromCommand() : commandResult.commandExecutor.getStandardOutputFromCommand();
        return output
    }

}
