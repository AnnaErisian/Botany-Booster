package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import blue.thejester.botanybooster.api.BaubleSlots;
import blue.thejester.botanybooster.entity.EntityBallosBall;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.entity.EntityMagicMissile;

public class ItemArgentumArmor extends BaubleBaseItem implements IManaUsingItem {

    private static final int COST = 80;

    public ItemArgentumArmor() {
        super("argentum_shoulderpad");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getEntityPlayer().world.isRemote) {
            EntityPlayer player = evt.getEntityPlayer();
            ItemStack body = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleSlots.BODY);
            if (!body.isEmpty() && body.getItem() == this && player.getCooledAttackStrength(0) == 1 && ManaItemHandler.requestManaExact(body, player, COST, false)) {
                EntityMagicMissile missile = new EntityMagicMissile(player, false);
                missile.setPosition(player.posX, player.posY + 2.4, player.posZ);
                Entity target = evt.getTarget();

                if (target instanceof EntityLivingBase
                        && target != null && ((EntityLivingBase) target).getHealth() > 0
                        && !target.isDead
                        && player.world.loadedEntityList.contains(target)) {
                    missile.setTarget((EntityLivingBase) target);
                }
                if (missile.findTarget()) {
                    missile.playSound(ModSounds.missile, 0.6F, 0.8F + (float) Math.random() * 0.2F);
                    player.world.spawnEntity(missile);
                    ManaItemHandler.requestManaExact(body, player, COST, true);
                }
            }
        }
    }

    /**
     * This code is from Botania's ItemMissileRod
     * and as such was written by Vazkii
     */
    public boolean spawnMissile(World world, EntityLivingBase thrower, double x, double y, double z) {

        return false;
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.BODY;
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

}
