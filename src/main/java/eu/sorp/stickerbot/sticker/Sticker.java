package eu.sorp.stickerbot.sticker;

import java.net.URL;

/**
 *
 * @author sorp
 */
public class Sticker {
    
    private final String name;
    private final URL url;
    
    public Sticker(String name, URL url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public URL getURL() {
        return url;
    }
    
    
    
}
