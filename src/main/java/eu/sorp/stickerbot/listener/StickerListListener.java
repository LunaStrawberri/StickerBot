package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.StickerManager;
import java.util.Collection;
import java.util.Iterator;
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
        
        if(message.toLowerCase().toLowerCase().startsWith("/list")){
            
            int page = 1;
            
            if(message.split(" ").length > 2){
                if(event.getGuild() != null) event.getChannel().sendMessage(event.getAuthor().mention() + "\nSyntax Error:\n/list <Seite>");
                else event.getAuthor().getOrCreatePMChannel().sendMessage(event.getAuthor().mention() + "\nSyntax Error:\n/list <Seite>");
                return;
            }
            
            if(message.split(" ").length == 2){
                try {
                    page = Integer.parseInt(message.replaceFirst("/list ", ""));
                } catch (NumberFormatException e) {
                    if(event.getGuild() != null) event.getChannel().sendMessage(event.getAuthor().mention() + " Gib bitte eine Zahl als Seite an.");
                    else event.getAuthor().getOrCreatePMChannel().sendMessage(event.getAuthor().mention() + " Gib bitte eine Zahl als Seite an.");
                    return;
                }
            }
            
            Collection<String> stickers = StickerManager.getSortedList();
            
            int s = stickers.size() / 12;
            if(stickers.size() % 12 != 0)
                s++;
            
            String[][] stickerFields = new String[s][12];
            Iterator<String> stickersIt = stickers.iterator();
            
            for(int i=0; i < s; i++){
                for(int i1=0; i1 < 12; i1++){
                    if(!stickersIt.hasNext())
                        break;
                    
                    stickerFields[i][i1] = stickersIt.next();
                }
                if(!stickersIt.hasNext())
                    break;
            }
            
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                    .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL());
            
            StringBuilder msg = new StringBuilder();
            
            if(s >= page){
                for(String str : stickerFields[page-1]){
                    if(str == null)
                        break;
                    msg.append(str).append("\n");
                }
            }
            
            if(msg.length() > 0) embedBuilder.appendField("Seite " + page + " von " + s, msg.toString(), false);
            
            if(embedBuilder.getFieldCount() == 0) embedBuilder.appendDescription("Keine Sticker auf dieser Seite vorhanden.\nAnzahl an Seiten: " + s);
                
            EmbedObject stickerList = embedBuilder.build();
            event.getAuthor().getOrCreatePMChannel().sendMessage(stickerList);
            
            if(event.getGuild() != null) event.getMessage().reply("Die Liste wurde dir privat gesendet.");
            
            
        }
        
    }
    
}
