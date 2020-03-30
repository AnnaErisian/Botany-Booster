package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import blue.thejester.botanybooster.api.BaubleSlots;
import blue.thejester.botanybooster.entity.EntityBallosBall;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;

public class ItemLivingOil extends BaubleBaseItem implements IManaUsingItem {

    private static final int COST = 15;
    public static final String NAME = "living_oil";

    public ItemLivingOil() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onEntityDamaged(LivingHurtEvent event) {
        if(!event.getEntityLiving().world.isRemote
                && event.getEntityLiving() instanceof EntityPlayer
                && event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack crown = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleSlots.BODY);

            if(!crown.isEmpty() && crown.getItem() == this && ManaItemHandler.requestManaExact(crown, player, COST, false)) {
                EntityLivingBase vic = (EntityLivingBase) event.getSource().getTrueSource();
                vic.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, 1));
                Vec3d vec = player.getPositionVector().subtract(vic.getPositionVector());
                vic.knockBack(player, 0.7f, enforceNonZero(vec.x), enforceNonZero(vec.z));
            }
        }
    }

    private double enforceNonZero(double d) {
        if(Math.abs(d) >= 0.0001) {
            return d;
        } if(Math.abs(d) < 0.0001) {
            return 0.01 * Math.signum(d);
        } else {
            return 0.01;
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.BODY;
    }

    @Override
    public boolean usesMana(ItemStack itemStack) {
        return true;
    }
}
