package uk.tfindustries.blueaerotweaks.block;


public class BlueBlocks {
    /*
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



    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
*/
}
