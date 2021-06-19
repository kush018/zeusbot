package me.kush018.zeus;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {

    int SUCCESS = 0;
    int INVALID_USAGE = -1;

    int run(MessageReceivedEvent event, String commandRaw);

    String getHelpString();

}
