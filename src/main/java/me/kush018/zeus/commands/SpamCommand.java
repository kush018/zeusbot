package me.kush018.zeus.commands;

import me.kush018.zeus.Command;
import me.kush018.zeus.Utils;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.ConcurrentHashMap;

public class SpamCommand implements Command {

    public static final int SPAM_LIMIT = 100;

    public static ConcurrentHashMap<MessageChannel, Boolean> continueSpammingMap;

    public SpamCommand() {
        continueSpammingMap = new ConcurrentHashMap<>();
    }

    @Override
    public int run(MessageReceivedEvent event, String commandArgsRaw) {
        String[] argv = Utils.divideIntoArgs(commandArgsRaw);
        int times = 10;
        boolean specifiedTimes = false;
        try {
            times = Integer.parseInt(argv[argv.length - 1]);
            specifiedTimes = true;
        } catch (NumberFormatException ignored) { }
        if (times > SPAM_LIMIT) {
            event.getMessage().getChannel().sendMessage("Stop trying to spam so much - you'll annoy your friends!").queue();
            return Command.SUCCESS;
        }
        String toSpam = commandArgsRaw.trim();
        if (specifiedTimes) {
            toSpam = commandArgsRaw.substring(0, commandArgsRaw.length() - argv[argv.length - 1].length()).trim();
        }
        if (toSpam.equals("")) {
            event.getMessage().getChannel().sendMessage("You haven't told me what to spam!").queue();
        } else {
            int finalTimes = times;
            String finalToSpam = toSpam;
            Runnable runnable = () -> {
                MessageChannel channel = event.getMessage().getChannel();
                continueSpammingMap.put(channel, true);
                for (int i = 0; i < finalTimes; i++) {
                    if (!continueSpammingMap.get(channel)) {
                        channel.sendMessage("Stopped spamming due to user abort.").queue();
                        continueSpammingMap.put(channel, true);
                        break;
                    }
                    event.getMessage().getChannel().sendMessage(finalToSpam).complete();
                }
                event.getMessage().getChannel().sendMessage("done spamming").queue();
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        return Command.SUCCESS;
    }

    @Override
    public String getHelpString() {
        return "spam <message to be spammed> <n> - spams <message to be spammed> n times\n" +
                "spam <message to be spammed> - spams <message to be spammed> 10 times";
    }

    @Override
    public String getCommandName() {
        return "spam";
    }
}
