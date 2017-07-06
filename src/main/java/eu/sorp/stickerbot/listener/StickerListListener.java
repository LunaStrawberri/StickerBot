package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.sticker.StickerManager;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author sorp
 */
public class StickerListListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent event) {
        
        String message = event.getMessage().getContent();
        
        if(message.equalsIgnoreCase("#list")){
            
            StringBuilder stickers = new StringBuilder();
           
            stickers.append("```").append("\n");
            StickerManager.getSortedList().forEach(t -> {
                stickers.append(t).append("\n");
            });
            stickers.append("```");
                
            event.getAuthor().getOrCreatePMChannel().sendMessage(stickers.toString());
            if(event.getGuild() != null) event.getMessage().reply("Die Liste wurde dir privat gesendet.");
            
            
        }
        
    }
    
}
