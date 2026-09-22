package uk.tfindustries.blueaerotweaks.registries;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
import uk.tfindustries.blueaerotweaks.content.blocks.AirFryer.AirFryerBlock;
import uk.tfindustries.blueaerotweaks.content.blocks.JapaneseCedarLamp.JapaneseCedarLampBlock;
import uk.tfindustries.blueaerotweaks.content.blocks.Squallstone.SquallstoneBlock;

import java.util.function.Supplier;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;


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

    //Japanese cedar wood set
    public static final DeferredBlock<Block> JAPANESE_CEDAR_WOOD = registerBlock("japanese_cedar_wood",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> STRIPPED_JAPANESE_CEDAR_WOOD = registerBlock("stripped_japanese_cedar_wood",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<RotatedPillarBlock> JAPANESE_CEDAR_LOG = registerBlock("japanese_cedar_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_JAPANESE_CEDAR_LOG = registerBlock("stripped_japanese_cedar_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> JAPANESE_CEDAR_PLANKS = registerBlock("japanese_cedar_planks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));


    public static final DeferredBlock<StairBlock> JAPANESE_CEDAR_STAIRS = registerBlock("japanese_cedar_stairs",
            () -> new StairBlock(BlueBlocks.JAPANESE_CEDAR_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));


    public static final DeferredBlock<SlabBlock> JAPANESE_CEDAR_SLAB = registerBlock("japanese_cedar_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<PressurePlateBlock> JAPANESE_CEDAR_PRESSURE_PLATE = registerBlock("japanese_cedar_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<ButtonBlock> JAPANESE_CEDAR_BUTTON = registerBlock("japanese_cedar_button",
            () -> new ButtonBlock(BlockSetType.OAK, 20,
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noCollission()));

    public static final DeferredBlock<FenceBlock> JAPANESE_CEDAR_FENCE = registerBlock("japanese_cedar_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<FenceGateBlock> JAPANESE_CEDAR_FENCE_GATE = registerBlock("japanese_cedar_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK,
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<WallBlock> JAPANESE_CEDAR_WALL = registerBlock("japanese_cedar_wall",
            () -> new WallBlock(
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops()));

    public static final DeferredBlock<DoorBlock> JAPANESE_CEDAR_DOOR = registerBlock("japanese_cedar_door",
            () -> new DoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));

    public static final DeferredBlock<TrapDoorBlock> JAPANESE_CEDAR_TRAPDOOR = registerBlock("japanese_cedar_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD).requiresCorrectToolForDrops().noOcclusion()));


    public static final DeferredBlock<Block> JAPANESE_CEDAR_LAMP = registerBlock("japanese_cedar_lamp",
            () -> new JapaneseCedarLampBlock(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD)
                    .requiresCorrectToolForDrops().lightLevel(state -> state.getValue(JapaneseCedarLampBlock.CLICKED) ? 15 : 0)));


    /*
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

     */




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
