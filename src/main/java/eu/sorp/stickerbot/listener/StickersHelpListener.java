package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

/**
 *
 * @author sorp
 */
public class StickersHelpListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent event) {
        
        if(event.getMessage().getContent().equalsIgnoreCase("/stickers")){
            
            String commands = "/stickers - Hilfe\n"
                            + "/upload <Stickername> - Sticker hochladen (Sticker als Bild im Anhang)\n"
                            + "/<Stickername> - Sticker senden";
            
            EmbedBuilder embedBuilder = new EmbedBuilder()
                            .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                            .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL())
                            .appendField("Befehle", commands, false);
            EmbedObject help = embedBuilder.build();
                    
            event.getChannel().sendMessage(help);
            
        }
        
    }
    
}
