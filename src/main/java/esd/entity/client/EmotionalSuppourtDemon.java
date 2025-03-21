package esd.entity.client;

import esd.EmotionalSuppourtDemons;
import esd.entity.custom.DemonEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class EmotionalSuppourtDemon<T extends DemonEntity> extends SinglePartEntityModel<T> {
	public static final EntityModelLayer DEMON = new EntityModelLayer(Identifier.of(EmotionalSuppourtDemons.MOD_ID),"main");
	private final ModelPart HandsomeBod;
	private final ModelPart Left_Ear;

	public EmotionalSuppourtDemon(ModelPart root) {
		this.HandsomeBod = root.getChild("HandsomeBod");
		this.Left_Ear = this.HandsomeBod.getChild("Left_Ear");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData HandsomeBod = modelPartData.addChild("HandsomeBod", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -5.0F, 8.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, -1.0F));

		ModelPartData cube_r1 = HandsomeBod.addChild("cube_r1", ModelPartBuilder.create().uv(24, 17).cuboid(0.0F, -5.0F, -2.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -8.0F, -4.0F, 0.0F, 0.0F, 0.1745F));

		ModelPartData cube_r2 = HandsomeBod.addChild("cube_r2", ModelPartBuilder.create().uv(16, 17).cuboid(0.0F, -5.0F, -2.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -8.0F, -4.0F, 0.0F, 0.0F, -0.1745F));

		ModelPartData Left_Ear = HandsomeBod.addChild("Left_Ear", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, -5.0F, -4.0F));

		ModelPartData cube_r3 = Left_Ear.addChild("cube_r3", ModelPartBuilder.create().uv(0, 3).mirrored().cuboid(0.0F, -3.0F, 0.0F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.0873F, -0.3054F));

		ModelPartData fin = HandsomeBod.addChild("fin", ModelPartBuilder.create().uv(0, 17).cuboid(0.0F, -4.0F, -5.0F, 0.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, -4.0F));

		ModelPartData Tail = HandsomeBod.addChild("Tail", ModelPartBuilder.create().uv(8, 17).cuboid(0.0F, -2.0F, 0.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, 5.0F));

		ModelPartData Front_left_leg = HandsomeBod.addChild("Front_left_leg", ModelPartBuilder.create().uv(8, 26).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -2.0F, -4.0F));

		ModelPartData Back_left_leg = HandsomeBod.addChild("Back_left_leg", ModelPartBuilder.create().uv(0, 29).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -2.0F, 4.0F));

		ModelPartData Front_right_leg = HandsomeBod.addChild("Front_right_leg", ModelPartBuilder.create().uv(16, 26).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -2.0F, -4.0F));

		ModelPartData Back_right_leg = HandsomeBod.addChild("Back_right_leg", ModelPartBuilder.create().uv(24, 26).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -2.0F, 4.0F));

		ModelPartData right_ear = HandsomeBod.addChild("right_ear", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, -5.0F, -4.0F));

		ModelPartData cube_r4 = right_ear.addChild("cube_r4", ModelPartBuilder.create().uv(0, 3).mirrored().cuboid(0.0F, -3.0F, 0.0F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 3.1416F, -0.0873F, -2.8362F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(DemonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.animateMovement(DemonAnimations.walk, limbSwing, limbSwingAmount, 2f, 2.5f);


		if(entity.getSitTicks() > 0){
			this.updateAnimation(entity.sit,DemonAnimations.sit,21 - entity.getSitTicks());
		}
		if(entity.getSitTicks() < 0){
			if(entity.getSitTicks() < -20){
				this.animate(DemonAnimations.idle);
			}else{
				this.updateAnimation(entity.sit, DemonAnimations.stand, entity.getSitTicks() + 20);
			}
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		HandsomeBod.render(matrices, vertexConsumer, light, overlay);
	}
	@Override
	public ModelPart getPart() {
		return HandsomeBod;
	}
}