package uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer;


import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
}
