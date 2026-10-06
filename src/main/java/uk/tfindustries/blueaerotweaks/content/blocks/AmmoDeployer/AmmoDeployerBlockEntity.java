package uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer;

import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes;

public class AmmoDeployerBlockEntity extends DeployerBlockEntity {

    public AmmoDeployerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlueBlockEntityTypes.AMMO_DEPLOYER_BLOCK_ENTITY.get(), pos, blockState);
    }


}
