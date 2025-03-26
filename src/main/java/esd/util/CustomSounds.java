package esd.util;

import esd.EmotionalSuppourtDemons;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class CustomSounds {
    public static final RegistryEntry.Reference<SoundEvent> CRASH = registerReference("crash");
    public static final SoundEvent START_FALL = registerSound("start_fall");
    public static final SoundEvent SQUEAK = registerSound("squeak");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(EmotionalSuppourtDemons.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    @SuppressWarnings("SameParameterValue")
    private static RegistryEntry.Reference<SoundEvent> registerReference(String id) {
        return registerReference(Identifier.of("esd", id));
    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id) {
        return registerReference(id, id);
    }

    private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id, Identifier soundId) {
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
    }

    public static void initialize() {
        EmotionalSuppourtDemons.LOGGER.info("Registering " + EmotionalSuppourtDemons.MOD_ID + " Sounds");
    }
}