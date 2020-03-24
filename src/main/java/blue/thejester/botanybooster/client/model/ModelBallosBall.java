package blue.thejester.botanybooster.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelBallosBall - Anna Erisian
 * Created using Tabula 7.1.0
 */
public class ModelBallosBall extends ModelBase {
    public ModelRenderer core;
    public ModelRenderer spike_ur;
    public ModelRenderer spike_ul;
    public ModelRenderer spike_tr;
    public ModelRenderer spike_tl;
    public ModelRenderer spike_lr;
    public ModelRenderer spike_ll;

    public ModelBallosBall() {
        this.textureWidth = 32;
        this.textureHeight = 16;
        this.spike_ur = new ModelRenderer(this, 0, 0);
        this.spike_ur.setRotationPoint(3.0F, -1.8F, 0.0F);
        this.spike_ur.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(spike_ur, 0.0F, 0.0F, 0.7853981633974483F);
        this.spike_lr = new ModelRenderer(this, 0, 0);
        this.spike_lr.setRotationPoint(3.0F, 1.0F, 0.0F);
        this.spike_lr.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(spike_lr, 0.0F, 0.0F, 0.7853981633974483F);
        this.spike_tl = new ModelRenderer(this, 0, 0);
        this.spike_tl.setRotationPoint(-1.2F, -3.0F, 0.0F);
        this.spike_tl.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(spike_tl, 0.0F, 0.0F, 0.7853981633974483F);
        this.spike_tr = new ModelRenderer(this, 0, 0);
        this.spike_tr.setRotationPoint(1.2F, -3.0F, 0.0F);
        this.spike_tr.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(spike_tr, 0.0F, 0.0F, 0.7853981633974483F);
        this.spike_ll = new ModelRenderer(this, 0, 0);
        this.spike_ll.setRotationPoint(-3.0F, 1.0F, 0.0F);
        this.spike_ll.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(spike_ll, 0.0F, 0.0F, 0.7853981633974483F);
        this.core = new ModelRenderer(this, 0, 0);
        this.core.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.core.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.spike_ul = new ModelRenderer(this, 0, 0);
        this.spike_ul.setRotationPoint(-3.0F, -1.8F, 0.0F);
        this.spike_ul.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(spike_ul, 0.0F, 0.0F, 0.7853981633974483F);
        this.core.addChild(this.spike_ur);
        this.core.addChild(this.spike_lr);
        this.core.addChild(this.spike_tl);
        this.core.addChild(this.spike_tr);
        this.core.addChild(this.spike_ll);
        this.core.addChild(this.spike_ul);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.core.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
