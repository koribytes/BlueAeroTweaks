package uk.tfindustries.blueaerotweaks;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.EventPriority;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import uk.tfindustries.blueaerotweaks.config.BlueConfig;
import uk.tfindustries.blueaerotweaks.datagen.DataGenerators;
import uk.tfindustries.blueaerotweaks.registries.BlueItems;
import uk.tfindustries.blueaerotweaks.registries.BlueBlocks;
import uk.tfindustries.blueaerotweaks.registries.BlueCreativeTabs;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BlueAeroTweaks.MODID)
public class BlueAeroTweaks {
    public static final String MODID = "blueaerotweaks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item))));


    public BlueAeroTweaks(IEventBus modEventBus, ModContainer modContainer) {
        //yes
        REGISTRATE.registerEventListeners(modEventBus);

        //yes
        BlueCreativeTabs.register(modEventBus);
        BlueItems.register(modEventBus);
        BlueBlocks.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        //modEventBus.addListener(this::onRegister);
        NeoForge.EVENT_BUS.register(this);

        //modEventBus.addListener(EventPriority.HIGHEST, DataGenerators::gatherData);
        //yes
        modContainer.registerConfig(ModConfig.Type.SERVER, BlueConfig.getSpec());
        //yes
        LOGGER.info("Blue Aero Tweaks started");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    public static Component lang(String path, Object... args) {
        return Component.translatable(MODID + "." + path, args);
    }

    public static ResourceLocation path(final String path) {
        return ResourceLocation.tryBuild(MODID, path);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }


}
