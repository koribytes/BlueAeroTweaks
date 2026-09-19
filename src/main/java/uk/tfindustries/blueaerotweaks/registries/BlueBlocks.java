package uk.tfindustries.blueaerotweaks.registries;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import com.simibubi.create.content.kinetics.deployer.DeployerMovementBehaviour;
import com.simibubi.create.content.kinetics.deployer.DeployerMovingInteraction;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
import uk.tfindustries.blueaerotweaks.config.BlueStress;
import uk.tfindustries.blueaerotweaks.content.AirFryer.AirFryerBlock;
import uk.tfindustries.blueaerotweaks.content.AirFryer.AirFryerBlockEntity;
import uk.tfindustries.blueaerotweaks.content.AmmoDeployer.AmmoDeployerBlock;
import uk.tfindustries.blueaerotweaks.content.Squallstone.SquallstoneBlock;

import java.util.function.Supplier;

import static com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour.interactionBehaviour;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static uk.tfindustries.blueaerotweaks.BlueAeroTweaks.REGISTRATE;


public class BlueBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BlueAeroTweaks.MODID);



    public static final DeferredBlock<Block> RAW_TOFU_BLOCK = registerBlock("raw_tofu_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2f).sound(SoundType.WET_SPONGE)));

    public static final DeferredBlock<Block> FRIED_TOFU_BLOCK = registerBlock("fried_tofu_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2f).sound(SoundType.WET_SPONGE)));

    public static final DeferredBlock<Block> SQUALLSTONE_BLOCK = registerBlock("squallstone_block",
            () -> new SquallstoneBlock(BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> AIR_FRYER_BLOCK = registerBlock("air_fryer_block",
            () -> new AirFryerBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));



    public static final BlockEntry<AmmoDeployerBlock> AMMO_DEPLOYER = REGISTRATE.block("ammo_deployer", AmmoDeployerBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.directionalAxisBlockProvider())
            .transform(BlueStress.setImpact(4.0))
            .onRegister(movementBehaviour(new DeployerMovementBehaviour()))
            .onRegister(interactionBehaviour(new DeployerMovingInteraction()))
            .item(AssemblyOperatorBlockItem::new)
            .tag(AllTags.AllItemTags.CONTRAPTION_CONTROLLED.tag)
            .transform(customItemModel())
            .register();




    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        BlueItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
