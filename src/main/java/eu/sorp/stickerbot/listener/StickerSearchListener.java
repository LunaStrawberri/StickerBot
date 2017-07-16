package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.StickerManager;
import java.util.Collection;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

public class StickerSearchListener implements IListener<MessageReceivedEvent>{

    @Override
    public void handle(MessageReceivedEvent e){
        
        String msg = e.getMessage().getContent();
        
        if(msg.startsWith("/search")){
            
            final String cmd = msg.replaceFirst("/search", "").trim();
            
            if(!cmd.equals("")){
                
                Collection<String> stickers = StickerManager.getSortedList();
                
                EmbedBuilder emdBld = new EmbedBuilder()
                    .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                    .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL());
                
                StringBuilder resultMsg = new StringBuilder();
                
                stickers.forEach((t) -> {
                    if(t.contains(cmd)){
                        resultMsg.append(t).append("\n");
                    }
                });
                
                if(resultMsg.length() == 0){
                    if(e.getGuild() != null) e.getMessage().reply("Es existieren keine Sticker mit: ``" + cmd + "``");
                    else e.getAuthor().getOrCreatePMChannel().sendMessage("Es existieren keine Sticker mit: ``" + cmd + "``");
                    return;
                }
                
                emdBld.appendField("Results", resultMsg.toString(), false);
                
                e.getAuthor().getOrCreatePMChannel().sendMessage(emdBld.build());
                if(e.getGuild() != null) e.getMessage().reply("Die Ergebnisse wurden dir privat gesendet.");
                
            }else{
                if(e.getGuild() != null) e.getMessage().reply("Syntax Error:\n``/search <...>``");
                else e.getAuthor().getOrCreatePMChannel().sendMessage("Syntax Error:\n``/search <...>``");
            }
            
        }
        
    }
    
}
