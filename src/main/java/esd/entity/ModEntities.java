package esd.entity;

import esd.EmotionalSuppourtDemons;
import esd.entity.custom.DemonEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * The ModEntities class is responsible for registering and managing custom entity types
 * within the EmotionalSupportDemons mod.
 *
 * This class handles the initialization of entities, including their registration
 * with the game registry. It defines the properties and behaviors of the entity types,
 * as well as ensures that they are properly logged and identified.
 *
 * Entity registration is achieved through the use of the Fabric API's Registry utilities,
 * which enable seamless integration with the game. Among other entities, this class defines
 * the DemonEntity, a custom creature with unique traits and interactions.
 *
 * Methods:
 * - registerEntities(): Logs the registration process for custom entities in this mod.
 *
 * Fields:
 * - DEMON: Represents the registered instance of the custom DemonEntity type, created and registered
 *   with specific attributes such as spawn group and dimensions.
 */
public class ModEntities {
    public static final EntityType<DemonEntity> DEMON = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(EmotionalSuppourtDemons.MOD_ID, "emotional_suppourt_demon"),
            EntityType.Builder.create(DemonEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f,1f).makeFireImmune().build());

    public static void registerEntities() {
        EmotionalSuppourtDemons.LOGGER.info("Registering entities for " + EmotionalSuppourtDemons.MOD_ID);
    }

}
