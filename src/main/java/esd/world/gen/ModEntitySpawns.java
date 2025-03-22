package esd.world.gen;

import esd.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public class ModEntitySpawns {
    public static void addEntitySpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.all(),
                SpawnGroup.CREATURE, ModEntities.DEMON, 30, 1, 10);
        SpawnRestriction.register(
                ModEntities.DEMON,
                SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                (type, world, spawnReason, pos, random) -> true
        );
    }
}
