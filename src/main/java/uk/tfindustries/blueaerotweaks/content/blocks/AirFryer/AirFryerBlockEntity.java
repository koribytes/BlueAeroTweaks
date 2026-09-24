package uk.tfindustries.blueaerotweaks.content.blocks.AirFryer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes.AIR_FRYER_BLOCK_ENTITY;

public class AirFryerBlockEntity extends BlockEntity {
    public AirFryerBlockEntity(BlockPos pos, BlockState state) {
        super(AIR_FRYER_BLOCK_ENTITY.get(), pos, state);
    }
}
