package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.sticker.StickerManager;
import java.time.LocalDateTime;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

/**
 *
 * @author sorp
 */
public class StickersSendListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent t) {
        
        String message = t.getMessage().getContent();
        
        StickerManager.stickers.forEach((s) -> {
            if(message.equals("#" + s.getName()) && !(message.equalsIgnoreCase("#upload") || message.equalsIgnoreCase("#list") || message.equalsIgnoreCase("#remove"))){
                    t.getMessage().delete();
                    
                    EmbedBuilder stickerBuilder = new EmbedBuilder()
                            .withImage(s.getURL().toString())
                            .withAuthorName(t.getAuthor().getDisplayName(t.getGuild()))
                            .withAuthorIcon(t.getAuthor().getAvatarURL())
                            .withFooterText("Sticker: " + s.getName())
                            .withTimestamp(LocalDateTime.now());
                    EmbedObject sticker = stickerBuilder.build();
                    
                    t.getChannel().sendMessage(sticker);
           }
        });
        
    }
    
}
