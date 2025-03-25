package esd;

import esd.entity.ModEntities;
import esd.entity.client.DemonRenderer;
import esd.entity.client.EmotionalSuppourtDemon;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class EmotionalSuppourtDemonsClientInitializer implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(EmotionalSuppourtDemon.DEMON, EmotionalSuppourtDemon::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.DEMON, DemonRenderer::new);
    }
}
