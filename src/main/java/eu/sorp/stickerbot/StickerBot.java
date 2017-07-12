package eu.sorp.stickerbot;

import eu.sorp.stickerbot.file.JSONFile;
import eu.sorp.stickerbot.listener.ReadyEventListener;
import eu.sorp.stickerbot.listener.StickerRemoveListener;
import eu.sorp.stickerbot.listener.StickerUploadListener;
import eu.sorp.stickerbot.listener.StickerListListener;
import eu.sorp.stickerbot.listener.StickerSendListener;
import eu.sorp.stickerbot.listener.StickersHelpListener;
import eu.sorp.stickerbot.sticker.StickerManager;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     * Bot owner variable
     */
    public static IUser BOT_OWNER;

    /**
     * config file variable
     */
    public static JSONFile config;

    /**
     * url file variable
     */
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
     * @param dispatcher Discord EventDispatcher
     */
    public static void registerListeners(EventDispatcher dispatcher){
        dispatcher.registerListener(new ReadyEventListener());
        dispatcher.registerListener(new StickerUploadListener());
        dispatcher.registerListener(new StickerSendListener());
        dispatcher.registerListener(new StickerListListener());
        dispatcher.registerListener(new StickerRemoveListener());
        dispatcher.registerListener(new StickersHelpListener());
    }
    
    /**
     * Sets the bot owner
     */
    public static void setOwner(){
        if(config.getJsonObject().get("owner") != ""){
            try{
                long ownerID = Long.parseLong((String) config.getJsonObject().get("owner"));
                BOT_OWNER = DISCORD_CLIENT.getUserByID(ownerID);
                if(BOT_OWNER != null) System.out.println("Bot owner: " + BOT_OWNER.getName() + "#" + BOT_OWNER.getDiscriminator());
                else System.out.println("Bot owner: null");
            } catch(NumberFormatException ex){
                Logger.getLogger(StickerBot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static Map<String, Object> getConfigDefaults(){
        Map<String, Object> configDefaults = new HashMap<>();
        configDefaults.put("owner", "");
        configDefaults.put("upload-role", "none");
        configDefaults.put("remove-role", "null (admins & bot_owner)");
        configDefaults.put("bot-token", "INSERT-BOT-TOKEN");
        configDefaults.put("pageSize", "50");
        return configDefaults;
    }
    
}
