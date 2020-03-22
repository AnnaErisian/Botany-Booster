package blue.thejester.botanybooster.core;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import blue.thejester.botanybooster.client.RenderLongCorporeaSpark;
import blue.thejester.botanybooster.entity.EntityLongCorporeaSpark;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.BotaniaAPIClient;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public class ClientOnlyProxy extends CommonProxy {

    @Override
    public void preInit() {

        RenderingRegistry.registerEntityRenderingHandler(EntityLongCorporeaSpark.class, RenderLongCorporeaSpark::new);
        MinecraftForge.EVENT_BUS.register(MiscellaneousIcons.INSTANCE);

        super.preInit();
    }



    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent e) {
        BotaniaAPIClient.registerSubtileModel(SubTileDryacinth.class, new ModelResourceLocation(BotanyBooster.MODID + ":dryacinth"));
    }

    @Override
    public boolean isDedicatedServer() {
        return false;
    }

}
