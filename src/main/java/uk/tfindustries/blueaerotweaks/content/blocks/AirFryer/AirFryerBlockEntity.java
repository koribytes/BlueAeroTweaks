package uk.tfindustries.blueaerotweaks.content.blocks.AirFryer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes;
import uk.tfindustries.blueaerotweaks.registries.BlueItems;

public class AirFryerBlockEntity extends BlockEntity implements MenuProvider {

    public final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 3;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    protected final ContainerData data;
    //protected final ContainerData fuelData;
    private int progress = 0;
    private int progressMax = 72;
    private int fuelBurnProgress = 0;
    private int fuelBurnProgressMax = 800;


    public AirFryerBlockEntity(BlockPos pos, BlockState state) {
        super(BlueBlockEntityTypes.AIR_FRYER_BLOCK_ENTITY.get(), pos, state);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> AirFryerBlockEntity.this.progress;
                    case 1 -> AirFryerBlockEntity.this.progressMax;
                    case 2 -> AirFryerBlockEntity.this.fuelBurnProgress;
                    case 3 -> AirFryerBlockEntity.this.fuelBurnProgressMax;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0: AirFryerBlockEntity.this.progress = value;
                    case 1: AirFryerBlockEntity.this.progressMax = value;
                    case 2: AirFryerBlockEntity.this.fuelBurnProgress = value;
                    case 3: AirFryerBlockEntity.this.fuelBurnProgressMax = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
        /*
        fuelData = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> AirFryerBlockEntity.this.fuelBurnProgress;
                    case 1 -> AirFryerBlockEntity.this.fuelBurnProgressMax;
                    default -> 2;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0: AirFryerBlockEntity.this.fuelBurnProgress = value;
                    case 1: AirFryerBlockEntity.this.fuelBurnProgressMax = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

         */
    }


    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }


    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("air_fryer.progress", progress);
        tag.putInt("air_fryer.progress_max", progressMax);
        tag.putInt("air_fryer.fuel_burn_progress", fuelBurnProgress);
        tag.putInt("air_fryer.fuel_burn_progress_max", fuelBurnProgressMax);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("air_fryer.progress");
        progressMax = tag.getInt("air_fryer.progress_max");
        fuelBurnProgress = tag.getInt("air_fryer.fuel_burn_progress");
        fuelBurnProgressMax = tag.getInt("air_fryer.fuel_burn_progress_max");
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.blueaerotweaks.air_fryer_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new AirFryerMenu(i, inventory, this, this.data);
    }

    // The signature of this method matches the signature of the BlockEntityTicker functional interface.
    public static void tick(Level level, BlockPos pos, BlockState state, AirFryerBlockEntity blockEntity) {
        //begins burning fuel when needed
        if (!blockEntity.hasFuelBurning() && blockEntity.hasFuel()) {
            if (blockEntity.hasRecipe()) {
                blockEntity.startFuelBurn();
            }
        }
        //if there is fuel burning, increment it
        if (blockEntity.hasFuelBurning()) {
            blockEntity.incrementFuelBurn();
        }

        else {
            //blockEntity.turnOff();
        }

        //if there is a valid recipe, check for fuel
        if(blockEntity.hasRecipe()) {
            //if there is fuel burning, increment the processes
            if (blockEntity.hasFuelBurning()) {
                blockEntity.increaseCraftingProgress();
                setChanged(level, pos, state);
                //if an item is done, craft it and reset progress
                if (blockEntity.hasCraftingFinished()) {
                    blockEntity.craftItem();
                    blockEntity.resetProgress();
                }
            }

            //when there is no more fuel left burning or in the slot, start decreasing the crafting progress
            else {
                blockEntity.decreaseCraftingProgress();
            }
        }
        //if there isn't a valid recipe, reset all progress
        else {
            blockEntity.resetProgress();
        }
    }

    private boolean hasRecipe() {
        //temporarily hard coding will fix later
        ItemStack output = new ItemStack(BlueItems.FRIED_TOFU.get());
        return inventory.getStackInSlot(INPUT_SLOT).is(BlueItems.RAW_TOFU) &&
                canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);

    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        // true if the slot is empty or is the same item type
        return inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                inventory.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : inventory.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = inventory.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    private void decreaseCraftingProgress() {
        progress--;
    }

    private void incrementFuelBurn() {
        fuelBurnProgress--;
    }

    private void startFuelBurn() {
        inventory.extractItem(FUEL_SLOT, 1, false);
        fuelBurnProgress = fuelBurnProgressMax;
    }

    private boolean hasFuel() {
        //temporarily hard coding will fix later
        return inventory.getStackInSlot(FUEL_SLOT).is(BlueItems.BINCHOTAN);
    }

    private void resetProgress() {
        progress = 0;
        progressMax = 72;
    }

    private void craftItem() {
        //temporarily hard coding will fix later
        ItemStack output = new ItemStack(BlueItems.FRIED_TOFU.get(), 1);

        inventory.extractItem(INPUT_SLOT, 1, false);
        inventory.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                inventory.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.progressMax;
    }

    private void increaseCraftingProgress() {
        progress++;
    }


    private boolean hasFuelBurning() {
        return fuelBurnProgress != 0;
    }



}
