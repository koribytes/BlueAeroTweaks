package uk.tfindustries.blueaerotweaks.content.blocks.AirFryer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import static uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes.AIR_FRYER_BLOCK_ENTITY;


public class AirFryerBlock extends Block implements EntityBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;


    public AirFryerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AirFryerBlockEntity(pos, state);
    }



    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, LIT});
    }


    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock() != newState.getBlock()) {
            if(level.getBlockEntity(pos) instanceof AirFryerBlockEntity airFryerBlockEntity) {
                airFryerBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof AirFryerBlockEntity airFryerBlockEntity) {
            if(!level.isClientSide()) {
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(airFryerBlockEntity, Component.literal("Air Fryer")), pos);
                return ItemInteractionResult.SUCCESS;
            }
            /*
            if (airFryerBlockEntity.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty()) {
                airFryerBlockEntity.inventory.insertItem(0, stack.copy(), false);
                stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            } else if (stack.isEmpty()) {
                ItemStack stackOnPedestal = airFryerBlockEntity.inventory.extractItem(0, 1, false);
                player.setItemInHand(InteractionHand.MAIN_HAND, stackOnPedestal);
                airFryerBlockEntity.clearContents();
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }

             */
        }
        return ItemInteractionResult.SUCCESS;
    }

    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        // You can return different tickers here, depending on whatever factors you want. A common use case would be
        // to return different tickers on the client or server, only tick one side to begin with,
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return createTickerHelper(type, AIR_FRYER_BLOCK_ENTITY.get(), AirFryerBlockEntity::tick);
    }



}
