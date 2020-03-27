package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import blue.thejester.botanybooster.api.BaubleSlots;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.bauble.ItemTravelBelt;

public class ItemSuperHopBelt extends BaubleBaseItem implements IManaUsingItem {

    private static final double LEAP_FACTOR = 0.6;
    private static final int COST = 3;

    public ItemSuperHopBelt() {
        super("belt_leaping");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack belt = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleSlots.BELT);

            if(!belt.isEmpty() && belt.getItem() == this) {
                if(!belt.isEmpty() && belt.getItem() instanceof ItemSuperHopBelt && ManaItemHandler.requestManaExact(belt, player, COST, false)) {
                    double x = player.getLookVec().x;
                    double z = player.getLookVec().z;
                    double c = Math.sqrt(x*x + z*z);
                    if(Math.abs(c) > 0.0001) {
                        double xfactor = x / c;
                        double zfactor = z / c;
                        player.motionX += LEAP_FACTOR * xfactor;
                        player.motionZ += LEAP_FACTOR * zfactor;
                    }
                    player.motionY += 0.4;
                    player.fallDistance = -5;
                    ManaItemHandler.requestManaExact(belt, player, COST, true);
                }
            }
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
