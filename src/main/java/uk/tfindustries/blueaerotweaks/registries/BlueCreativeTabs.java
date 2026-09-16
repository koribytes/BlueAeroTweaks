package uk.tfindustries.blueaerotweaks.registries;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
import uk.tfindustries.blueaerotweaks.item.ModItems;

import java.util.function.Supplier;

public class BlueCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlueAeroTweaks.MODID);

    public static final Supplier<CreativeModeTab> BLUE_AERO_TWEAKS_TAB = CREATIVE_MODE_TAB.register("blue_aero_tweaks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FRIED_TOFU.get()))
                    .title(Component.translatable("creativetab.blueaerotweaks.blue_aero_tweaks_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.FRIED_TOFU);
                        output.accept(ModItems.RAW_TOFU);
                        output.accept(ModItems.BINCHOTAN);
                        output.accept(ModItems.TOFU_FRYER);
                        output.accept(BlueBlocks.RAW_TOFU_BLOCK);
                        output.accept(BlueBlocks.FRIED_TOFU_BLOCK);
                        output.accept(BlueBlocks.AIR_FRYER_BLOCK);
                        output.accept(BlueBlocks.SQUALLSTONE_BLOCK);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
