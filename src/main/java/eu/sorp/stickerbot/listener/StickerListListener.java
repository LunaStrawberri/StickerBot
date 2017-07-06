package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.StickerManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

/**
 *
 * @author sorp
 */
public class StickerListListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent event) {
        
        String message = event.getMessage().getContent();
        
        if(message.equalsIgnoreCase("/list")){
            
            EmbedBuilder embedBuilder = new EmbedBuilder()
                            .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                            .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL());
            
            Collection<String> stickers = StickerManager.getSortedList();
            
            int s = stickers.size() / 12;
            s += 2;
            String[] stickerFields = new String[s];
            int currentField = 0;
            
            for(int i=0; i<stickers.size(); i++){
                if(stickerFields[currentField] == null) stickerFields[currentField] = "";
                stickerFields[currentField] += stickers.toArray()[i] + "\n";
                if(i % 12 == 0){
                    currentField += 1;
                }
            }
            
            int f = 0;
            for(String str : stickerFields){
                f += 1;
                boolean inLine = true;
                if(f % 3 == 0) inLine = false;
                embedBuilder.appendField("Sticker " + f, str, false);
            }
            
            EmbedObject stickerList = embedBuilder.build();
            event.getAuthor().getOrCreatePMChannel().sendMessage(stickerList);
            
            if(event.getGuild() != null) event.getMessage().reply("Die Liste wurde dir privat gesendet.");
            
            
        }
        
    }
    
}
