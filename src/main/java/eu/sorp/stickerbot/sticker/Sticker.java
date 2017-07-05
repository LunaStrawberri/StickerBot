package eu.sorp.stickerbot.sticker;

import java.net.URL;

/**
 *
 * @author sorp
 */
public class Sticker {
    
    private final String name;
    private final URL url;
    
    /**
     * Sticker constructor
     * @param name sticker name (string)
     * @param url sticker image url (URL)
     */
    public Sticker(String name, URL url){
        this.name = name;
        this.url = url;
    }

    /**
     * Returns the sticker name
     * @return name variable
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the sticker image url
     * @return url variable
     */
    public URL getURL() {
        return url;
    }
    
    
    
}
