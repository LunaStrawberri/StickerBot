package eu.sorp.stickerbot.sticker;

import eu.sorp.stickerbot.StickerBot;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sorp
 */
public class StickerManager {
    
    /**
     * List for all stickers
     */
    public static LinkedList<Sticker> stickers = new LinkedList<>();
    
    /**
     * Adds a sticker to the stickers list and url file
     * @param s sticker to add
     */
    public static void addSticker(Sticker s){
        stickers.add(s);
        StickerBot.urlfile.getJsonObject().put(s.getName(), s.getURL().toString());
        System.out.println("Added Sticker " + s.getName());
    }
    
    /**
     * Removes a sticker from the stickers list and url file
     * @param s sticker to remove
     */
    public static void removeSticker(Sticker s){
        stickers.remove(s);
        StickerBot.urlfile.getJsonObject().remove(s.getName());
        StickerBot.urlfile.save();
        System.out.println("Removed Sticker " + s.getName());
    }
    
    /**
     * Reads the stickers from the url file and adds it to the stickers list
     */
    public static void loadStickers(){
        StickerBot.urlfile.getJsonObject().forEach((k,v) -> {
            try {
                String key = (String)k;
                String value = (String)v;
                Sticker s = new Sticker(key, new URL(value));
                addSticker(s);
            } catch (MalformedURLException ex) {
                Logger.getLogger(StickerManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    /**
     * Search a sticker with the sticker name
     * @param name name of the sticker (string)
     * @return null if not found or the sticker
     */
    public static Sticker searchWithName(String name){
        Sticker result = null;
        
        for(Sticker sticker : stickers){
            if(sticker.getName().equals(name)){
                result = sticker;
                break;
            }
        }
        
        return result;
    }
    
}
