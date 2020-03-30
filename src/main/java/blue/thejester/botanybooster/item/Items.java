package blue.thejester.botanybooster.item;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.item.bauble.*;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public class Items {
    public static final Item longRangeCorporeaSpark = new ItemLongCorporeaSpark();
    public static final Item launchLens = new ItemLaunchLens();
    public static final Item flyLens = new ItemFlyLens();
    public static final Item ssUpgrade0 = new ItemSSUpgrade(0);
    public static final Item ssUpgrade1 = new ItemSSUpgrade(1);
    public static final Item ssUpgrade2 = new ItemSSUpgrade(2);
    public static final Item ssUpgrade3 = new ItemSSUpgrade(3);
    public static final Item ssUpgrade4 = new ItemSSUpgrade(4);
    public static final Item ssUpgrade5 = new ItemSSUpgrade(5);
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
        registerItem(evt, longRangeCorporeaSpark);

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

        registerItem(evt, ssUpgrade0);
        registerItem(evt, ssUpgrade1);
        registerItem(evt, ssUpgrade2);
        registerItem(evt, ssUpgrade3);
        registerItem(evt, ssUpgrade4);
        registerItem(evt, ssUpgrade5);
    }

    private static void registerItem(RegistryEvent.Register<Item> evt, Item item) {
        if(item != null) {
            evt.getRegistry().register(item);
        }
    }
}
