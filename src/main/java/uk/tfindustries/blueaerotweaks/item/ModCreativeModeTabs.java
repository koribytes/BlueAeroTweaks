package uk.tfindustries.blueaerotweaks.item;

import net.minecraft.network.chat.Component;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.tfindustries.blueaerotweaks.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlueAeroTweaks.MODID);

    public static final Supplier<CreativeModeTab> BLUE_AERO_TWEAKS_TAB = CREATIVE_MODE_TAB.register("blue_aero_tweaks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FRIED_TOFU.get()))
                    .title(Component.translatable("creativetab.blueaerotweaks.blue_aero_tweaks_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.FRIED_TOFU);
                        output.accept(ModItems.RAW_TOFU);
                        output.accept(ModBlocks.RAW_TOFU_BLOCK);
                        output.accept(ModBlocks.FRIED_TOFU_BLOCK);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
