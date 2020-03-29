/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Apr 30, 2015, 4:10:14 PM (GMT)]
 */
package blue.thejester.botanybooster.client;

import blue.thejester.botanybooster.asm.names.ObfuscatedName;
import blue.thejester.botanybooster.block.tile.TileSecondSun;
import blue.thejester.botanybooster.client.model.ModelBallosBall;
import blue.thejester.botanybooster.client.model.SphereRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import vazkii.botania.client.model.ModelSpinningCubes;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public class RenderTileSecondSun extends TileEntitySpecialRenderer<TileSecondSun> {

    final ModelSpinningCubes cubes = new ModelSpinningCubes();

    private static int displayListId = -1;

    private static float[] shellRadius = {0.8f,1.0f,1.3f,1.7f,2.1f,2.5f,3.2f};

    private static final ResourceLocation mana_tex = new ResourceLocation("botanybooster:textures/blocks/second_sun_core.png");
    private static final ResourceLocation glass_tex = new ResourceLocation("botanybooster:textures/blocks/second_sun_glass.png");
    private static final ResourceLocation extra_tex = new ResourceLocation("botanybooster:textures/blocks/second_sun_extra.png");

    @Override
    public void render(@Nonnull TileSecondSun te, double x, double y, double z, float ff, int digProgress, float unused) {
        if(displayListId == -1) {
            displayListId = GLAllocation.generateDisplayLists(1);
            GlStateManager.glNewList(displayListId, GL11.GL_COMPILE);
            BufferBuilder bb = Tessellator.getInstance().getBuffer();
            bb.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
            SphereRenderer.insertVertices(bb);
            Tessellator.getInstance().draw();
            GlStateManager.glEndList();

        }
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate(x+0.5, y+0.5, z+0.5);

        long ticks = getWorld().getWorldTime();
        GlStateManager.rotate(ticks * 0.6f, 0, 1, 0.2f);

        float outerScale = shellRadius[te.getCurrentTier()];
        float innerScale = outerScale * 0.80f * te.getCurrentMana() / te.getCurrentManaCap() + 0.175f;

        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableBlend();

        GlStateManager.scale(innerScale, innerScale, innerScale);
        this.bindTexture(mana_tex);
        GlStateManager.callList(displayListId);

        GlStateManager.scale(outerScale/innerScale, outerScale/innerScale, outerScale/innerScale);
        this.bindTexture(glass_tex);
        GlStateManager.callList(displayListId);

        if(te.getCurrentTier() == 6) {
            GlStateManager.rotate(ticks * -1.4f, -0.3f, 1, 0);
            GlStateManager.scale(1.05f, 1.05f, 1.05f);
            this.bindTexture(extra_tex);
            GlStateManager.callList(displayListId);
        }

        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
