package uk.tfindustries.blueaerotweaks.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
import uk.tfindustries.blueaerotweaks.item.custom.FuelItem;
import uk.tfindustries.blueaerotweaks.item.custom.TofuFryerItem;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlueAeroTweaks.MODID);

    public static final DeferredItem<Item> FRIED_TOFU = ITEMS.register("fried_tofu",
            () -> new Item(new Item.Properties().food(ModFoodProperties.FRIED_TOFU)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.blueaerotweaks.fried_tofu.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> RAW_TOFU = ITEMS.register("raw_tofu",
            () -> new Item(new Item.Properties().food(ModFoodProperties.RAW_TOFU)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.blueaerotweaks.raw_tofu.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> BINCHOTAN = ITEMS.register("binchotan",
            () -> new FuelItem(new Item.Properties(), 6400) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.blueaerotweaks.binchotan.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> TOFU_FRYER = ITEMS.register("tofu_fryer",
            () -> new TofuFryerItem(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if (Screen.hasShiftDown())
                        tooltipComponents.add(Component.translatable("tooltip.blueaerotweaks.tofu_fryer.tooltip"));
                    else
                        tooltipComponents.add(Component.translatable("tooltip.blueaerotweaks.tofu_fryer_shift_down.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
