package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import blue.thejester.botanybooster.potion.ModPotions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;

/**
 * This is pretty much the same as the Silent Eternity from ExtraBotany
 * But way less OP (still pretty OP)
 */

public class ItemStabilizingSash extends BaubleBaseItem implements IManaUsingItem {

    private static final String TAG_SAVED_X = "spx";
    private static final String TAG_SAVED_Y = "spy";
    private static final String TAG_SAVED_Z = "spz";
    private static final String TAG_STOPED_TICKS = "sticks";

    private static final int COST = 1;
    private static final int COST_INTERVAL = 3;


    public ItemStabilizingSash() {
        super("stabilizing_sash");
    }

    @Override
    public void onWornTick(ItemStack belt, EntityLivingBase entity) {
        super.onWornTick(belt, entity);
        if(!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if(!entity.world.isRemote) {
            if(closeEnough(belt, player)) {
                setStopeedTicks(belt, getStoppedTicks(belt)+1);
                if(getStoppedTicks(belt) > 72 && ManaItemHandler.requestManaExact(belt, player, COST, false)) {
                    player.addPotionEffect(new PotionEffect(ModPotions.stabilized, 10));
                    if (player.ticksExisted % 3 == 0) {
                        ManaItemHandler.requestManaExact(belt, player, COST, true);
                        if(player.ticksExisted % 18 == 0){
                            player.setHealth(Math.min(player.getMaxHealth(), player.getHealth()+0.5F));
                        }
                    }

                }
            }else
                setStopeedTicks(belt, 0);
            saveXPos(belt,player.lastTickPosX);
            saveYPos(belt,player.lastTickPosY);
            saveZPos(belt,player.lastTickPosZ);
        }
    }

    private boolean closeEnough(ItemStack belt, EntityPlayer player) {
        return Math.abs(getSavedX(belt) - player.posX) < 0.01
                && Math.abs(getSavedY(belt) - player.posY) < 0.01
                && Math.abs(getSavedZ(belt) - player.posZ) < 0.01;
    }

    public double getSavedX(ItemStack stack) {
        return ItemNBTHelper.getDouble(stack, TAG_SAVED_X, 0);
    }

    public double getSavedY(ItemStack stack) {
        return ItemNBTHelper.getDouble(stack, TAG_SAVED_Y, 0);
    }

    public double getSavedZ(ItemStack stack) {
        return ItemNBTHelper.getDouble(stack, TAG_SAVED_Z, 0);
    }

    public void saveXPos(ItemStack stack, double d) {
        ItemNBTHelper.setDouble(stack, TAG_SAVED_X, d);
    }

    public void saveYPos(ItemStack stack, double d) {
        ItemNBTHelper.setDouble(stack, TAG_SAVED_Y, d);
    }

    public void saveZPos(ItemStack stack, double d) {
        ItemNBTHelper.setDouble(stack, TAG_SAVED_Z, d);
    }

    public int getStoppedTicks(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_STOPED_TICKS, 0);
    }

    public void setStopeedTicks(ItemStack stack, int i) {
        ItemNBTHelper.setInt(stack, TAG_STOPED_TICKS, i);
    }

    @Override
    public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
        if(!player.world.isRemote) {
            Multimap<String, AttributeModifier> attributes = HashMultimap.create();
            if(!stack.isEmpty()) {
                attributes.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(getBaubleUUID(stack), "STABILIZINGSASH", 1, 0).setSaved(false));
            }
            player.getAttributeMap().applyAttributeModifiers(attributes);
        }
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        if(!player.world.isRemote) {
            Multimap<String, AttributeModifier> attributes = HashMultimap.create();
            if(!stack.isEmpty()) {
                attributes.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(getBaubleUUID(stack), "STABILIZINGSASH", 1, 0).setSaved(false));
            }
            player.getAttributeMap().removeAttributeModifiers(attributes);
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.BELT;
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

}
