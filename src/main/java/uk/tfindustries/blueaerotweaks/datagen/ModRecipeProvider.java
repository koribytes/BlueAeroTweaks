package uk.tfindustries.blueaerotweaks.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import uk.tfindustries.blueaerotweaks.registries.BlueBlocks;
import uk.tfindustries.blueaerotweaks.registries.BlueItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }


    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        //List<ItemLike> BINCHOTAN_SMELTABLES = List.of(BlueItems.BINCHOTAN);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlueBlocks.FRIED_TOFU_BLOCK.get())
                .pattern("FFF")
                .pattern("FFF")
                .pattern("FFF")
                .define('F', BlueItems.FRIED_TOFU.get())
                .unlockedBy("has_fried_tofu", has(BlueItems.FRIED_TOFU)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlueItems.FRIED_TOFU.get(), 9)
                .requires(BlueItems.FRIED_TOFU)
                .unlockedBy("has_fried_tofu_block", has(BlueBlocks.FRIED_TOFU_BLOCK)).save(recipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlueBlocks.RAW_TOFU_BLOCK.get())
                .pattern("FFF")
                .pattern("FFF")
                .pattern("FFF")
                .define('F', BlueItems.RAW_TOFU.get())
                .unlockedBy("has_raw_tofu", has(BlueItems.RAW_TOFU)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlueItems.RAW_TOFU.get(), 9)
                .requires(BlueItems.RAW_TOFU)
                .unlockedBy("has_fried_tofu_block", has(BlueBlocks.RAW_TOFU_BLOCK)).save(recipeOutput);

    }
}
