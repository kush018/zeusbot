package me.kush018.zeus.commands;

import me.kush018.zeus.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StopCommand implements Command {
    @Override
    public int run(MessageReceivedEvent event, String commandArgsRaw) {
        SpamCommand.continueSpammingMap.put(event.getMessage().getChannel(), false);
        return Command.SUCCESS;
    }

    @Override
    public String getHelpString() {
        return "stop - stops spam in the current channel";
    }

    @Override
    public String getCommandName() {
        return "stop";
    }
}
