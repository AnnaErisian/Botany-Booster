/**
 * Adapted from Botania.  Original licence below.
 *
 */

/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Feb 13, 2015, 10:25:32 PM (GMT)]
 */
package blue.thejester.botanybooster.item;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.entity.EntityLongCorporeaSpark;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import vazkii.botania.api.corporea.CorporeaHelper;
import vazkii.botania.client.render.IModelRegister;

import javax.annotation.Nonnull;

public class ItemLongCorporeaSpark extends BaseItem implements IModelRegister {

	public ItemLongCorporeaSpark() {
		setRegistryName(new ResourceLocation(BotanyBooster.MODID, "longCorporeaSpark"));
		setTranslationKey("longCorporeaSpark");
	}

	@Nonnull
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float xv, float yv, float zv) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null && (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP) || tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
				&& !CorporeaHelper.doesBlockHaveSpark(world, pos)) {
			ItemStack stack = player.getHeldItem(hand);
			stack.shrink(1);
			if(!world.isRemote) {
				EntityLongCorporeaSpark spark = new EntityLongCorporeaSpark(world);
				if(stack.getItemDamage() == 1)
					spark.setMaster(true);
				spark.setPosition(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
				world.spawnEntity(spark);
				world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 0);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

}
