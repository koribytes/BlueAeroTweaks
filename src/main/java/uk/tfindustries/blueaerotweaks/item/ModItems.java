package uk.tfindustries.blueaerotweaks.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlueAeroTweaks.MODID);

    public static final DeferredItem<Item> FRIED_TOFU = ITEMS.register("fried_tofu", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_TOFU = ITEMS.register("raw_tofu", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
