package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import blue.thejester.botanybooster.api.BaubleSlots;
import blue.thejester.botanybooster.entity.EntityBallosBall;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaUsingItem;

public class ItemFastEatCharm extends BaubleBaseItem implements IManaUsingItem {

    public ItemFastEatCharm() {
        super("consumption_charm");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onItemUse (LivingEntityUseItemEvent.Tick event) {
        if (event.getEntityLiving() instanceof EntityPlayer && event.getItem().getItemUseAction() == EnumAction.EAT) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack charm = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleSlots.CHARM);

            if(!charm.isEmpty() && charm.getItem() == this) {
                event.setDuration(0);
            }
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
