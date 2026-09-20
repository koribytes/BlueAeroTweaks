package uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer;

import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AmmoDeployerBlockEntity extends DeployerBlockEntity {

    public AmmoDeployerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
