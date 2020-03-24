package blue.thejester.botanybooster.client;

import blue.thejester.botanybooster.client.model.ModelBallosBall;
import blue.thejester.botanybooster.entity.EntityBallosBall;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.internal.ShaderCallback;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.client.lib.LibResources;

import javax.annotation.Nonnull;

public class RenderBallosBall extends RenderLiving<EntityBallosBall> {

    final ShaderCallback callback = shader -> {
        // Frag Uniforms
        int disfigurationUniform = ARBShaderObjects.glGetUniformLocationARB(shader, "disfiguration");
        ARBShaderObjects.glUniform1fARB(disfigurationUniform, 0.025F);

        // Vert Uniforms
        int grainIntensityUniform = ARBShaderObjects.glGetUniformLocationARB(shader, "grainIntensity");
        ARBShaderObjects.glUniform1fARB(grainIntensityUniform, 0.05F);
    };

    public RenderBallosBall(RenderManager renderManager) {
        super(renderManager, new ModelBallosBall(), 0.25F);
        //setRenderPassModel(new ModelPixie());
        shadowSize = 0.0F;
    }

    @Nonnull
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityBallosBall entity) {
        return new ResourceLocation("botanybooster:textures/model/ballosball.png");
    }

    @Override
    public void doRender(@Nonnull EntityBallosBall pixie, double par2, double par4, double par6, float par8, float par9) {
        if(pixie.getType() == 1)
            ShaderHelper.useShader(ShaderHelper.doppleganger, callback);
        super.doRender(pixie, par2, par4, par6, par8, par9);
        if(pixie.getType() == 1)
            ShaderHelper.releaseShader();
    }
}
