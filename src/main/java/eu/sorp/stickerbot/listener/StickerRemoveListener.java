package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.StickerManager;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

/**
 *
 * @author sorp
 */
public class StickerRemoveListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent event) {
        
        if(event.getMessage().getContent().startsWith("#remove")){
            if(isAllowedToRemove(event.getGuild(), event.getAuthor())){
                String stickerName = event.getMessage().getContent();
                stickerName = stickerName.replaceFirst("(?i)#remove", "");
                stickerName = stickerName.trim();
                
                if(StickerManager.searchWithName(stickerName) != null){
                    StickerManager.removeSticker(StickerManager.searchWithName(stickerName), true);
                    event.getMessage().reply("Sticker wurde entfernt!");
                } else {
                    event.getMessage().reply("Dieser Sticker existiert nicht.");
                }
                
            } else {
                event.getMessage().reply("Du bist nicht dazu berichtigt, einen Sticker zu löschen.");
            }
        }
        
    }
    
     public boolean isAllowedToRemove(IGuild guild, IUser user){
        boolean allowed = false;
        
        if(user.equals(StickerBot.BOT_OWNER)) return true;
        
        final String removeRole = (String) StickerBot.config.getJsonObject().get("remove-role");
        if(removeRole.equals("bot_owner")) return false;
        
        if(!removeRole.equals("none")){
            if(!user.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR)){
                for(IRole role : user.getRolesForGuild(guild)){
                    if(role.getName().equals(removeRole)){
                        allowed = true;
                    }
                }
            } else {
                allowed = true;
            }
        } else {
            allowed = true;
        }
        
        return allowed;
    }
    
}
