package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import eu.sorp.stickerbot.sticker.Sticker;
import eu.sorp.stickerbot.sticker.StickerManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class StickerUploadListener implements IListener<MessageReceivedEvent> {

    @Override
    public void handle(MessageReceivedEvent t) {
        
        String message = t.getMessage().getContent();
        
        if(message.toLowerCase().startsWith("#upload")){
            if(isAllowedToUpload(t.getGuild(), t.getAuthor())){
                if(!t.getMessage().getAttachments().isEmpty() && isPicture(t.getMessage().getAttachments().get(0).getFilename())){
                    message = message.replaceFirst("(?i)#upload", "");
                    message = message.trim();

                    if(message.length() > 0){

                        String stickerName = message;
                        String stickerURL = t.getMessage().getAttachments().get(0).getUrl();

                        if(StickerManager.searchWithName(stickerName) == null){

                            try {
                                StickerBot.urlfile.getJsonObject().put(stickerName, stickerURL);

                                //Register Sticker
                                Sticker sticker = new Sticker(stickerName, new URL(stickerURL));
                                StickerManager.addSticker(sticker, true);

                                t.getMessage().reply("Sticker wurde erstellt!");
                            } catch (MalformedURLException ex) {
                                Logger.getLogger(StickerUploadListener.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else {
                            t.getMessage().reply("Ein Sticker mit diesem Namen existiert bereits, bitte verwende einen anderen Namen.");
                        }

                    } else {
                        t.getMessage().reply("Bitte gebe einen Namen für den Sticker an!");
                    }

                } else {
                    t.getMessage().reply("Es wurde kein Sticker im Anhang gefunden.");
                }
            } else {
                t.getMessage().reply("Du bist nicht dazu berichtigt, einen Sticker hochzuladen.");
            }
        }
        
    }
    
    public boolean isPicture(String fileName){
        List<String> validFormats = Arrays.asList("gif", "png", "jpg");
        String format = fileName.substring(fileName.length() - 3, fileName.length());
        
        return validFormats.contains(format);
    }
    
    public boolean isAllowedToUpload(IGuild guild, IUser user){
        boolean allowed = false;
        
        if(user.equals(StickerBot.BOT_OWNER)) return true;
        
        final String uploadRole = (String) StickerBot.config.getJsonObject().get("upload-role");
        if(!uploadRole.equals("none")){
            if(!user.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR)){
                for(IRole role : user.getRolesForGuild(guild)){
                    if(role.getName().equals(uploadRole)){
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
