package uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer;


import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import uk.tfindustries.blueaerotweaks.content.blocks.AirFryer.AirFryerBlockEntity;
import uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes;

public class AmmoDeployerBlock extends DirectionalAxisKineticBlock implements IBE<AmmoDeployerBlockEntity> {
    public AmmoDeployerBlock(Properties properties) {
        super(properties);
    }


    //done?
    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (placer instanceof ServerPlayer)
            withBlockEntityDo(worldIn, pos, adbe -> adbe.owner = placer.getUUID());
    }

    //done
    @Override
    public Class<AmmoDeployerBlockEntity> getBlockEntityClass() {
        return AmmoDeployerBlockEntity.class;
    }

    //done
    @Override
    public BlockEntityType<? extends AmmoDeployerBlockEntity> getBlockEntityType() {
        return BlueBlockEntityTypes.AMMO_DEPLOYER_BLOCK_ENTITY.get();
    }

    //define redstoneUpdate in the block entity class
    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
        withBlockEntityDo(world, pos, AmmoDeployerBlockEntity::redstoneUpdate);
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
        withBlockEntityDo(world, pos, AmmoDeployerBlockEntity::redstoneUpdate);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock() != newState.getBlock()) {
            if(level.getBlockEntity(pos) instanceof AmmoDeployerBlockEntity ammoDeployerBlockEntity) {
                ammoDeployerBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof AmmoDeployerBlockEntity ammoDeployerBlockEntity) {
            //if the inventory is empty and a stack is in hand, place it in the block
            if(ammoDeployerBlockEntity.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty()) {
                ammoDeployerBlockEntity.inventory.setStackInSlot(0, stack.copy());
                stack.shrink(64);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            }
            //if the hand is empty, grab the items inside
            else if(stack.isEmpty()) {
                ItemStack stackOnAmmoDeployer = ammoDeployerBlockEntity.inventory.getStackInSlot(0);
                player.setItemInHand(InteractionHand.MAIN_HAND, stackOnAmmoDeployer);
                ammoDeployerBlockEntity.clearContent();
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

}
