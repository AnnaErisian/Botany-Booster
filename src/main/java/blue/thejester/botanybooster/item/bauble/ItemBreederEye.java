package blue.thejester.botanybooster.item.bauble;

import baubles.api.BaubleType;
import gnu.trove.list.array.TIntArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.Arrays;
import java.util.List;

/**
 * The majority of the logic here is equivalent to the Spectator (ItemItemFinder) from Botania
 * And as such was written by Vazkii
 */
public class ItemBreederEye extends BaubleBaseItem{

    private static final String TAG_ENTITY_POSITIONS = "highlightPositionsEnt";
    public static final String NAME = "eye_of_hathor";

    public ItemBreederEye() {
        super(NAME);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        super.onWornTick(stack, player);

        if(!(player instanceof EntityPlayer))
            return;

        if(player.world.isRemote)
            tickClient(stack, (EntityPlayer) player);
        else tickServer(stack, (EntityPlayer) player);
    }

    protected void tickClient(ItemStack stack, EntityPlayer player) {
        if(!Botania.proxy.isTheClientPlayer(player))
            return;

        Botania.proxy.setWispFXDepthTest(false);

        int[] entities = ItemNBTHelper.getIntArray(stack, TAG_ENTITY_POSITIONS);
        for(int i : entities) {
            Entity e =  player.world.getEntityByID(i);
            if(e != null && Math.random() < 0.6) {
                Botania.proxy.setWispFXDepthTest(Math.random() < 0.6);
                Botania.proxy.wispFX(e.posX + (float) (Math.random() * 0.5 - 0.25) * 0.45F, e.posY + e.height, e.posZ + (float) (Math.random() * 0.5 - 0.25) * 0.45F, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.15F + 0.05F * (float) Math.random(), -0.05F - 0.03F * (float) Math.random());
            }
        }

        Botania.proxy.setWispFXDepthTest(true);
    }

    protected void tickServer(ItemStack stack, EntityPlayer player) {
        TIntArrayList entPosBuilder = new TIntArrayList();
        NBTTagList blockPosBuilder = new NBTTagList();

        scanForBreedReady(player, entPosBuilder);

        int[] currentEnts = entPosBuilder.toArray();

        boolean entsEqual = Arrays.equals(currentEnts, ItemNBTHelper.getIntArray(stack, TAG_ENTITY_POSITIONS));

        if(!entsEqual) {
            ItemNBTHelper.setIntArray(stack, TAG_ENTITY_POSITIONS, currentEnts);
            BotaniaAPI.internalHandler.sendBaubleUpdatePacket(player, 4);
        }
    }

    private void scanForBreedReady(EntityPlayer player, TIntArrayList entIdBuilder) {
        if(player.isSneaking()) {
            int range = 24;

            List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range));
            for(Entity e : entities) {
                if(e == player)
                    continue;

                //instead of looking for items, we're looking for animals that can breed
                if(e instanceof EntityAnimal) {
                    EntityAnimal animal = (EntityAnimal) e;
                    if(animal.getGrowingAge() == 0 && !animal.isInLove()) {
                        entIdBuilder.add(e.getEntityId());
                    }
                }
            }
        }
    }


    @Override
    public BaubleType getBaubleType(ItemStack arg0) {
        return BaubleType.HEAD;
    }

}
