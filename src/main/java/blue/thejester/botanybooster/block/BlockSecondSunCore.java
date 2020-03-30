package blue.thejester.botanybooster.block;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.tile.TileSecondSun;
import blue.thejester.botanybooster.core.LexiconData;
import blue.thejester.botanybooster.item.ItemSSUpgrade;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.client.core.handler.ModelHandler;
import vazkii.botania.client.render.IModelRegister;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockSecondSunCore extends Block implements IWandHUD, ILexiconable, IWandable, IModelRegister {

    public static final String NAME = "second_sun_core";

    public int tier;

    public BlockSecondSunCore(int tier) {
        super(Material.ROCK);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.STONE);
        setTranslationKey(NAME + tier);
        setRegistryName(new ResourceLocation(BotanyBooster.MODID, NAME + tier));
        setLightLevel(1);
        this.tier = tier;
    }

    public BlockSecondSunCore() {
        this(0);
    }

    @Deprecated
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public LexiconEntry getEntry(World world, BlockPos blockPos, EntityPlayer entityPlayer, ItemStack itemStack) {
        return LexiconData.secondSun;
    }

    @Override
    public boolean onUsedByWand(EntityPlayer entityPlayer, ItemStack itemStack, World world, BlockPos blockPos, EnumFacing enumFacing) {
        ((TileSecondSun) world.getTileEntity(blockPos)).onWanded(entityPlayer, itemStack);
        return true;
    }

    @Override
    public void registerModels() {
        ModelHandler.registerInventoryVariant(this);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(this), 0, TileSecondSun.class);
    }

    @Override
    public void renderHUD(Minecraft minecraft, ScaledResolution scaledResolution, World world, BlockPos blockPos) {
        ((TileSecondSun) world.getTileEntity(blockPos)).renderHUD(minecraft, scaledResolution);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileSecondSun(tier);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing s, float xs, float ys, float zs) {
        if(world.isRemote) {
            return true;
        }
        TileSecondSun avatar = (TileSecondSun) world.getTileEntity(pos);
        ItemStack item = player.getHeldItem(hand);
        if(item.getItem() instanceof ItemSSUpgrade && state.getBlock() instanceof BlockSecondSunCore) {
            ItemSSUpgrade upgrade = (ItemSSUpgrade) item.getItem();
            BlockSecondSunCore sun = (BlockSecondSunCore) state.getBlock();
            TileSecondSun te = (TileSecondSun) world.getTileEntity(pos);
            if(upgrade.tier == sun.tier) {
                Block newBlock;
                switch (sun.tier) {
                    case 0:
                        newBlock = Blocks.secondSunCore1;
                        break;
                    case 1:
                        newBlock = Blocks.secondSunCore2;
                        break;
                    case 2:
                        newBlock = Blocks.secondSunCore3;
                        break;
                    case 3:
                        newBlock = Blocks.secondSunCore4;
                        break;
                    case 4:
                        newBlock = Blocks.secondSunCore5;
                        break;
                    case 5:
                        newBlock = Blocks.secondSunCore6;
                        break;
                    default:
                        newBlock = Blocks.secondSunCore0;
                }
                int mana = te.getCurrentMana();
                world.setBlockState(pos, newBlock.getDefaultState());
                TileSecondSun newTile = (TileSecondSun) world.getTileEntity(pos);
                newTile.recieveMana(mana);

            }
        }
        /**
         * If the item is an upgrade
         *   if the tier is this tier
         *     modify ourself
         *     modify the tile entity
         */
        return false;
    }
}
