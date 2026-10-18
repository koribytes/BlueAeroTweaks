package uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerItemHandler;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import uk.tfindustries.blueaerotweaks.config.BlueConfig;
import uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes;
import uk.tfindustries.blueaerotweaks.registries.BlueBlocks;
import uk.tfindustries.blueaerotweaks.registries.BlueItems;

import java.util.UUID;

import static uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer.AmmoDeployerBlock.FACING;

public class AmmoDeployerBlockEntity extends KineticBlockEntity implements Clearable {

    public UUID owner;
    protected boolean redstoneLocked;
    ProcessingInventory inventory;
    int cooldownMax;
    protected ItemStack heldItem;
    private IItemHandlerModifiable invHandler;
    private ListTag deferredInventoryList;
    Direction placementSetting;


    AmmoDeployerFakePlayer player;

    public AmmoDeployerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlueBlockEntityTypes.AMMO_DEPLOYER_BLOCK_ENTITY.get(), pos, blockState);
        //temporarily hard coding the withSlotLimit method
        inventory = new ProcessingInventory(this::start).withSlotLimit(false);
        inventory.remainingTime = -1;
        redstoneLocked = false;
        //temp hardcoded 10sec
        cooldownMax = 200;
        //defaults to the same facing as the block
        placementSetting = getBlockState().getValue(FACING);;

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

        if (level.isClientSide)
            return;
        if (player == null)
            return;

        if (inventory.remainingTime > 0) {
            inventory.remainingTime--;
        }

        if (!isWhitelisted()) {
            return;
        }

        if (inventory.remainingTime <= 0) {
            if (!inventory.isEmpty() && canPlaceBlock()) {
                placeBlock();
                inventory.remainingTime = cooldownMax;
            }
        }




        //check if the machine is being rotationally powered
        //check if starting conditions are met for this thing to even place
        //start aka place block
        //blockPlacedRecently = true


        //if blockPlacedRecently = true then progress cooldown timer


        //once cooldown timer is equal to or greater than cooldownMax, blockPlacedRecently = false

    }

    private boolean isWhitelisted() {
        //temporary hard coded
        if (inventory.getStackInSlot(0).is(Items.COAL_BLOCK)) {
            return true;
        }

        return false;
    }

    private void placeBlock()
    {
        //DeployerBlockEntity Activate section
        Vec3 movementVector = getMovementVector();
        Direction direction = getBlockState().getValue(FACING);
        Vec3 center = VecHelper.getCenterOf(worldPosition);
        BlockPos clickedPos = worldPosition.relative(direction, 2);
        player.setXRot(direction == Direction.UP ? -90 : direction == Direction.DOWN ? 90 : 0);
        player.setYRot(direction.toYRot());


        activateInner(player, center, clickedPos, movementVector, inventory.getStackInSlot(0));

    }

    private static void activateInner(AmmoDeployerFakePlayer player, Vec3 vec, BlockPos clickedPos, Vec3 extensionVector, ItemStack invStack) {
        //DeployerHandler ActivateInner section
        Vec3 rayOrigin = vec.add(extensionVector.scale(3 / 2f + 1 / 64f));
        Vec3 rayTarget = vec.add(extensionVector.scale(5 / 2f - 1 / 64f));
        player.setPos(rayOrigin.x, rayOrigin.y, rayOrigin.z);
        BlockPos pos = BlockPos.containing(vec);
        //ItemStack stack = player.getMainHandItem();
        ItemStack stack = invStack;
        //Item item = stack.getItem();

        final ServerLevel level = player.serverLevel();

        InteractionHand hand = InteractionHand.MAIN_HAND;

        // Shoot ray
        ClipContext rayTraceContext =
                new ClipContext(rayOrigin, rayTarget, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        BlockHitResult result = level.clip(rayTraceContext);
        if (result.getBlockPos() != clickedPos)
            result = new BlockHitResult(result.getLocation(), result.getDirection(), clickedPos, result.isInside());
        BlockState clickedState = level.getBlockState(clickedPos);
        Direction face = result.getDirection();
        if (face == null)
            face = Direction.getNearest(extensionVector.x, extensionVector.y, extensionVector.z)
                    .getOpposite();

        AmmoDeployerFakePlayer testPlayer = player;
        testPlayer.setItemInHand(hand, stack);
        UseOnContext itemusecontext = new UseOnContext(testPlayer, hand, result);
        stack.useOn(itemusecontext);
    }


    //WIP
    private boolean canPlaceBlock() {
        return true;
    }

    private void start(ItemStack itemStack) {
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }

    public void discardPlayer() {
        if (player == null)
            return;
        player.getInventory()
                .dropAll();
        //overflowItems.forEach(itemstack -> player.drop(itemstack, true, false));
        player.discard();
        player = null;
    }

    public AmmoDeployerFakePlayer getPlayer() {
        return player;
    }

    protected Vec3 getMovementVector() {
        if (getBlockState().is(BlueBlocks.AMMO_DEPLOYER_BLOCK.get()))
            return Vec3.ZERO;
        return Vec3.atLowerCornerOf(getBlockState().getValue(FACING)
                .getNormal());
    }

    @Override
    public void initialize() {
        super.initialize();
        initHandler();
    }


    private void initHandler() {
        if (invHandler != null)
            return;
        if (level instanceof ServerLevel sLevel) {
            player = new AmmoDeployerFakePlayer(sLevel, owner);
            if (deferredInventoryList != null) {
                player.getInventory()
                        .load(deferredInventoryList);
                deferredInventoryList = null;
                heldItem = player.getMainHandItem();
                sendData();
            }
            Vec3 initialPos = VecHelper.getCenterOf(worldPosition.relative(getBlockState().getValue(FACING)));
            player.setPos(initialPos.x, initialPos.y, initialPos.z);
        }
        //invHandler = createHandler();
    }
}
