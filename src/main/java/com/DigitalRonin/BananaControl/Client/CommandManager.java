package com.DigitalRonin.BananaControl.Client;

import com.DigitalRonin.BananaControl.Client.Commands.*;
import com.DigitalRonin.BananaControl.Client.Interfaces.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class CommandManager {

    private static final Map<String, Command> commands = new ConcurrentHashMap<>();

    CommandManager(EventListener eventListener){

        CommandManager cf = new CommandManager();

        cf.confirm(new Ban());
        cf.confirm(new ChannelLock());
        cf.confirm(new CreateRole());
        cf.confirm(new DeleteRole());
        cf.confirm(new Demote());
        cf.confirm(new Invite());
        cf.confirm(new Kick());
        cf.confirm(new Leave());
        cf.confirm(new MessageClean());
        cf.confirm(new MessageDelete());
        cf.confirm(new Pause());
        cf.confirm(new Play());
        cf.confirm(new Promote());
        cf.confirm(new Resume());
        cf.confirm(new RSSCreate());
        cf.confirm(new RSSDelete());
        cf.confirm(new SetupBot());
        cf.confirm(new SetupRules());
        cf.confirm(new SetupWelcome());
        cf.confirm(new Skip());
        cf.confirm(new Stop());
        cf.confirm(new TxtCreate());
        cf.confirm(new TxtDelete());
        cf.confirm(new TxtPerms());
        cf.confirm(new VoiceCreate());
        cf.confirm(new VoicePerms());
        cf.confirm(new VoiceLock());
        cf.confirm(new VoiceDelete());






    }

    private static void confirm(Command command){
        if(!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
            for(int i = 0; i < command.getAlias().length; i++) {
                if (commands.containsKey(command.getAlias()[i])) {
                    commands.put(command.getAlias()[i], command);
                }
            }
        }
    }

    private static void Unconfirm(Command command) {
        if(!commands.containsKey(command.getInvoke())) {
            commands.remove(command.getInvoke());
            for(int i = 0; i < command.getAlias().length; i++) {
                if (commands.containsKey(command.getAlias()[i])) {
                    commands.remove(command.getAlias()[i], command);
                }
            }
        }
    }

    private Collection<Command> getCommands() {
        return commands.values();
    }

    private Command getCommand(@NotNull String name) {
        return commands.get(name);
    }

    void handle(MessageCreateEvent event) {
        final String[] split = event.getMessage().getContent().replaceFirst("(?i)" + Pattern.quote(Core.PREFIX), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);
            commands.get(invoke).handle(args, event);
        }
    }



}
