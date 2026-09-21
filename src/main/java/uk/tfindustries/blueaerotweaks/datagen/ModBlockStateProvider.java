package uk.tfindustries.blueaerotweaks.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
import uk.tfindustries.blueaerotweaks.content.blocks.JapaneseCedarLamp.JapaneseCedarLampBlock;
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
        blockWithItem(BlueBlocks.STRIPPED_JAPANESE_CEDAR_WOOD);
        blockWithItem(BlueBlocks.JAPANESE_CEDAR_WOOD);


        logBlock(BlueBlocks.JAPANESE_CEDAR_LOG.get());
        logBlock(BlueBlocks.STRIPPED_JAPANESE_CEDAR_LOG.get());

        stairsBlock(BlueBlocks.JAPANESE_CEDAR_STAIRS.get(), blockTexture(BlueBlocks.JAPANESE_CEDAR_PLANKS.get()));

        slabBlock(BlueBlocks.JAPANESE_CEDAR_SLAB.get(), blockTexture(BlueBlocks.JAPANESE_CEDAR_PLANKS.get()), blockTexture(BlueBlocks.JAPANESE_CEDAR_PLANKS.get()));

        buttonBlock(BlueBlocks.JAPANESE_CEDAR_BUTTON.get(), blockTexture(BlueBlocks.JAPANESE_CEDAR_PLANKS.get()));
        pressurePlateBlock(BlueBlocks.JAPANESE_CEDAR_PRESSURE_PLATE.get(), blockTexture(BlueBlocks.JAPANESE_CEDAR_PLANKS.get()));

        fenceBlock(BlueBlocks.JAPANESE_CEDAR_FENCE.get(), blockTexture(BlueBlocks.JAPANESE_CEDAR_PLANKS.get()));
        fenceGateBlock(BlueBlocks.JAPANESE_CEDAR_FENCE_GATE.get(), blockTexture(BlueBlocks.JAPANESE_CEDAR_PLANKS.get()));
        wallBlock(BlueBlocks.JAPANESE_CEDAR_WALL.get(), blockTexture(BlueBlocks.JAPANESE_CEDAR_PLANKS.get()));

        doorBlockWithRenderType(BlueBlocks.JAPANESE_CEDAR_DOOR.get(), modLoc("block/japanese_cedar_door_bottom"), modLoc("block/japanese_cedar_door_top"), "cutout");
        trapdoorBlockWithRenderType(BlueBlocks.JAPANESE_CEDAR_TRAPDOOR.get(), modLoc("block/japanese_cedar_trapdoor"), true, "cutout");

        blockItem(BlueBlocks.JAPANESE_CEDAR_LOG);
        blockItem(BlueBlocks.STRIPPED_JAPANESE_CEDAR_LOG);
        blockItem(BlueBlocks.JAPANESE_CEDAR_STAIRS);
        blockItem(BlueBlocks.JAPANESE_CEDAR_SLAB);
        blockItem(BlueBlocks.JAPANESE_CEDAR_FENCE_GATE);
        blockItem(BlueBlocks.JAPANESE_CEDAR_PRESSURE_PLATE);
        blockItem(BlueBlocks.JAPANESE_CEDAR_TRAPDOOR, "_bottom");

        //customLamp(BlueBlocks.JAPANESE_CEDAR_LAMP);
        customLamp();
    }


    /*
    private void customLamp(DeferredBlock<?> deferredBlock) {
        getVariantBuilder(deferredBlock.get()).forAllStates(state -> {
            if(state.getValue(JapaneseCedarLampBlock.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(deferredBlock.getId() + "_on",
                        ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID, "block/" + deferredBlock.getId() + "_on")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(deferredBlock.getId() + "_off",
                        ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID, "block/" + deferredBlock.getId() + "_off")))};
            }
        });

        simpleBlockItem(deferredBlock.get(), models().cubeAll(deferredBlock.getId() + "_on",
                ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID, "block/" + deferredBlock.getId() + "_on")));
    }

     */

    private void customLamp() {
        getVariantBuilder(BlueBlocks.JAPANESE_CEDAR_LAMP.get()).forAllStates(state -> {
            if(state.getValue(JapaneseCedarLampBlock.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("japanese_cedar_lamp_on",
                        ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID, "block/" + "japanese_cedar_lamp_on")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("japanese_cedar_lamp_off",
                        ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID, "block/" + "japanese_cedar_lamp_off")))};
            }
        });

        simpleBlockItem(BlueBlocks.JAPANESE_CEDAR_LAMP.get(), models().cubeAll("japanese_cedar_lamp_on",
                ResourceLocation.fromNamespaceAndPath(BlueAeroTweaks.MODID, "block/" + "japanese_cedar_lamp_on")));
    }


    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("blueaerotweaks:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("blueaerotweaks:block/" + deferredBlock.getId().getPath() + appendix));
    }
}
