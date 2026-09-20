package uk.tfindustries.blueaerotweaks.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.minecraft.world.level.block.Block;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
import uk.tfindustries.blueaerotweaks.registries.BlueBlocks;
import uk.tfindustries.blueaerotweaks.registries.BlueItems;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BlueAeroTweaks.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(BlueItems.BINCHOTAN.get());
        basicItem(BlueItems.RAW_TOFU.get());
        basicItem(BlueItems.FRIED_TOFU.get());
        basicItem(BlueItems.TOFU_FRYER.get());

        buttonItem(BlueBlocks.JAPANESE_CEDAR_BUTTON, BlueBlocks.JAPANESE_CEDAR_PLANKS);
        fenceItem(BlueBlocks.JAPANESE_CEDAR_FENCE, BlueBlocks.JAPANESE_CEDAR_PLANKS);
        wallItem(BlueBlocks.JAPANESE_CEDAR_WALL, BlueBlocks.JAPANESE_CEDAR_PLANKS);

        //basicItem(BlueBlocks.JAPANESE_CEDAR_DOOR.asItem());

        //withExistingParent(BlueBlocks.JAPANESE_CEDAR_DOOR.getId().toString(), modLoc("block/japanese_cedar_door"));
    }


    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
}
