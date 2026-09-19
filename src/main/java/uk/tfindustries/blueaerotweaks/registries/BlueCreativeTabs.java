package uk.tfindustries.blueaerotweaks.registries;

import dev.simulated_team.simulated.registrate.SimulatedRegistrate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;

import java.util.function.Supplier;

public class BlueCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlueAeroTweaks.MODID);

    private static final ResourceLocation SIMULATED_SECTION = ResourceLocation.fromNamespaceAndPath("simulated", "simulated");
    private static boolean sectionsInitialized = false;

    public static final Supplier<CreativeModeTab> BLUE_AERO_TWEAKS_TAB = CREATIVE_MODE_TAB.register("blue_aero_tweaks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(BlueItems.FRIED_TOFU.get()))
                    .title(Component.translatable("creativetab.blueaerotweaks.blue_aero_tweaks_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(BlueItems.FRIED_TOFU);
                        output.accept(BlueItems.RAW_TOFU);
                        output.accept(BlueItems.BINCHOTAN);
                        output.accept(BlueItems.TOFU_FRYER);
                        output.accept(BlueBlocks.RAW_TOFU_BLOCK);
                        output.accept(BlueBlocks.FRIED_TOFU_BLOCK);
                        output.accept(BlueBlocks.AIR_FRYER_BLOCK);
                        output.accept(BlueBlocks.SQUALLSTONE_BLOCK);
                        //output.accept(BlueBlocks.AMMO_DEPLOYER);

                        output.accept(BlueBlocks.JAPANESE_CEDAR_WOOD);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_LOG);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_PLANKS);
                        //output.accept(BlueBlocks.JAPANESE_CEDAR_STAIRS);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_SLAB);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_WALL);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_FENCE);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_FENCE_GATE);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_BUTTON);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_PRESSURE_PLATE);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_DOOR);
                        output.accept(BlueBlocks.JAPANESE_CEDAR_TRAPDOOR);
                    }).build());



    private static void registerSectionItem(ResourceLocation sectionId, ResourceLocation itemPath, Supplier<Item> itemSupplier) {
        SimulatedRegistrate.TAB_ITEMS.add(itemSupplier);
        SimulatedRegistrate.ITEM_TO_SECTION.put(itemPath, sectionId);
    }


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
