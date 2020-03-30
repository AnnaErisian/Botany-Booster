package blue.thejester.botanybooster.block.tile;

import blue.thejester.botanybooster.BotanyBooster;
import blue.thejester.botanybooster.block.Blocks;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.*;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.mana.TileBellows;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.core.handler.ManaNetworkHandler;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.item.ItemManaTablet;
import vazkii.botania.common.item.ModItems;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;

import static vazkii.botania.common.block.tile.mana.TilePool.MAX_MANA_DILLUTED;

/**
 * This is largely a copy of TileManaPool from Botania
 * and as such was written by vazkii
 */
public class TileSecondSun extends TileEntity implements IManaPool, ISparkAttachable, IThrottledPacket, ITickable {

    private static final Color PARTICLE_COLOR = new Color(0xffddee);

    private static final String TAG_MANA = "mana";
    private static final String TAG_KNOWN_MANA = "knownMana";
    private static final String TAG_COLOR = "color";
    private static final int[] MANA_CAPS = {1000000 * 16, 1000000 * 32, 1000000 * 64, 1000000 * 128, 1000000 * 256, 1000000 * 512, 1000000 * 2048};
    private static final String TAG_TIER = "tier";

    int mana;
    public int tier;
    private int knownMana = -1;
    private int ticks;
    boolean isDoingTransfer = false;
    int ticksDoingTransfer = 0;
    private int soundTicks = 0;
    private boolean sendPacket = false;
    private EnumDyeColor color = EnumDyeColor.CYAN;

    private AxisAlignedBB renderBoundingBox = null;

    public TileSecondSun(int tier) {
        super();
        this.tier = tier;
    }

    public TileSecondSun() {
        this(0);
    }

    @Override
    public boolean isFull() {
        return getCurrentMana() >= getCurrentManaCap();
    }

    @Override
    public void recieveMana(int i) {
        int old = this.mana;
        this.mana = Math.max(0, Math.min(getCurrentMana() + i, getCurrentManaCap()));
        if(old != this.mana) {
            world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
            markDispatchable();
        }
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return true;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
//        if(renderBoundingBox == null) {
//            renderBoundingBox = new AxisAlignedBB(pos.add(new Vec3i(-10,-10,-10)), pos.add(new Vec3i(10,10,10)));
//        }
//        return renderBoundingBox;
    }

    /**
     * This comes from TileManaPool, but with two changes:
     * the section about interacting with items in the pool has been removed
     * the mana cap is initially set to 10, then is updated every second depending on the nearby block structure
     */
    @Override
    public void update() {
        if(!ManaNetworkHandler.instance.isPoolIn(this) && !isInvalid())
            ManaNetworkEvent.addPool(this);

        if(world.isRemote) {
            double particleChance = 1F - (double) getCurrentMana() / (double) getCurrentManaCap() * 0.1;
            if(Math.random() > particleChance)
                Botania.proxy.wispFX(pos.getX() + 0.3 + Math.random() * 0.5, pos.getY() + 0.6 + Math.random() * 0.25, pos.getZ() + Math.random(), PARTICLE_COLOR.getRed() / 255F, PARTICLE_COLOR.getGreen() / 255F, PARTICLE_COLOR.getBlue() / 255F, (float) Math.random() / 3F, (float) -Math.random() / 25F, 2F);
            return;
        }

        boolean wasDoingTransfer = isDoingTransfer;
        isDoingTransfer = false;

        if(soundTicks > 0) {
            soundTicks--;
        }

        if(sendPacket && ticks % 10 == 0) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
            sendPacket = false;
        }

        if(isDoingTransfer)
            ticksDoingTransfer++;
        else {
            ticksDoingTransfer = 0;
            if(wasDoingTransfer)
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
        }

        ticks++;
    }

    @Override
    public boolean isOutputtingPower() {
        return false;
    }

    @Override
    public EnumDyeColor getColor() {
        return color;
    }

    @Override
    public void setColor(EnumDyeColor enumDyeColor) {
        this.color = color;
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 0b1011);
    }

    @Override
    public void markDispatchable() {
        sendPacket = true;
    }

    @Override
    public boolean canAttachSpark(ItemStack itemStack) {
        return true;
    }

    @Override
    public void attachSpark(ISparkEntity iSparkEntity) {}

    @Override
    public int getAvailableSpaceForMana() {
        return Math.max(0, getCurrentManaCap() - getCurrentMana());
    }

    @Override
    public ISparkEntity getAttachedSpark() {
        List sparks = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.up(), pos.up().add(1, 1, 1)), Predicates.instanceOf(ISparkEntity.class));
        if(sparks.size() == 1) {
            Entity e = (Entity) sparks.get(0);
            return (ISparkEntity) e;
        }

        return null;
    }

    @Override
    public boolean areIncomingTranfersDone() {
        return false;
    }

    @Override
    public int getCurrentMana() {
        return mana;
    }
    public int getCurrentManaCap() {
        return MANA_CAPS[tier];
    }
    public int getCurrentTier() {return Math.max(tier, 0);}
    public void onWanded(EntityPlayer player, ItemStack wand) {
        if(player == null)
            return;

        if(!world.isRemote) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            writePacketNBT(nbttagcompound);
            nbttagcompound.setInteger(TAG_KNOWN_MANA, getCurrentMana());
            if(player instanceof EntityPlayerMP)
                ((EntityPlayerMP) player).connection.sendPacket(new SPacketUpdateTileEntity(pos, -999, nbttagcompound));
        }

        world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.ding, SoundCategory.PLAYERS, 0.11F, 1F);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1nbtTagCompound) {
        NBTTagCompound ret = super.writeToNBT(par1nbtTagCompound);
        writePacketNBT(ret);
        return ret;
    }

    @Nonnull
    @Override
    public final NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        readPacketNBT(par1nbtTagCompound);
    }

    public void writePacketNBT(NBTTagCompound cmp) {
        cmp.setInteger(TAG_MANA, mana);
        cmp.setInteger(TAG_COLOR, color.getMetadata());
        cmp.setInteger(TAG_TIER, tier);
    }

    public void readPacketNBT(NBTTagCompound cmp) {
        mana = cmp.getInteger(TAG_MANA);
        color = EnumDyeColor.byMetadata(cmp.getInteger(TAG_COLOR));
        tier = cmp.getInteger(TAG_TIER);

        if(cmp.hasKey(TAG_KNOWN_MANA))
            knownMana = cmp.getInteger(TAG_KNOWN_MANA);
    }

    /**
     * Pretty sure we removed everything special from the normal pool renderer
     * @param mc
     * @param res
     */
    @SideOnly(Side.CLIENT)
    public void renderHUD(Minecraft mc, ScaledResolution res) {
        ItemStack pool = new ItemStack(Blocks.secondSunCore0, 1);
        String name = I18n.format(pool.getTranslationKey().replaceAll("tile.", "tile." + BotanyBooster.MODID + ":") + ".name");
        int color = 0x4444FF;
        HUDHandler.drawSimpleManaHUD(color, knownMana, getCurrentManaCap(), name, res);
            //TODO

//        int x = res.getScaledWidth() / 2 - 11;
//        int y = res.getScaledHeight() / 2 + 30;
//
//        int u = 0;
//        int v = 38;

//        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//
//        GlStateManager.color(1F, 1F, 1F, 1F);
//
//        GlStateManager.disableLighting();
//        GlStateManager.disableBlend();
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        readPacketNBT(packet.getNbtCompound());
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writePacketNBT(tag);
        return new SPacketUpdateTileEntity(pos, -999, tag);
    }

}
