package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.StickerManager;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

/**
 *
 * @author sorp
 */
public class StickerRemoveListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent event) {
        
        if(event.getMessage().getContent().startsWith("/remove")){
            if(isAllowedToRemove(event.getGuild(), event.getAuthor())){
                String stickerName = event.getMessage().getContent();
                stickerName = stickerName.replaceFirst("(?i)/remove", "");
                stickerName = stickerName.trim();
                
                if(StickerManager.searchWithName(stickerName) != null){
                    StickerManager.removeSticker(StickerManager.searchWithName(stickerName), true);
                    event.getMessage().reply("Sticker wurde entfernt!");
                } else {
                    event.getMessage().reply("Dieser Sticker existiert nicht.");
                }
                
            } else {
                event.getMessage().reply("Du bist nicht dazu berechtigt, einen Sticker zu löschen.");
            }
        }
        
    }
    
    public boolean isAllowedToRemove(IGuild guild, IUser user){
        final String removeRole = (String) StickerBot.config.getJsonObject().get("remove-role");
        
        if(StickerBot.BOT_OWNER != null){
            if(removeRole.equals("bot_owner")){
                if(user.equals(StickerBot.BOT_OWNER)) return true;
                else return false;
            }
        }
        
        if(removeRole.equals("none"))
            return true;
        
         if(user.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR))
            return true;
        if (user.getRolesForGuild(guild).stream().anyMatch((role) -> (role.getName().equals(removeRole))))
            return true;
        
        return false;
    }
    
}
