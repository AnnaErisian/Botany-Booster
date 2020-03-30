package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import blue.thejester.botanybooster.api.BaubleSlots;
import blue.thejester.botanybooster.entity.EntityBallosBall;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemFairySignet extends BaubleBaseItem implements IManaUsingItem {

    private static final int COST = 200;
    public static final String NAME = "fairy_signet";

    public ItemFairySignet() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onProjectileHit(ProjectileImpactEvent event) {
        if(event.getRayTraceResult().typeOfHit == RayTraceResult.Type.ENTITY && event.getRayTraceResult().entityHit instanceof EntityPlayer) {
//            if(!event.getRayTraceResult().entityHit.world.isRemote) {
                EntityPlayer player = (EntityPlayer) event.getRayTraceResult().entityHit;
                ItemStack charm = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleSlots.CHARM);
                if(!charm.isEmpty() && charm.getItem() == this &&  ManaItemHandler.requestManaExact(charm, player, COST, false)) {
                    Entity projectile = event.getEntity();
                    projectile.motionX *= -1;
                    projectile.motionY *= -1;
                    projectile.motionZ *= -1;
                    if(event.isCancelable()) {
                        event.setCanceled(true);
                    }
                    ManaItemHandler.requestManaExact(charm, player, COST, true);
                }
//            }
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.CHARM;
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }

}
