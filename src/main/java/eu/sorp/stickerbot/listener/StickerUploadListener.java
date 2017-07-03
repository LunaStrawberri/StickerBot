package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.Sticker;
import eu.sorp.stickerbot.sticker.StickerManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author sorp
 */
public class StickerUploadListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent t) {
        
        String message = t.getMessage().getContent();
        
        if(message.toLowerCase().startsWith("#upload")){
            if(!t.getMessage().getAttachments().isEmpty()){
                
                message = message.replaceFirst("(?i)#upload ", "");
                
                if(message.length() > 0){
                    
                    String stickerName = message;
                    String stickerURL = t.getMessage().getAttachments().get(0).getUrl();
                    
                    if(StickerManager.searchWithName(stickerName) == null){
                        
                        try {
                            StickerBot.urlfile.getJsonObject().put(stickerName, stickerURL);
                            
                            //Register Sticker
                            Sticker sticker = new Sticker(stickerName, new URL(stickerURL));
                            StickerManager.addSticker(sticker);
                            
                            //t.getMessage().reply("Downloaded " + fileName + " to /stickers/" + stickerName + "." + fileFormat);
                            t.getMessage().reply("Sticker wurde erstellt!");
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(StickerUploadListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    } else {
                        t.getMessage().reply("Ein Sticker mit diesem Namen existiert bereits, bitte verwende einen anderen Namen.");
                    }
                    
                } else {
                    t.getMessage().reply("Bitte gebe einen Namen für den Sticker an!");
                }
                
            } else {
                t.getMessage().reply("Es wurde kein Sticker im Anhang gefunden.");
            }
        }
        
    }
    
}
