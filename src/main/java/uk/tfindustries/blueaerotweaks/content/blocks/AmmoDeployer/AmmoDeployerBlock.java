package uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer;


import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.Nullable;

public class AmmoDeployerBlock extends DeployerBlock {


    public AmmoDeployerBlock(Properties properties) {
        super(properties);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AmmoDeployerBlockEntity(blockPos, blockState);
    }

    //override the shape method getShaped

    //testing placement method


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        Component message = Component.literal("Testing");
        player.sendSystemMessage(message);
        InteractionHand hand = InteractionHand.MAIN_HAND;
        Vec3 worldPosition = pos.getBottomCenter();
        BlockPos clickedPos = BlockPos.containing(worldPosition.relative(Direction.NORTH, 2));

        UseOnContext itemusecontext = new UseOnContext(player, hand, hitResult);

        ItemStack stack = Items.COAL_BLOCK.getDefaultInstance();

        stack.useOn(itemusecontext);

        //return super.useWithoutItem(state, level, pos, player, hitResult);
        return InteractionResult.SUCCESS;
    }


}
