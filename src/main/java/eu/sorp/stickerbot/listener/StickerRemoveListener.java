package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.StickerManager;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author sorp
 */
public class StickerRemoveListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent event) {
        
        if(event.getMessage().getContent().startsWith("#remove")){
            if(event.getAuthor().equals(StickerBot.BOT_OWNER)){
                String stickerName = event.getMessage().getContent();
                stickerName = stickerName.replaceFirst("(?i)#remove", "");
                stickerName = stickerName.trim();
                
                if(StickerManager.searchWithName(stickerName) != null){
                    StickerManager.removeSticker(StickerManager.searchWithName(stickerName));
                    event.getMessage().reply("Sticker wurde entfernt!");
                } else {
                    event.getMessage().reply("Dieser Sticker existiert nicht.");
                }
                
            } else {
                event.getMessage().reply("Diese Funktion ist für dich nicht verfügbar.");
            }
        }
        
    }
    
}
