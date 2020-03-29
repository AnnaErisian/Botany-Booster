package blue.thejester.botanybooster.block;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import blue.thejester.botanybooster.block.subtile.functional.SubTileOrechidCunctus;
import blue.thejester.botanybooster.block.tile.TileArrayedCrystalCube;
import blue.thejester.botanybooster.block.tile.TileSecondSun;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.signature.BasicSignature;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.item.block.ItemBlockMod;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public class Blocks {

    public static final Block corporeaArrayedCrystalCube = new BlockArrayedCrystalCube();
    public static final Block secondSunCore0 = new BlockSecondSunCore(0);
    public static final Block secondSunCore1 = new BlockSecondSunCore(1);
    public static final Block secondSunCore2 = new BlockSecondSunCore(2);
    public static final Block secondSunCore3 = new BlockSecondSunCore(3);
    public static final Block secondSunCore4 = new BlockSecondSunCore(4);
    public static final Block secondSunCore5 = new BlockSecondSunCore(5);
    public static final Block secondSunCore6 = new BlockSecondSunCore(6);

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> e) {
        BotaniaAPI.registerSubTile(SubTileDryacinth.NAME, SubTileDryacinth.class);
        BotaniaAPI.registerSubTileSignature(SubTileDryacinth.class, new BasicSignature(SubTileDryacinth.NAME) {
            @Override
            public String getUnlocalizedLoreTextForStack(ItemStack stack) {
                return "tile.botania.flower.dryacinth.lore";
            }
        });
        BotaniaAPI.addSubTileToCreativeMenu(SubTileDryacinth.NAME);

        BotaniaAPI.registerSubTile(SubTileOrechidCunctus.NAME, SubTileOrechidCunctus.class);
        BotaniaAPI.registerSubTileSignature(SubTileOrechidCunctus.class, new BasicSignature(SubTileOrechidCunctus.NAME) {
            @Override
            public String getUnlocalizedLoreTextForStack(ItemStack stack) {
                return "tile.botania.flower.orechidCunctus.lore";
            }
        });
        BotaniaAPI.addSubTileToCreativeMenu(SubTileOrechidCunctus.NAME);

        e.getRegistry().register(corporeaArrayedCrystalCube);
        e.getRegistry().register(secondSunCore0);
        e.getRegistry().register(secondSunCore1);
        e.getRegistry().register(secondSunCore2);
        e.getRegistry().register(secondSunCore3);
        e.getRegistry().register(secondSunCore4);
        e.getRegistry().register(secondSunCore5);
        e.getRegistry().register(secondSunCore6);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> evt) {
        evt.getRegistry().register(new ItemBlockMod(corporeaArrayedCrystalCube).setRegistryName(corporeaArrayedCrystalCube.getRegistryName()));
        GameRegistry.registerTileEntity(TileArrayedCrystalCube.class, "botanybooster:arrayed_crystal_cube");
        evt.getRegistry().register(new ItemBlockMod(secondSunCore0).setRegistryName(secondSunCore0.getRegistryName()));
        evt.getRegistry().register(new ItemBlockMod(secondSunCore1).setRegistryName(secondSunCore1.getRegistryName()));
        evt.getRegistry().register(new ItemBlockMod(secondSunCore2).setRegistryName(secondSunCore2.getRegistryName()));
        evt.getRegistry().register(new ItemBlockMod(secondSunCore3).setRegistryName(secondSunCore3.getRegistryName()));
        evt.getRegistry().register(new ItemBlockMod(secondSunCore4).setRegistryName(secondSunCore4.getRegistryName()));
        evt.getRegistry().register(new ItemBlockMod(secondSunCore5).setRegistryName(secondSunCore5.getRegistryName()));
        evt.getRegistry().register(new ItemBlockMod(secondSunCore6).setRegistryName(secondSunCore6.getRegistryName()));
        GameRegistry.registerTileEntity(TileSecondSun.class, "botanybooster:" + BlockSecondSunCore.NAME);
    }
}
