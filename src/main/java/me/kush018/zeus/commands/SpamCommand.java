package me.kush018.zeus.commands;

import me.kush018.zeus.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SpamCommand implements Command {
    @Override
    public int run(MessageReceivedEvent event, String commandRaw) {
        return Command.SUCCESS;
    }

    @Override
    public String getHelpString() {
        return "";
    }
}
