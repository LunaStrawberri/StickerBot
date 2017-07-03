package eu.sorp.stickerbot;

import eu.sorp.stickerbot.configuration.Config;
import eu.sorp.stickerbot.listener.ReadyEventListener;
import eu.sorp.stickerbot.listener.StickerUploadListener;
import eu.sorp.stickerbot.listener.StickersListListener;
import eu.sorp.stickerbot.listener.StickersSendListener;
import eu.sorp.stickerbot.sticker.StickerManager;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

/**
 *
 * @author sorp
 */
public class StickerBot {
    
    /**
     * DiscordClient variable
     */
    public static IDiscordClient DISCORD_CLIENT;
    
    /**
     * Main function
     * @param args 
     */
    public static void main(String[] args) {
        Config config = new Config("config.json");
        StickerManager.loadStickers();
        DISCORD_CLIENT = new ClientBuilder().withToken((String) config.getJsonObject().get("bot-token")).login();
        registerListeners(DISCORD_CLIENT.getDispatcher());
    }
    
    /**
     * Registers the listeners
     * @param dispatcher
     */
    public static void registerListeners(EventDispatcher dispatcher){
        dispatcher.registerListener(new ReadyEventListener());
        dispatcher.registerListener(new StickerUploadListener());
        dispatcher.registerListener(new StickersSendListener());
        dispatcher.registerListener(new StickersListListener());
    }
    
}
