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
     * @param save save sticker to the url.json file
     */
    public static void addSticker(Sticker s, boolean save){
        stickers.add(s);
        //sortStickers();
        if(save){ 
            StickerBot.urlfile.getJsonObject().put(s.getName(), s.getURL().toString());
            StickerBot.urlfile.save();
        }
        System.out.println("Added Sticker " + s.getName());
    }
    
    /**
     * Removes a sticker from the stickers list and url file
     * @param s sticker to remove
     * @param save remove sticker from the url.json file & save
     */
    public static void removeSticker(Sticker s, boolean save){
        stickers.remove(s);
        if(save){
            StickerBot.urlfile.getJsonObject().remove(s.getName());
            StickerBot.urlfile.save();
        }
        System.out.println("Removed Sticker " + s.getName());
    }
    
    /**
     * Reads the stickers from the url file and adds it to the stickers list
     */
    public static void loadStickers(){
        StickerBot.urlfile.getJsonObject().forEach((k,v) -> {
            try {
                addSticker(new Sticker((String)k, new URL((String)v)), false);
            } catch (MalformedURLException ex) {
                Logger.getLogger(StickerManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    /**
     * Sorts the stickers by their names
     */
    //TODO: Fix? :o [Exception in thread "main" java.lang.StringIndexOutOfBoundsException: String index out of range: 2]
    public static void sortStickers(){
        stickers.sort((o1, o2) -> {
            for(int i = 0; i <= o1.getName().length() && i <= o2.getName().length(); i++){
                
                if(o1.getName().equalsIgnoreCase(o2.getName())){
                    if(o1.getName().charAt(i) > o2.getName().charAt(i))
                        return 1;
                    else if(o1.getName().charAt(i) < o2.getName().charAt(i))
                        return -1;
                }
                else if(o1.getName().toLowerCase().charAt(i) > o2.getName().toLowerCase().charAt(i))
                    return 1;
                else if(o1.getName().toLowerCase().charAt(i) < o2.getName().toLowerCase().charAt(i))
                    return -1;
                    
            }
            return 0;
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
