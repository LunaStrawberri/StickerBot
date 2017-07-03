package eu.sorp.stickerbot.sticker;

/**
 *
 * @author sorp
 */
public class Sticker {
    
    private final String name;
    private final String path;
    
    public Sticker(String name, String path){
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
    
    
    
}
