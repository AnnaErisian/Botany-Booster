package blue.thejester.botanybooster.core.handler;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BBEventHandler {
    @SubscribeEvent
    public void tick(TickEvent tickEvent) {
        if ((tickEvent.type == TickEvent.Type.CLIENT || tickEvent.type == TickEvent.Type.SERVER) && tickEvent.phase == TickEvent.Phase.END) {
            SubTileDryacinth.rainCache.clear();
        }
    }
}
