package blue.thejester.botanybooster.block;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import blue.thejester.botanybooster.block.subtile.functional.SubTileOrechidCunctus;
import blue.thejester.botanybooster.block.tile.TileArrayedCrystalCube;
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
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> evt) {
        evt.getRegistry().register(new ItemBlockMod(corporeaArrayedCrystalCube).setRegistryName(corporeaArrayedCrystalCube.getRegistryName()));
        GameRegistry.registerTileEntity(TileArrayedCrystalCube.class, "botanybooster:arrayed_crystal_cube");
    }
}
