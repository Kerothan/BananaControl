package com.DigitalRonin.BananaControl.Client.MusicBot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;


public class GuildMusicManager {
    /**
     * Audio player for the guild
     */
    private final AudioPlayer player;
    /**
     * Track scheduler for the player
     */
    private final TrackScheduler scheduler;

    private final D4jAudioProvider provider;

    /**
     * creates a player and a track scheduler.
     * @param manager Audio player manager to use for creating the player.
     */
    public GuildMusicManager(AudioPlayerManager manager){
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
        provider = new D4jAudioProvider(player);
    }


}
