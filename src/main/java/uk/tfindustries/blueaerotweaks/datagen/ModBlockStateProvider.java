package uk.tfindustries.blueaerotweaks.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
import uk.tfindustries.blueaerotweaks.registries.BlueBlocks;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BlueAeroTweaks.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(BlueBlocks.SQUALLSTONE_BLOCK);
        blockWithItem(BlueBlocks.FRIED_TOFU_BLOCK);
        blockWithItem(BlueBlocks.RAW_TOFU_BLOCK);
        blockWithItem(BlueBlocks.JAPANESE_CEDAR_PLANKS);
        blockWithItem(BlueBlocks.JAPANESE_CEDAR_WOOD);
        //blockWithItem(BlueBlocks.JAPANESE_CEDAR_LOG);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
