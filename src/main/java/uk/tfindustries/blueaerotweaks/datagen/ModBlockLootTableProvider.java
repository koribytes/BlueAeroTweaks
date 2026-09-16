package uk.tfindustries.blueaerotweaks.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import uk.tfindustries.blueaerotweaks.registries.BlueBlocks;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(BlueBlocks.RAW_TOFU_BLOCK.get());
        dropSelf(BlueBlocks.FRIED_TOFU_BLOCK.get());
        dropSelf(BlueBlocks.SQUALLSTONE_BLOCK.get());
        dropSelf(BlueBlocks.AIR_FRYER_BLOCK.get());


    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlueBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
