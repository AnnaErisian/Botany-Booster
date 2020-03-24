package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import blue.thejester.botanybooster.BotanyBooster;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BotanyBooster.MODID)
public abstract class BaubleBaseItem extends Item implements IBauble {

    public BaubleBaseItem(String name) {
        setRegistryName(new ResourceLocation(BotanyBooster.MODID, name));
        setTranslationKey(name);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return null;
    }
}

//setRegistryName(new ResourceLocation(LibMisc.MOD_ID, name));        setTranslationKey(name);