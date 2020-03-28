package blue.thejester.botanybooster.item;

import baubles.api.IBauble;
import blue.thejester.botanybooster.BotanyBooster;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.client.render.IModelRegister;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public class BaseItem  extends Item implements IModelRegister{

    @Nonnull
    @Override
    public String getUnlocalizedNameInefficiently(@Nonnull ItemStack par1ItemStack) {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item\\.", "item." + BotanyBooster.MODID + ":");
    }

    @Override
    public void registerModels() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
