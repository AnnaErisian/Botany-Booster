package blue.thejester.botanybooster.block;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.tile.TileArrayedCrystalCube;
import blue.thejester.botanybooster.block.tile.TileSecondSun;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.client.core.handler.ModelHandler;
import vazkii.botania.client.render.IModelRegister;
import vazkii.botania.common.block.tile.mana.TilePool;

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
        this.tier = tier;
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
        return null;
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
}
