package com.DigitalRonin.BananaControl.Client;

import com.DigitalRonin.BananaControl.Client.Interfaces.Command;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class BananaBot {

    private final static String disToken = System.getenv("DisToken");
    private final static DiscordClient client = null;

    private static final Map<String, Command> commands = new HashMap();


    interface Command {
        Mono<Void> execute(MessageCreateEvent event);
    }

    public static String getDisToken() {
        return disToken;
    }

    public static DiscordClient getClient() {
        return createBananaBot();
    }

    private static DiscordClient createBananaBot() {
        String t = getDisToken();
        String x = "@BananaBot";
        DiscordClientBuilder.create(t)
                .build().gateway()
                .setInitialStatus(shard -> Presence.online(Activity.playing("Booting Up!")))
                .withGateway(client -> {
                    client.getEventDispatcher().on(ReadyEvent.class)
                            .subscribe(ready -> System.out.println("Logged in as " + ready.getSelf().getUsername()));

                    client.getEventDispatcher().on(MessageCreateEvent.class)
                            .flatMap(event -> Mono.justOrEmpty(event.getMessage().getContent())
                            .flatMap(content -> Flux.fromIterable(commands.entrySet())
                            .filter(entry -> content.startsWith(x + " " + entry.getKey()))
                            .flatMap(entry -> entry.getValue().execute(event))
                            .next()))
                            .subscribe();


                    return client.onDisconnect();
                })
                .block();

        return client;
    }

    public static void main(String[] args) {

        DiscordClient newClient = getClient();




    }




}
