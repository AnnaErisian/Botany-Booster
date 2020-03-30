package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import blue.thejester.botanybooster.api.BaubleSlots;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.entity.EntityMagicMissile;

import java.util.List;

public class ItemCoatOfArms extends BaubleBaseItem {

    private static final int COST = 40;
    private static final double RANGE = 5;
    public static final String NAME = "coat_of_arms";

    public ItemCoatOfArms() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void hurtEntity(LivingHurtEvent evt) {
        if (evt.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) evt.getSource().getTrueSource();
            if(!player.world.isRemote) {
                List nearbyPlayers = player.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.posX - RANGE, player.posY - RANGE, player.posZ - RANGE, player.posX + RANGE, player.posY + RANGE, player.posZ + RANGE), Predicates.instanceOf(EntityPlayer.class));
                int count = Math.max(0, nearbyPlayers.size() - 1);
                evt.setAmount(evt.getAmount() + count * 2);
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

}
