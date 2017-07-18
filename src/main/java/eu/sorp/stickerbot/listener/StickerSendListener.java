package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.sticker.StickerManager;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

/**
 *
 * @author sorp
 */
public class StickerSendListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent t) {

        String message = t.getMessage().getContent();
        
        if (message.startsWith("/")) {
            StickerManager.stickers.forEach((s) -> {
                if (message.equals("/" + s.getName())) {
                    if(t.getGuild() != null) t.getMessage().delete();

                    EmbedBuilder stickerBuilder = new EmbedBuilder()
                            .withImage(s.getURL().toString())
                            .withAuthorName(t.getAuthor().getDisplayName(t.getGuild()))
                            .withAuthorIcon(t.getAuthor().getAvatarURL())
                            .withFooterText("Sticker: " + s.getName())
                            .withTimestamp(System.currentTimeMillis());
                    EmbedObject sticker = stickerBuilder.build();
                    
                    IMessage progressMsg;
            
                    if(t.getGuild() != null) progressMsg = t.getMessage().reply("Sticker wird geladen...");
                    else progressMsg = t.getAuthor().getOrCreatePMChannel().sendMessage("Sticker wird geladen...");

                    progressMsg.edit(sticker);
                }
            });
        }

    }

}
