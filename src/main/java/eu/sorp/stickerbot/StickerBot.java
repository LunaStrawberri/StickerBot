package eu.sorp.stickerbot;

import eu.sorp.stickerbot.file.JSONFile;
import eu.sorp.stickerbot.listener.ReadyEventListener;
import eu.sorp.stickerbot.listener.StickerRemoveListener;
import eu.sorp.stickerbot.listener.StickerUploadListener;
import eu.sorp.stickerbot.listener.StickersListListener;
import eu.sorp.stickerbot.listener.StickersSendListener;
import eu.sorp.stickerbot.sticker.StickerManager;
import java.util.HashMap;
import java.util.Map;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author sorp
 */
public class StickerBot {
    
    /**
     * DiscordClient variable
     */
    public static IDiscordClient DISCORD_CLIENT;
    public static IUser BOT_OWNER;
    public static JSONFile config;
    public static JSONFile urlfile;
    
    /**
     * Main function
     * @param args 
     */
    public static void main(String[] args) {
        config = new JSONFile("config.json", getConfigDefaults());
        urlfile = new JSONFile("url.json");
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
        dispatcher.registerListener(new StickerRemoveListener());
    }
    
    public static void setOwner(){
        if(config.getJsonObject().get("owner") != ""){
            try{
                long ownerID = Long.parseLong((String) config.getJsonObject().get("owner"));
                BOT_OWNER = DISCORD_CLIENT.getUserByID(ownerID);
                System.out.println("Bot owner: " + BOT_OWNER.getName() + "#" + BOT_OWNER.getDiscriminator());
            } catch(NumberFormatException e){
                e.printStackTrace();
            }
        }
    }
    
    private static Map<String, Object> getConfigDefaults(){
        Map<String, Object> configDefaults = new HashMap<>();
        configDefaults.put("bot-token", "INSERT-BOT-TOKEN");
        configDefaults.put("owner", "");
        return configDefaults;
    }
    
}
