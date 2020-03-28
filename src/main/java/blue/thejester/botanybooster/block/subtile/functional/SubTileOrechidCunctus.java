package blue.thejester.botanybooster.block.subtile.functional;

import blue.thejester.botanybooster.config.BotanyBoosterConfig;
import blue.thejester.botanybooster.core.LexiconData;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileFunctional;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.subtile.functional.SubTileOrechid;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.core.handler.ModSounds;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SubTileOrechidCunctus extends SubTileFunctional {

    public static final String NAME = "orechidCunctus";
    public static LexiconEntry lexicon;

    private static final int COST = 17500;
    private static final int COST_GOG = 700;
    private static final int DELAY = 100;
    private static final int DELAY_GOG = 2;
    private static final int RANGE = 5;
    private static final int RANGE_Y = 3;

    private static final Map<String, Map<String, Integer>> oreMap = new HashMap<>();

    static {
        for(String row : BotanyBoosterConfig.orechidCunctusConfig) {
            String[] splits = row.split("\\|");
            String stone = splits[0];
            String ore = splits[1];
            Integer freq = Integer.parseInt(splits[2]);
            if(!oreMap.keySet().contains(stone)) {
                oreMap.put(stone, new HashMap<>());
            }
            oreMap.get(stone).put(ore, freq);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(supertile.getWorld().isRemote || redstoneSignal > 0 || !canOperate())
            return;

        int cost = getCost();
        if(mana >= cost && ticksExisted % getDelay() == 0) {
            Pair<BlockPos, String> coordsAndStone = getCoordsToPut();
            if(coordsAndStone != null) {
                BlockPos coords = coordsAndStone.first;
                String stone = coordsAndStone.second;
                ItemStack stack = getOreToPut(stone);
                if(!stack.isEmpty()) {
                    Block block = Block.getBlockFromItem(stack.getItem());
                    int meta = stack.getItemDamage();
                    supertile.getWorld().setBlockState(coords, block.getStateFromMeta(meta), 1 | 2);
                    if(ConfigHandler.blockBreakParticles)
                        supertile.getWorld().playEvent(2001, coords, Block.getIdFromBlock(block) + (meta << 12));
                    supertile.getWorld().playSound(null, supertile.getPos(), ModSounds.orechid, SoundCategory.BLOCKS, 2F, 1F);

                    mana -= cost;
                    sync();
                }
            }
        }
    }

    public ItemStack getOreToPut(String stoneType) {
        List<WeightedRandom.Item> values = new ArrayList<>();
        Map<String, Integer> map = getOreMap().get(stoneType);
        for(String s : map.keySet())
            values.add(new StringRandomItem(map.get(s), s));

        String ore = ((StringRandomItem) WeightedRandom.getRandomItem(supertile.getWorld().rand, values)).s;

        List<ItemStack> ores = OreDictionary.getOres(ore);

        for(ItemStack stack : ores) {
            Item item = stack.getItem();

            if(!(item instanceof ItemBlock))
                continue;

            return stack;
        }

        return getOreToPut(stoneType);
    }

    private Pair<BlockPos, String> getCoordsToPut() {
        List<Pair<BlockPos, String>> possibleCoords = new ArrayList<>();

        for(BlockPos pos : BlockPos.getAllInBox(getPos().add(-RANGE, -RANGE_Y, -RANGE), getPos().add(RANGE, RANGE_Y, RANGE))) {
            IBlockState state = supertile.getWorld().getBlockState(pos);
            if(state.getBlock().isReplaceableOreGen(state, supertile.getWorld(), pos, getReplaceMatcher()))
                possibleCoords.add(new Pair(pos, state.getBlock().getRegistryName().toString()));
        }

        if(possibleCoords.isEmpty())
            return null;
        return possibleCoords.get(supertile.getWorld().rand.nextInt(possibleCoords.size()));
    }

    public boolean canOperate() {
        return true;
    }

    public Map<String, Map<String, Integer>> getOreMap() {
        return SubTileOrechidCunctus.oreMap;
    }

    public Predicate<IBlockState> getReplaceMatcher() {
        return state -> oreMap.keySet().contains(state.getBlock().getRegistryName().toString());
    }

    public int getCost() {
        return Botania.gardenOfGlassLoaded ? COST_GOG : COST;
    }

    public int getDelay() {
        return Botania.gardenOfGlassLoaded ? DELAY_GOG : DELAY;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(toBlockPos(), RANGE);
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public int getColor() {
        return 0x818181;
    }

    @Override
    public int getMaxMana() {
        return getCost();
    }

    @Override
    public LexiconEntry getEntry() {
        return LexiconData.orechidCunctus;
    }

    private static class StringRandomItem extends WeightedRandom.Item {

        public final String s;

        public StringRandomItem(int par1, String s) {
            super(par1);
            this.s = s;
        }

    }

    private static class Pair<K,V> {
        K first;
        V second;
        public Pair(K a, V b) {
            first = a;
            second = b;
        }
    }

}
