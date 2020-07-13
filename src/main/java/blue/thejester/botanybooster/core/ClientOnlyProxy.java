package blue.thejester.botanybooster.core;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import blue.thejester.botanybooster.block.subtile.functional.SubTileOrechidCunctus;
import blue.thejester.botanybooster.block.tile.TileArrayedCrystalCube;
import blue.thejester.botanybooster.block.tile.TileSecondSun;
import blue.thejester.botanybooster.client.RenderBallosBall;
import blue.thejester.botanybooster.client.RenderLongCorporeaSpark;
import blue.thejester.botanybooster.client.RenderTileArrayedCrystalCube;
import blue.thejester.botanybooster.client.RenderTileSecondSun;
import blue.thejester.botanybooster.entity.EntityBallosBall;
import blue.thejester.botanybooster.entity.EntityLongCorporeaSpark;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.BotaniaAPIClient;

public class ClientOnlyProxy extends CommonProxy {

    @Override
    public void preInit() {

        RenderingRegistry.registerEntityRenderingHandler(EntityLongCorporeaSpark.class, RenderLongCorporeaSpark::new);
        MinecraftForge.EVENT_BUS.register(MiscellaneousIcons.INSTANCE);

        ClientRegistry.bindTileEntitySpecialRenderer(TileArrayedCrystalCube.class, new RenderTileArrayedCrystalCube());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSecondSun.class, new RenderTileSecondSun());

        RenderingRegistry.registerEntityRenderingHandler(EntityBallosBall.class, RenderBallosBall::new);

        super.preInit();
    }

    @Override
    public boolean isDedicatedServer() {
        return false;
    }

}
