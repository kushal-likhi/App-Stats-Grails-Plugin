package org.devunited.grails.plugin.appstats

class ConsoleService {
    def serverInformationService
    static transactional = false

    String executeCommand(String command) {
        println command
        CommandResult commandResult = serverInformationService.runCommandAndGetInfo(command)
        StringBuilder output = commandResult.result ? commandResult.commandExecutor.getStandardErrorFromCommand() : commandResult.commandExecutor.getStandardOutputFromCommand();
        println commandResult.result
        println output
        return output.toString()
    }

}
