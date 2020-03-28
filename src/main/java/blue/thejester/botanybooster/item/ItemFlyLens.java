package blue.thejester.botanybooster.item;

import blue.thejester.botanybooster.BotanyBooster;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
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

public class ItemFlyLens extends BaseItem implements ICompositableLens {

    public ItemFlyLens() {
        setRegistryName(new ResourceLocation(BotanyBooster.MODID, "flyLens"));
        setTranslationKey("flyLens");
    }
    @Override
    public int getProps(ItemStack itemStack) {
        return 0; //PROP_NONE
    }

    @Override
    public boolean isCombinable(ItemStack itemStack) {
        return itemStack.getItemDamage() != ItemLens.NORMAL;
    }

    @Override
    public int getLensColor(ItemStack itemStack) {
        return 0xFFFFFF;
    }

    @Override
    public boolean canCombineLenses(ItemStack sourceLens, ItemStack compositeLens) {
        ICompositableLens sourceItem = (ICompositableLens) sourceLens.getItem();
        ICompositableLens compositeItem = (ICompositableLens) compositeLens.getItem();
        if(sourceItem == compositeItem && sourceLens.getItemDamage() == compositeLens.getItemDamage())
            return false;

        if(!sourceItem.isCombinable(sourceLens) || !compositeItem.isCombinable(compositeLens))
            return false;

        return true;
    }

    @Override
    public ItemStack getCompositeLens(ItemStack itemStack) {
        NBTTagCompound cmp = ItemNBTHelper.getCompound(itemStack, "compositeLens", true);
        if(cmp == null)
            return ItemStack.EMPTY;
        else return new ItemStack(cmp);
    }

    @Override
    public ItemStack setCompositeLens(ItemStack sourceLens, ItemStack compositeLens) {
        if(!compositeLens.isEmpty()) {
            NBTTagCompound cmp = compositeLens.writeToNBT(new NBTTagCompound());
            ItemNBTHelper.setCompound(sourceLens, "compositeLens", cmp);
        }
        return sourceLens;
    }

    @Override
    public void apply(ItemStack itemStack, BurstProperties burstProperties) {
        burstProperties.maxMana *= 3;
        burstProperties.ticksBeforeManaLoss = 80;
        burstProperties.manaLossPerTick *= 10;
    }

    @Override
    public boolean collideBurst(IManaBurst iManaBurst, RayTraceResult rayTraceResult, boolean b, boolean b1, ItemStack itemStack) {
        return false;
    }

    @Override
    public void updateBurst(IManaBurst burst, ItemStack stack) {
        EntityThrowable entity = (EntityThrowable) burst;
        if(!burst.isFake()) {
            double range = 3.5;
            AxisAlignedBB bounds = new AxisAlignedBB(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range);
            List<Entity> movables = entity.world.getEntitiesWithinAABB(EntityPlayer.class, bounds);
            for(Entity movable : movables) {
                if(movable == burst)
                    continue;

                if(!movable.isSneaking() && movable.equals(((EntityThrowable) burst).getThrower())) {
                    movable.motionX = entity.motionX;
                    movable.motionY = entity.motionY;
                    movable.motionZ = entity.motionZ;
                    double xd = -(movable.posX - ((EntityThrowable) burst).posX);
                    double yd = -(movable.posY - ((EntityThrowable) burst).posY);
                    double zd = -(movable.posZ - ((EntityThrowable) burst).posZ);
                    movable.motionX += xd/8;
                    movable.motionY += yd/8;
                    movable.motionZ += zd/8;
                    ((EntityPlayer)movable).velocityChanged = true;
                }
            }
        }
    }

    @Override
    public boolean doParticles(IManaBurst iManaBurst, ItemStack itemStack) {
        return true;
    }
}
