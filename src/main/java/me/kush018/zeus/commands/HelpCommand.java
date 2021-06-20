package me.kush018.zeus.commands;

import me.kush018.zeus.Command;
import me.kush018.zeus.Utils;
import me.kush018.zeus.Zeus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class HelpCommand implements Command {

    String helpMenu;

    public HelpCommand() {
        StringBuilder helpMenuBuilder = new StringBuilder();
        int i = 1;
        helpMenuBuilder.append("Use prefix: ").append(Zeus.BOT_COMMAND_PREFIX).append("\n")
                .append("\n")
                .append("Below is a list of valid commands: ").append("\n");
        for (HashMap.Entry<String, Command> entry : Zeus.commandHashMap.entrySet()) {
            helpMenuBuilder.append(i).append(") ").append(entry.getKey()).append("\n");
            i++;
        }
        helpMenuBuilder.append("\n");
        helpMenuBuilder.append("Write: ```").append(Zeus.BOT_COMMAND_PREFIX).append("help <commandname>``` to learn more about commandname where commandname is a valid command in the list");
        helpMenu = helpMenuBuilder.toString();
    }

    @Override
    public int run(MessageReceivedEvent event, String commandArgsRaw) {
        String[] argv = Utils.divideIntoArgs(commandArgsRaw);
        if (argv.length == 0) {
            event.getMessage().getChannel().sendMessage(helpMenu).queue();
        } else {
            Command commandToHelp = Zeus.commandHashMap.get(argv[0]);
            if (commandToHelp == null) {
                event.getMessage().getChannel()
                        .sendMessage("Not a valid command.\nType " + Zeus.BOT_COMMAND_PREFIX + "help for a list of valid commands")
                        .queue();
            } else {
                String helpMessage = commandToHelp.getHelpString();
                if (helpMessage.trim().equals("")) {
                    helpMessage = "null";
                }
                event.getMessage().getChannel().sendMessage(helpMessage).queue();
            }
        }
        return Command.SUCCESS;
    }

    @Override
    public String getHelpString() {
        return "help - prints general help menu\n" +
                "help <command> - prints help menu for command";
    }

    @Override
    public String getCommandName() {
        return "help";
    }
}
