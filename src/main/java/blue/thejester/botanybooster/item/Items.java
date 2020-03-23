package blue.thejester.botanybooster.item;

import blue.thejester.botanybooster.BotanyBooster;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public class Items {
    public static final Item lcs = new ItemLongCorporeaSpark();
    public static final Item launchLens = new ItemLaunchLens();
    public static final Item flyLens = new ItemFlyLens();
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> evt) {
        evt.getRegistry().register(lcs);
        evt.getRegistry().register(launchLens);
        evt.getRegistry().register(flyLens);
    }
}
