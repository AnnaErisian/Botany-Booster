package blue.thejester.botanybooster.item;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.item.bauble.*;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
    public static final Item consumptionCharm = new ItemFastEatCharm();
    public static final Item ballosCrown = new ItemBallosCrown();
    public static final Item fairySignet = new ItemFairySignet();
    public static final Item leapBelt = new ItemSuperHopBelt();
    public static final Item argentumShoulderpad = new ItemArgentumArmor();
    public static final Item basiliskHarness = new ItemBasiliskHarness();
    public static final Item livingOil = new ItemLivingOil();
    public static final Item coatOfArms = new ItemCoatOfArms();
    public static final Item stabilizingSash = new ItemStabilizingSash();
    public static final Item tinyWizardHat;
    static {
        if(Loader.isModLoaded("ebwizardry")) {
            tinyWizardHat = new ItemTinyWizardHat();
        } else {
            tinyWizardHat = null;
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> evt) {
        registerItem(evt, lcs);
        registerItem(evt, launchLens);
        registerItem(evt, flyLens);
        registerItem(evt, crownOfAtlantis);
        registerItem(evt, tinyWizardHat);
        registerItem(evt, ballosCrown);
        registerItem(evt, eyeOfHathor);
        registerItem(evt, consumptionCharm);
        registerItem(evt, fairySignet);
        registerItem(evt, leapBelt);
        registerItem(evt, argentumShoulderpad);
        registerItem(evt, basiliskHarness);
        registerItem(evt, livingOil);
        registerItem(evt, coatOfArms);
        registerItem(evt, stabilizingSash);
    }

    private static void registerItem(RegistryEvent.Register<Item> evt, Item item) {
        if(item != null) {
            evt.getRegistry().register(item);
        }
    }
}
