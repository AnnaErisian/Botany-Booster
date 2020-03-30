package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import com.google.common.base.Predicates;
import com.google.common.util.concurrent.ListenableFutureTask;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.block.subtile.functional.SubTileHeiseiDream;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.item.equipment.bauble.ItemWaterRing;
import vazkii.botania.common.network.PacketBotaniaEffect;
import vazkii.botania.common.network.PacketHandler;

import java.util.Collections;
import java.util.List;

/**
 * The majority of the logic here is equivalent to the Ring of Chordata (ItemWaterRing) from Botania
 * And as such was written by Vazkii
 */
public class ItemCrownOfAtlantis extends BaubleBaseItem implements IManaUsingItem {

    private static final double SPEED_MULT = 1.2;
    private static final double MAX_SPEED = 1.3;
    public static final String NAME = "crown_of_atlantis";

    public ItemCrownOfAtlantis() {
        super(NAME);
    }
    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        super.onWornTick(stack, player);

        if(player.isInsideOfMaterial(Material.WATER)) {
            double motionX = player.motionX * SPEED_MULT;
            double motionY = player.motionY * SPEED_MULT;
            double motionZ = player.motionZ * SPEED_MULT;

            boolean flying = player instanceof EntityPlayer && ((EntityPlayer) player).capabilities.isFlying;

            if(Math.abs(motionX) < MAX_SPEED && !flying)
                player.motionX = motionX;
            if(Math.abs(motionY) < MAX_SPEED && !flying)
                player.motionY = motionY;
            if(Math.abs(motionZ) < MAX_SPEED && !flying)
                player.motionZ = motionZ;

            PotionEffect effect = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
            if(effect == null) {
                PotionEffect neweffect = new PotionEffect(MobEffects.NIGHT_VISION, Integer.MAX_VALUE, -62, true, true);
                player.addPotionEffect(neweffect);
            }

            if(player.getAir() <= 1 && player instanceof EntityPlayer) {
                int mana = ManaItemHandler.requestMana(stack, (EntityPlayer) player, 300, true);
                if (mana > 0)
                    player.setAir(mana);
            }

            if(player.ticksExisted % 20 == 0 && player instanceof EntityPlayer) {
                EntityPlayer playerEnt = (EntityPlayer) player;
                final int cost = 25;
                if(ManaItemHandler.requestManaExact(stack, playerEnt, cost, false)) {
                    final int range = 35;

                    List mobs = player.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range), Predicates.instanceOf(EntityGuardian.class));
                    if(mobs.size() >= 2) {
                        //pick one randomly
                        EntityGuardian guardianOne = (EntityGuardian) mobs.remove((int)Math.random() * mobs.size());

                        if(SubTileHeiseiDream.brainwashEntity(guardianOne, mobs)) {
                            ManaItemHandler.requestManaExact(stack, playerEnt, cost, true);
                        }
                    }
                }
            }
        } else onUnequipped(stack, player);
    }


    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        PotionEffect effect = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
        if(effect != null && effect.getAmplifier() == -62)
            player.removePotionEffect(MobEffects.NIGHT_VISION);
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
