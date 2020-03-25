package blue.thejester.botanybooster.item;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.item.bauble.ItemBallosCrown;
import blue.thejester.botanybooster.item.bauble.ItemBreederEye;
import blue.thejester.botanybooster.item.bauble.ItemCrownOfAtlantis;
import blue.thejester.botanybooster.item.bauble.ItemTinyWizardHat;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.common.item.equipment.bauble.ItemWaterRing;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public class Items {
    public static final Item lcs = new ItemLongCorporeaSpark();
    public static final Item launchLens = new ItemLaunchLens();
    public static final Item flyLens = new ItemFlyLens();
    public static final Item crownOfAtlantis = new ItemCrownOfAtlantis();
    public static final Item eyeOfHathor = new ItemBreederEye();
    public static final Item tinyWizardHat;
    static {
        if(Loader.isModLoaded("ebwizardry")) {
            tinyWizardHat = new ItemTinyWizardHat();
        } else {
            tinyWizardHat = null;
        }
    }
    public static final Item ballosCrown = new ItemBallosCrown();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> evt) {
        registerItem(evt, lcs);
        registerItem(evt, launchLens);
        registerItem(evt, flyLens);
        registerItem(evt, crownOfAtlantis);
        registerItem(evt, tinyWizardHat);
        registerItem(evt, ballosCrown);
        registerItem(evt, eyeOfHathor);
    }

    private static void registerItem(RegistryEvent.Register<Item> evt, Item item) {
        if(item != null) {
            evt.getRegistry().register(item);
        }
    }
}
