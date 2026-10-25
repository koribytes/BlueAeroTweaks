package uk.tfindustries.blueaerotweaks.content.blocks.HorizontalAmmoDeployer;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer.AmmoDeployerBlockEntity;
import uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes;

public class HorizontalAmmoDeployerBlock extends HorizontalKineticBlock implements IBE<HorizontalAmmoDeployerBlockEntity> {

    //done?
    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (placer instanceof ServerPlayer)
            withBlockEntityDo(worldIn, pos, adbe -> adbe.owner = placer.getUUID());
    }

    public HorizontalAmmoDeployerBlock(Properties properties) {
        super(properties);
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
}
