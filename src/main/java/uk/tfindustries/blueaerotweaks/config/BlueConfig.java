package uk.tfindustries.blueaerotweaks.config;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

//code from createaerophysicsgantry
public class BlueConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /*
    public static final ModConfigSpec.IntValue BELT_WHEEL_MAX_DISTANCE = BUILDER
            .comment("Maximum allowed link distance for Belt Wheel (hard capped at 64 blocks)")
            .defineInRange("beltWheelMaxDistance", 64, 1, 64);

     */

    private static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    public static ModConfigSpec getSpec() {
        return SPEC;
    }
}
