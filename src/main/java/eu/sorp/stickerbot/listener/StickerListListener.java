package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.StickerManager;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

/**
 *
 * @author sorp
 */
public class StickerListListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent event) {
        
        String message = event.getMessage().getContent();
        
        if(message.startsWith("/list")){
            
            IMessage progressMsg;
            
            if(event.getGuild() != null) progressMsg = event.getMessage().reply("Liste wird zusammengestellt...");
            else progressMsg = event.getAuthor().getOrCreatePMChannel().sendMessage(event.getAuthor().mention() + " Liste wird zusammengestellt...");
            
            boolean all = false;
            int page = 1;
            int pageSize = Integer.parseInt((String) StickerBot.config.getJsonObject().get("pageSize"));
            
            if(message.split(" ").length > 2){
                progressMsg.edit(event.getAuthor().mention() + " Syntax Error:\n``/list <Seite>``");
                return;
            }
            
            if(message.split(" ").length == 2){
                if(message.replaceFirst("/list", "").trim().equals("all"))
                    all = true;
                else{  
                    try {
                        page = Integer.parseInt(message.replaceFirst("/list", "").trim());
                    } catch (NumberFormatException e) {
                        progressMsg.edit(event.getAuthor().mention() + " Die Seite muss als Zahl angegeben werden.");
                        return;
                    }
                }
            }
            
            String[] stickers = StickerManager.getSortedList().toArray(new String[0]);
            
            int maxPages = stickers.length / pageSize;
            if(stickers.length % pageSize != 0)
                maxPages++;
            
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                    .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL());
            
            StringBuilder msg = new StringBuilder();
            
            if(all){
                for(String sticker : stickers){
                    msg.append(sticker).append("\n");
                    if(msg.length() >= 1024){
                        msg.delete(msg.length()-2, msg.length())
                                .delete(msg.lastIndexOf("\n"), msg.length());
                        
                        embedBuilder.appendField("Alle Sticker", msg.toString(), false);
                        event.getAuthor().getOrCreatePMChannel().sendMessage(embedBuilder.build());
                        
                        embedBuilder = new EmbedBuilder()
                                .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                                .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL());
                        msg = new StringBuilder();
                        
                        msg.append(sticker).append("\n");
                    }
                }
            }else if(page <= maxPages && page > 0){
                for(int i = pageSize*(page-1); i < pageSize*(page); i++){
                    if(stickers.length == i)
                        break;
                    msg.append(stickers[i]).append("\n");
                    if(msg.length() >= 1024){
                        msg.delete(msg.length()-2, msg.length())
                                .delete(msg.lastIndexOf("\n"), msg.length());
                        
                        embedBuilder.appendField("Seite " + page + " von " + maxPages, msg.toString(), false);
                        event.getAuthor().getOrCreatePMChannel().sendMessage(embedBuilder.build());
                        
                        embedBuilder = new EmbedBuilder()
                                .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                                .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL());
                        msg = new StringBuilder();
                        
                        msg.append(stickers[i]).append("\n");
                    }
                }
            }
            
            if(msg.length() == 0) {
                progressMsg.edit(event.getAuthor().mention() + " Keine Sticker auf dieser Seite vorhanden.\nAnzahl an Seiten: " + maxPages);
                return;
            }
            
            if(!all) embedBuilder.appendField("Seite " + page + " von " + maxPages, msg.toString(), false);
            else embedBuilder.appendField("Alle Sticker", msg.toString(), false);
            
            event.getAuthor().getOrCreatePMChannel().sendMessage(embedBuilder.build());
            progressMsg.edit(event.getAuthor().mention() + " Die Liste wurde dir gesendet");
            
        }
        
    }
    
}
