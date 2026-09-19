package uk.tfindustries.blueaerotweaks.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
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
    }
}
