package com.DigitalRonin.BananaControl.Client.Interfaces;

import discord4j.core.event.domain.message.MessageCreateEvent;

import java.util.List;

public interface Command {
    void handle(List<String> args, MessageCreateEvent event);

    String getInvoke();

    String[] getAlias();

    Category getCategory();

    enum Category {ADMIN, MODERATION, BOT, MUSIC, STOCKS, MISC, OWNER}

}
