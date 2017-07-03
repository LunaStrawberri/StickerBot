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
    
    public static void addSticker(Sticker s){
        stickers.add(s);
        StickerBot.urlfile.getJsonObject().put(s.getName(), s.getURL().toString());
        StickerBot.urlfile.save();
        System.out.println("Added Sticker " + s.getName());
    }
    
    public static void removeSticker(Sticker s){
        stickers.remove(s);
        StickerBot.urlfile.getJsonObject().remove(s.getName());
        StickerBot.urlfile.save();
        System.out.println("Removed Sticker " + s.getName());
    }
    
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
