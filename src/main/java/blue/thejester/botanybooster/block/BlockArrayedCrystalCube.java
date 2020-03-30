/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Apr 30, 2015, 3:56:19 PM (GMT)]
 */
package blue.thejester.botanybooster.block;

import javax.annotation.Nonnull;

import blue.thejester.botanybooster.block.tile.TileArrayedCrystalCube;
import blue.thejester.botanybooster.core.LexiconData;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.client.core.handler.ModelHandler;
import vazkii.botania.client.render.IModelRegister;
import vazkii.botania.common.block.corporea.BlockCorporeaBase;
import vazkii.botania.common.block.tile.TileSimpleInventory;
import vazkii.botania.common.block.tile.corporea.TileCorporeaBase;
import vazkii.botania.common.core.helper.InventoryHelper;
import vazkii.botania.common.item.ModItems;

public class BlockArrayedCrystalCube extends Block implements ILexiconable, IWandable, IModelRegister {

	public static final String NAME = "arrayed_crystal_cube";

	public BlockArrayedCrystalCube() {
		super(Material.IRON);
		setTranslationKey(NAME);
		this.setRegistryName(new ResourceLocation("botanybooster", NAME));
		setHardness(5.5F);
		setSoundType(SoundType.METAL);
		setDefaultState(blockState.getBaseState().withProperty(Properties.StaticProperty, true));
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TileSimpleInventory inv = (TileSimpleInventory) world.getTileEntity(pos);

		InventoryHelper.dropInventory(inv, world, state, pos);

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nonnull
	@Override
	public BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { Properties.StaticProperty }, new IUnlistedProperty[] { Properties.AnimationProperty } );
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}

	@Nonnull
	@Override
	public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(Properties.StaticProperty, true);
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		if(!world.isRemote) {
			TileArrayedCrystalCube cube = (TileArrayedCrystalCube) world.getTileEntity(pos);
			cube.doRequest(player.isSneaking());
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing s, float xs, float ys, float zs) {
		ItemStack stack = player.getHeldItem(hand);
		if(!stack.isEmpty()) {
			if(stack.getItem() == ModItems.twigWand && player.isSneaking())
				return false;
			TileArrayedCrystalCube cube = (TileArrayedCrystalCube) world.getTileEntity(pos);
			if(cube.locked) {
				if(!world.isRemote)
					player.sendStatusMessage(new TextComponentTranslation("botaniamisc.crystalCubeLocked"), false);
			} else
				cube.setRequestTarget(stack);
			return true;
		}
		return false;
	}

	@Override
	public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side) {
		if(player == null || player.isSneaking()) {
			TileArrayedCrystalCube cube = (TileArrayedCrystalCube) world.getTileEntity(pos);
			cube.locked = !cube.locked;
			if(!world.isRemote)
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(cube);
			return true;
		}
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Nonnull
	@Override
	public TileCorporeaBase createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
		return new TileArrayedCrystalCube();
	}

	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return LexiconData.clearCrystalCube;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		return ((TileArrayedCrystalCube) world.getTileEntity(pos)).getComparatorValue();
	}

	@Nonnull
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
		return BlockFaceShape.UNDEFINED;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		ModelHandler.registerInventoryVariant(this);
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(this), 0, TileArrayedCrystalCube.class);
	}
}
