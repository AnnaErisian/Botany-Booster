package blue.thejester.botanybooster.block.subtile.functional;

import blue.thejester.botanybooster.core.LexiconData;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.core.handler.ModSounds;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SubTileDryacinth extends SubTileFunctional {

    public static final String NAME = "dryacinth";
    private static String TAG_ACTIVE = "isRainShielding";

    public SubTileDryacinth() {
        synchronized (shields) {
            shields.add(this);
        }
        this.active = false;
    }


    public static Set<SubTileDryacinth> shields = Collections.newSetFromMap(new WeakHashMap());

    public static ConcurrentHashMap<BlockPos, Boolean> rainCache = new ConcurrentHashMap<>();

    public boolean active = true;



    @Override
    public void writeToPacketNBT(NBTTagCompound cmp) {
        super.writeToPacketNBT(cmp);
        cmp.setBoolean(TAG_ACTIVE, active);
    }

    @Override
    public void readFromPacketNBT(NBTTagCompound cmp) {
        super.readFromPacketNBT(cmp);
        active = cmp.getBoolean(TAG_ACTIVE);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(supertile.getWorld().isRemote)
            return;

        if(ticksExisted % 10 == 0) {
            boolean oldActive = active;
            active = false;
            if (mana > 1 && redstoneSignal == 0) {
                mana -= 1;
                active = true;
            }
            if(oldActive != active || ticksExisted % 200 == 0) {
                sync();
            }
        }
    }

    @Override
    public int getMaxMana() {
        return 500;
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public int getColor() {
        return 0x11FF00;
    }

    @Override
    public LexiconEntry getEntry() {
        return LexiconData.dryacinth;
    }

    public static boolean shouldRain(World world, BlockPos pos)
    {
        Boolean cachedValue;
        if ((cachedValue = rainCache.get(pos)) != null)
        {
            return cachedValue;
        }

        synchronized (shields)
        {
            for (SubTileDryacinth rainShield : shields)
            {
                if (rainShield.active && rainShield.getWorld() == world && rainShield.getPos().add(0, -rainShield.getPos().getY(), 0).distanceSq(pos) < (100) * (100))
                {
                    rainCache.put(pos, Boolean.FALSE);
                    return false;
                }
            }
        }

        rainCache.put(pos, Boolean.TRUE);
         return true;
    }
}
