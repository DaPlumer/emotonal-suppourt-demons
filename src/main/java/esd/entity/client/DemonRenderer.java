package esd.entity.client;

import esd.EmotionalSuppourtDemons;
import esd.entity.custom.DemonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DemonRenderer extends MobEntityRenderer<DemonEntity, EmotionalSuppourtDemon<DemonEntity>> {
    public DemonRenderer(EntityRendererFactory.Context context) {
        super(context, new EmotionalSuppourtDemon<>(context.getPart(EmotionalSuppourtDemon.DEMON)), 0.75f);
    }

    @Override
    public Identifier getTexture(DemonEntity entity) {
        return Identifier.of(EmotionalSuppourtDemons.MOD_ID, "textures/entity/demon/"+entity.orientation+"esd.png");
    }

    @Override
    public void render(DemonEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}