package uk.tfindustries.blueaerotweaks.content.blocks.HorizontalAmmoDeployer;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer.AmmoDeployerBlockEntity;
import uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes;

public class HorizontalAmmoDeployerBlock extends HorizontalKineticBlock implements IBE<HorizontalAmmoDeployerBlockEntity> {

    public HorizontalAmmoDeployerBlock(Properties properties) {
        super(properties);
    }

    //done?
    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (placer instanceof ServerPlayer)
            withBlockEntityDo(worldIn, pos, adbe -> adbe.owner = placer.getUUID());
    }


    @Override
    public Class<HorizontalAmmoDeployerBlockEntity> getBlockEntityClass() {
        return HorizontalAmmoDeployerBlockEntity.class;
    }

    //done
    @Override
    public BlockEntityType<? extends HorizontalAmmoDeployerBlockEntity> getBlockEntityType() {
        return BlueBlockEntityTypes.HORIZONTAL_AMMO_DEPLOYER_BLOCK_ENTITY.get();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(HORIZONTAL_FACING)
                .getClockWise()
                .getAxis();
    }

    //define redstoneUpdate in the block entity class
    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
        withBlockEntityDo(world, pos, HorizontalAmmoDeployerBlockEntity::redstoneUpdate);
    }

    //done
    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    //done
    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block p_220069_4_, BlockPos p_220069_5_,
                                boolean p_220069_6_) {
        withBlockEntityDo(world, pos, HorizontalAmmoDeployerBlockEntity::redstoneUpdate);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock() != newState.getBlock()) {
            if(level.getBlockEntity(pos) instanceof HorizontalAmmoDeployerBlockEntity horizontalAmmoDeployerBlockEntity) {
                horizontalAmmoDeployerBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return getRotationAxis(state) == face.getAxis();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof HorizontalAmmoDeployerBlockEntity horizontalAmmoDeployerBlockEntity) {

            ItemStack heldByPlayer = stack.copy();

            if (level.isClientSide)
                return ItemInteractionResult.SUCCESS;

            withBlockEntityDo(level, pos, be -> {
                ItemStack heldByDeployer = be.player.getMainHandItem()
                        .copy();
                if (heldByDeployer.isEmpty() && heldByPlayer.isEmpty())
                    return;

                player.setItemInHand(hand, heldByDeployer);
                be.player.setItemInHand(InteractionHand.MAIN_HAND, heldByPlayer);
                be.notifyUpdate();
            });

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.SUCCESS;
    }
}
