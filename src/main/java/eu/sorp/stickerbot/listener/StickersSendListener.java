package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.sticker.StickerManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            if(message.equalsIgnoreCase("#" + s.getName()) && !(message.equalsIgnoreCase("#upload") || message.equalsIgnoreCase("#list"))){
                try {
                    t.getMessage().delete();
                    t.getChannel().sendFile(new File(s.getPath()));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(StickersSendListener.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        });
        
    }
    
}
