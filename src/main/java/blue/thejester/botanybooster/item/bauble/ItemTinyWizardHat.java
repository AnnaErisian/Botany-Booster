package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import com.google.common.base.Predicates;
import electroblob.wizardry.item.IManaStoringItem;
import electroblob.wizardry.util.WizardryUtilities;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.block.subtile.functional.SubTileHeiseiDream;

import java.util.List;

/**
 * The majority of the logic here is equivalent to the Ring of Chordata (ItemWaterRing) from Botania
 * And as such was written by Vazkii
 */
public class ItemTinyWizardHat extends BaubleBaseItem implements IManaUsingItem {

    private static final double SPEED_MULT = 1.2;
    private static final double MAX_SPEED = 1.3;

    public ItemTinyWizardHat() {
        super("tiny_wizard_hat");
    }

    @Override
    public void onWornTick(ItemStack hat, EntityLivingBase player) {
        super.onWornTick(hat, player);

        if (player.ticksExisted % 60 == 0 && player instanceof EntityPlayer) {
            EntityPlayer playerEnt = (EntityPlayer) player;
            for (ItemStack wand : WizardryUtilities.getPrioritisedHotbarAndOffhand(playerEnt)) {
                if (wand.getItem() instanceof IManaStoringItem && !((IManaStoringItem) wand.getItem()).isManaFull(wand)) {
                    final int cost = 100;
                    if(ManaItemHandler.requestManaExact(hat, playerEnt, cost, false)) {
                        ((IManaStoringItem) wand.getItem()).rechargeMana(wand, 1);
                        ManaItemHandler.requestManaExact(hat, playerEnt, cost, true);
                    }
                }
            }
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.HEAD;
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

}
