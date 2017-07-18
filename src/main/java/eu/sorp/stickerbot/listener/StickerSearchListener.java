package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.StickerManager;
import java.util.Collection;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

public class StickerSearchListener implements IListener<MessageReceivedEvent>{

    @Override
    public void handle(MessageReceivedEvent e){
        
        String msg = e.getMessage().getContent();
        
        if(msg.startsWith("/search")){
            
            final String cmd = msg.replaceFirst("/search", "").trim();
            
            IMessage progressMsg;
            
            if(e.getGuild() != null) progressMsg = e.getMessage().reply("Sticker werden gesucht...");
            else progressMsg = e.getAuthor().getOrCreatePMChannel().sendMessage(e.getAuthor().mention() + " Sticker werden gesucht...");
            
            if(!cmd.equals("")){
                
                Collection<String> stickers = StickerManager.getSortedList();
                
                EmbedBuilder emdBld = new EmbedBuilder()
                    .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                    .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL());
                
                StringBuilder resultMsg = new StringBuilder();
                
                for(String t : stickers){
                    if(t.contains(cmd)){
                        resultMsg.append(t).append("\n");
                        if(resultMsg.length() >= 1024){
                            resultMsg.delete(resultMsg.length()-2, resultMsg.length())
                                    .delete(resultMsg.lastIndexOf("\n"), resultMsg.length());
                            
                            emdBld.appendField("Results", resultMsg.toString(), false);
                            e.getAuthor().getOrCreatePMChannel().sendMessage(emdBld.build());
                            
                            emdBld = new EmbedBuilder()
                                .withAuthorName(StickerBot.DISCORD_CLIENT.getOurUser().getName())
                                .withAuthorIcon(StickerBot.DISCORD_CLIENT.getOurUser().getAvatarURL());
                            resultMsg = new StringBuilder();
                        
                            resultMsg.append(t).append("\n");
                        }
                    }
                }
                
                if(resultMsg.length() == 0){
                    progressMsg.edit(e.getAuthor().mention() + " Es existieren keine Sticker mit: ``" + cmd + "``");
                    return;
                }
                
                emdBld.appendField("Results", resultMsg.toString(), false);
                
                e.getAuthor().getOrCreatePMChannel().sendMessage(emdBld.build());
                progressMsg.edit(e.getAuthor().mention() + " Die Ergebnisse wurden dir gesendet");
                
            }else{
                progressMsg.edit(e.getAuthor().mention() + " Syntax Error:\n``/search <...>``");
            }
            
        }
        
    }
    
}
