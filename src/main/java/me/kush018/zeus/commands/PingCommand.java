package me.kush018.zeus.commands;

import me.kush018.zeus.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand implements Command {
    @Override
    public int run(MessageReceivedEvent event, String commandArgsRaw) {
        MessageChannel channel = event.getChannel();
        long time = System.currentTimeMillis();
        channel.sendMessage("Pong!")
                .queue(response -> {
                    response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                });
        return Command.SUCCESS;
    }

    @Override
    public String getHelpString() {
        return "";
    }

    @Override
    public String getCommandName() {
        return "ping";
    }
}
