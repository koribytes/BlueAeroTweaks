package uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import uk.tfindustries.blueaerotweaks.config.BlueConfig;
import uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes;

import java.util.UUID;

public class AmmoDeployerBlockEntity extends KineticBlockEntity implements Clearable {

    public UUID owner;
    protected boolean redstoneLocked;
    ProcessingInventory inventory;

    public AmmoDeployerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlueBlockEntityTypes.AMMO_DEPLOYER_BLOCK_ENTITY.get(), pos, blockState);
        //temporarily hard coding the withSlotLimit method
        inventory = new ProcessingInventory(this::start).withSlotLimit(false);
        inventory.remainingTime = -1;
        redstoneLocked = false;
    }


    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }


    public void redstoneUpdate() {
        if (level.isClientSide)
            return;
        boolean blockPowered = level.hasNeighborSignal(worldPosition);
        if (blockPowered == redstoneLocked)
            return;
        redstoneLocked = blockPowered;
        sendData();
    }

    @Override
    public void tick() {
        super.tick();

        if (getSpeed() == 0)
            return;

        if (inventory.remainingTime == -1) {
            if (!inventory.isEmpty() && !canPlaceBlock())
                start(inventory.getStackInSlot(0));
            return;
        }


        //conditions to be met
        //

        //check if the machine is being rotationally powered
        //check if starting conditions are met for this thing to even place
        //start aka place block
        //blockPlacedRecently = true


        //if blockPlacedRecently = true then progress cooldown timer


        //once cooldown timer is equal to or greater than cooldownMax, blockPlacedRecently = false

    }

    private boolean canPlaceBlock() {
        return false;
    }

    private void start(ItemStack itemStack) {
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }
}
