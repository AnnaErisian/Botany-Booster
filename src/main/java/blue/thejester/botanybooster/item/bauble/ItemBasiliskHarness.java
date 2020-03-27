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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemBasiliskHarness extends BaubleBaseItem {

    public ItemBasiliskHarness() {
        super("basilisk_harness");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityInteraction(PlayerInteractEvent.EntityInteractSpecific event) {
        if(!event.getEntityPlayer().world.isRemote) {
            EntityPlayer player = event.getEntityPlayer();
            ItemStack harness = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleSlots.BODY);

            if(!harness.isEmpty() && harness.getItem() == this) {
                if(event.getTarget() instanceof EntityAnimal) {
                    ((EntityAnimal) event.getTarget()).addPotionEffect(new PotionEffect(MobEffects.WITHER, 40, 0));
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDamaged(LivingHurtEvent event) {
        if(!event.getEntityLiving().world.isRemote
                && event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ItemStack harness = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleSlots.BODY);

            if(!harness.isEmpty() && harness.getItem() == this) {
                player.heal(Math.max(event.getAmount() * 0.166f, 1f));
            }
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.BODY;
    }

}
