package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.StickerManager;
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
            
            String info = "Der Bot wurde von Ashimara & sorpjp programmiert"
                        + "\nGitHub: http://sorp.me/src/stickerbot";
            
            String stats = "Stickers: " + StickerManager.stickers.size() + ""
                        + "\nServer: " + StickerBot.DISCORD_CLIENT.getGuilds().size();
            
            String commands = "/stickers - Hilfe\n"
                            + "/upload <Stickername> - Sticker hochladen (Sticker als Bild im Anhang)\n"
                            + "/list - Sendet dir privat eine Liste aller Stickernamen\n"
                            + "/<Stickername> - Sticker senden";
            
            EmbedBuilder embedBuilder = new EmbedBuilder()
                            .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                            .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL())
                            .appendField("Info", info, false)
                            .appendField("Stats", stats, false)
                            .appendField("Befehle", commands, false);
            EmbedObject help = embedBuilder.build();
                    
            event.getChannel().sendMessage(help);
            
        }
        
    }
    
}
