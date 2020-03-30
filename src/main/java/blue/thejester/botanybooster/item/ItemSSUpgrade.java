package blue.thejester.botanybooster.item;

import blue.thejester.botanybooster.BotanyBooster;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ICompositableLens;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.lens.ItemLens;

import java.util.List;

public class ItemSSUpgrade extends BaseItem {

    public static final String NAME = "second_sun_upgrade";

    public int tier = 0;

    public ItemSSUpgrade(int tier) {
        setRegistryName(new ResourceLocation(BotanyBooster.MODID, NAME + tier));
        setTranslationKey(NAME + tier);
        this.tier = tier;
    }
}
