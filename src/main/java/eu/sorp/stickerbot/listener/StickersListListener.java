package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.sticker.StickerManager;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author sorp
 */
public class StickersListListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent event) {
        
        String message = event.getMessage().getContent();
        
        if(message.equalsIgnoreCase("#list")){
            
            StringBuilder stickers = new StringBuilder();
           
            stickers.append("```");
            StickerManager.stickers.forEach((t) -> {
                stickers.append(t.getName()).append("\n");
            });
            stickers.append("```");
                
            event.getMessage().reply(stickers.toString());
            
        }
        
    }
    
}
