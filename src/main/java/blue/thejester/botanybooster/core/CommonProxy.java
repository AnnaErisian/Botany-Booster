package blue.thejester.botanybooster.core;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import blue.thejester.botanybooster.block.subtile.functional.SubTileOrechidCunctus;
import blue.thejester.botanybooster.entity.EntityBallosBall;
import blue.thejester.botanybooster.entity.EntityLongCorporeaSpark;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.signature.BasicSignature;

public class CommonProxy {

    public static ItemStack dryacinth;
    public static ItemStack orechidCunctus;

    /**
     * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
     */
    public void preInit() {
        ResourceLocation lrcs_name = new ResourceLocation(BotanyBooster.MODID, "long-range-corporea-spark");
        EntityRegistry.registerModEntity(lrcs_name, EntityLongCorporeaSpark.class, "long-range-corporea-spark", 0, BotanyBooster.instance, 64, 10, false);
        ResourceLocation ballos_ball_name = new ResourceLocation(BotanyBooster.MODID, "ballos-ball");
        EntityRegistry.registerModEntity(ballos_ball_name, EntityBallosBall.class, "ballos-ball", 1, BotanyBooster.instance, 16, 3, true);
    }

    /**
     * Do your mod setup. Build whatever data structures you care about. Register recipes,
     * send FMLInterModComms messages to other mods.
     */
    public void init() {
        SubTileDryacinth.lexicon = new LexiconEntry("dryacinth", BotaniaAPI.categoryGenerationFlowers);
        SubTileDryacinth.lexicon.setLexiconPages(
                BotaniaAPI.internalHandler.textPage("botania.lexicon.dryacinth.0")
        );
        SubTileDryacinth.lexicon.setIcon(dryacinth);

        SubTileOrechidCunctus.lexicon = new LexiconEntry("orechidCunctus", BotaniaAPI.categoryGenerationFlowers);
        SubTileOrechidCunctus.lexicon.setLexiconPages(
                BotaniaAPI.internalHandler.textPage("botania.lexicon.orechidCunctus.0")
        );
        SubTileOrechidCunctus.lexicon.setIcon(orechidCunctus);
    }

    /**
     * Handle interaction with other mods, complete your setup based on this.
     */
    public void postInit() {

    }

    /**
     * is this a dedicated server?
     *
     * @return true if this is a dedicated server, false otherwise
     */
    public boolean isDedicatedServer() {
        return true;
    }

    public void registerFluidModels(Fluid fluid) {

    }
}
