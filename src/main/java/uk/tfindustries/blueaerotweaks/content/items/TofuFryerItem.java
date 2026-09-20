package uk.tfindustries.blueaerotweaks.content.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import uk.tfindustries.blueaerotweaks.registries.BlueBlocks;

import java.util.Map;

public class TofuFryerItem extends Item {
    private static final Map<Block, Block> TOFU_FRYER_MAP =
            Map.of(
                    BlueBlocks.RAW_TOFU_BLOCK.get(), BlueBlocks.FRIED_TOFU_BLOCK.get()
            );
    public TofuFryerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (TOFU_FRYER_MAP.containsKey(clickedBlock)) {
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(context.getClickedPos(), TOFU_FRYER_MAP.get(clickedBlock).defaultBlockState());

                // Damages the fryer but not using this
                /*
                context.getItemInHand().hurtAndBreak(1,((ServerLevel) level), context.getPlayer(),
                        item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
                 */
                level.playSound(null, context.getClickedPos(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
