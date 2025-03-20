package esd.item;

import esd.EmotionalSuppourtDemons;
import esd.entity.ModEntities;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item DEMON_SPAWN_EGG = registerItem("demon_spawn_egg", new SpawnEggItem(ModEntities.DEMON, 0xa53543, 0x545452,new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(EmotionalSuppourtDemons.MOD_ID, name), item);
    }
    public static void registerItems() {
        EmotionalSuppourtDemons.LOGGER.info("Registering items for " + EmotionalSuppourtDemons.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register((content) -> {
            content.add(DEMON_SPAWN_EGG);
        });
    }
}
