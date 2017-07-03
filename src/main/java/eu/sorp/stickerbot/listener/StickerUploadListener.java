package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.sticker.Sticker;
import eu.sorp.stickerbot.sticker.StickerManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
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
                            
                            //Download Sticker
                            String[] stickerURLSplit = stickerURL.split("/");
                            String fileName = stickerURLSplit[stickerURLSplit.length - 1];
                            String fileFormat = (String) fileName.subSequence(fileName.length() - 3, fileName.length());
                            
                            System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
                            FileUtils.copyURLToFile(new URL(stickerURL), new File("stickers/" + stickerName + "." + fileFormat));
                            
                            Sticker sticker = new Sticker(stickerName, "stickers/" + stickerName + "." + fileFormat);
                            StickerManager.addSticker(sticker);
                            
                            //t.getMessage().reply("Downloaded " + fileName + " to /stickers/" + stickerName + "." + fileFormat);
                            t.getMessage().reply("Sticker wurde erstellt!");
                            
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(StickerUploadListener.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
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
