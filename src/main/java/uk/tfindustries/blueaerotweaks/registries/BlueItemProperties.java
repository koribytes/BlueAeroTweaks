package uk.tfindustries.blueaerotweaks.registries;

import net.minecraft.world.food.FoodProperties;

public class BlueItemProperties {
    public static final FoodProperties FRIED_TOFU = new FoodProperties.Builder().nutrition(10).saturationModifier(1f).build();
    public static final FoodProperties RAW_TOFU = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();
}
