package blue.thejester.botanybooster.asm.handler;

import blue.thejester.botanybooster.block.subtile.functional.SubTileDryacinth;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AsmHandler {

    public static boolean shouldRain(World worldObj, BlockPos pos)
    {
        return SubTileDryacinth.shouldRain(worldObj, pos.add(0, -pos.getY(), 0));
    }

    public static boolean canSnowAt(World worldObj, BlockPos pos)
    {
        return SubTileDryacinth.shouldRain(worldObj, pos.add(0, -pos.getY(), 0));
    }
}
