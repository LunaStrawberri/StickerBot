package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.sticker.StickerManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author sorp
 */
public class StickersSendListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent t) {
        
        String message = t.getMessage().getContent();
        
        StickerManager.stickers.forEach((s) -> {
            if(message.equals("#" + s.getName()) && !(message.equalsIgnoreCase("#upload") || message.equalsIgnoreCase("#list"))){
                try {
                    t.getMessage().delete();
                    
                    String[] stickerURLSplit = s.getURL().toString().split("/");
                    String fileName = stickerURLSplit[stickerURLSplit.length - 1];
                    
                    File sticker = new File(FileUtils.getTempDirectoryPath() + "/" + fileName);
                    
                    System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
                    FileUtils.copyURLToFile(s.getURL(), sticker);
                    
                    t.getChannel().sendFile(sticker);
                    
                    sticker.delete();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(StickersSendListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(StickersSendListener.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
        
    }
    
}
