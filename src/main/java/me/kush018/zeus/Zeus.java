package me.kush018.zeus;

import me.kush018.zeus.commands.HelpCommand;
import me.kush018.zeus.commands.PingCommand;
import me.kush018.zeus.commands.SpamCommand;
import me.kush018.zeus.commands.StopCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Zeus extends ListenerAdapter {
    public static HashMap<String, Command> commandHashMap;

    public static final String BOT_COMMAND_PREFIX = "z-";

    public static void main(String[] args) {
        commandHashMap = new HashMap<>();

        commandHashMap.put("ping", new PingCommand());
        commandHashMap.put("spam", new SpamCommand());
        commandHashMap.put("stop", new StopCommand());

        commandHashMap.put("help", new HelpCommand());

        String apiKey;
        try {
            apiKey = Files.readAllLines(Path.of("apikey")).get(0);
        } catch (IOException e) {
            System.out.println("IOException occurred while reading file apikey. Aborting ...");
            return;
        }

        JDA jda;
        try {
            jda = JDABuilder.createDefault(apiKey)
                    .setActivity(Activity.playing("z-help"))
                    .build();
        } catch (LoginException e) {
            System.out.println("LoginException occurred while creating JDA object. Aborting ...");
            return;
        }

        jda.addEventListener(new Zeus());
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        String content = event.getMessage().getContentDisplay();
        if (content.startsWith(BOT_COMMAND_PREFIX)) {
            String commandRaw = content.substring(BOT_COMMAND_PREFIX.length());
            String commandName = commandRaw.split(" ")[0];
            Command commandObj = commandHashMap.get(commandName);
            if (commandObj == null) {
                event.getMessage().getChannel()
                        .sendMessage("Invalid command entered\nEnter " + BOT_COMMAND_PREFIX + "help for a list of valid commands")
                        .queue();
            } else {
                String commandArgsRaw = commandRaw.substring(commandName.length());
                commandArgsRaw = commandArgsRaw.trim();
                int executionCode = commandObj.run(event, commandArgsRaw);
                if (executionCode == Command.INVALID_USAGE) {
                    event.getMessage().getChannel()
                            .sendMessage("Invalid command usage\nEnter " + BOT_COMMAND_PREFIX + "help " + commandName + " to learn more")
                            .queue();
                }
            }
        }
    }
}
