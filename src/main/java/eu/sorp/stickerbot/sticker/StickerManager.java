package eu.sorp.stickerbot.sticker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        System.out.println("Added Sticker " + s.getName());
    }
    
    public static void removeSticker(Sticker s){
        stickers.remove(s);
        System.out.println("Removed Sticker " + s.getName());
    }
    
    public static void loadStickers(){
        
        try {
            List<File> filesInFolder = Files.walk(Paths.get("stickers"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            
            filesInFolder.forEach((t) -> {
                Sticker s = new Sticker(t.getName().substring(0, t.getName().length() - 4), t.getPath());
                addSticker(s);
            });
            
        } catch (IOException ex) {
            Logger.getLogger(StickerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
