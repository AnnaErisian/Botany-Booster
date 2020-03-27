package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.api.BaubleSlots;
import blue.thejester.botanybooster.entity.EntityBallosBall;
import electroblob.wizardry.item.IManaStoringItem;
import electroblob.wizardry.util.WizardryUtilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;
import vazkii.botania.api.item.IPixieSpawner;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.helper.PlayerHelper;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.armor.elementium.ItemElementiumHelm;

/**
 * The majority of the logic here is equivalent to the PixieHandler from Botania
 * And as such was written by Vazkii
 */
public class ItemBallosCrown extends BaubleBaseItem {

    public ItemBallosCrown() {
        super("ballos_crown");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityDamaged(LivingHurtEvent event) {
        if(!event.getEntityLiving().world.isRemote
                && event.getEntityLiving() instanceof EntityPlayer
                && event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack crown = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleSlots.HEAD);

            if(!crown.isEmpty() && crown.getItem() == this) {
                if(Math.random() < 0.25) {
                    EntityBallosBall pixie = new EntityBallosBall(player.world);
                    pixie.setPosition(player.posX, player.posY + 2, player.posZ);
                    pixie.setProps((EntityLivingBase) event.getSource().getTrueSource(), player, 0, 4);
                    pixie.onInitialSpawn(player.world.getDifficultyForLocation(new BlockPos(pixie)), null);
                    player.world.spawnEntity(pixie);
                }
            }
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.HEAD;
    }

}
