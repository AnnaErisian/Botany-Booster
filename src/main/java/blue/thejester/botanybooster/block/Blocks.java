package blue.thejester.botanybooster.block;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.subtile.signature.BasicSignature;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public class Blocks {
    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> e) {
        BotaniaAPI.registerSubTile("dryacinth", SubTileDryacinth.class);
        BotaniaAPI.registerSubTileSignature(SubTileDryacinth.class, new BasicSignature("dryacinth") {
            @Override
            public String getUnlocalizedLoreTextForStack(ItemStack stack) {
                return "tile.botania.flower.dryacinth.lore";
            }
        });
        BotaniaAPI.addSubTileToCreativeMenu("dryacinth");
    }
}
