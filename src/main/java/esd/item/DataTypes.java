package esd.item;

import esd.EmotionalSuppourtDemons;
import esd.entity.client.EmotionalSuppourtDemon;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;
public class DataTypes{
    public static final ComponentType<String> ORENTATION =register(stringBuilder -> stringBuilder.codec(Codecs.NON_EMPTY_STRING),"orentation");
    private static <T>ComponentType<T> register(@NotNull UnaryOperator<ComponentType.Builder<T>> builderOperator, String name) {
     return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(EmotionalSuppourtDemons.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }
    public static void registerDataComponentTypes(){
        EmotionalSuppourtDemons.LOGGER.info("Registering Data Component Types for " + EmotionalSuppourtDemons.MOD_ID);
    }
}