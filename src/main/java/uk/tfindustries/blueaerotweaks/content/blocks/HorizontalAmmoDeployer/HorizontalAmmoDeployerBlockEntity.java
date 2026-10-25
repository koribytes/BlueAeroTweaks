package uk.tfindustries.blueaerotweaks.content.blocks.HorizontalAmmoDeployer;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer.AmmoDeployerBlockEntity;
import uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes;

import java.util.UUID;

public class HorizontalAmmoDeployerBlockEntity extends KineticBlockEntity implements Clearable {

    public UUID owner;
    protected boolean redstoneLocked;
    //ProcessingInventory inventory;
    int cooldownMax, timer;
    protected ItemStack heldItem;
    private IItemHandlerModifiable invHandler;
    private ListTag deferredInventoryList;
    AmmoDeployerBlockEntity.TransformationSettings placementSetting;
    int distanceSetting;

    public HorizontalAmmoDeployerBlockEntity(BlockPos pos, BlockState state) {
        super(BlueBlockEntityTypes.HORIZONTAL_AMMO_DEPLOYER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void clearContent() {

    }
}
