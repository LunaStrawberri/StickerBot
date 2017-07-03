package eu.sorp.stickerbot.listener;

import eu.sorp.stickerbot.StickerBot;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;

/**
 *
 * @author sorp
 */
public class ReadyEventListener implements IListener<ReadyEvent> {

    @Override
    public void handle(ReadyEvent t) {
        
        StickerBot.DISCORD_CLIENT.changePlayingText("Stickers");
        
    }
    
}
