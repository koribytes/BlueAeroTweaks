package uk.tfindustries.blueaerotweaks.content.blocks.HorizontalAmmoDeployer;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonMunitionBlock;
import uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer.AmmoDeployerBlockEntity;
import uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer.AmmoDeployerFakePlayer;
import uk.tfindustries.blueaerotweaks.registries.BlueBlockEntityTypes;
import uk.tfindustries.blueaerotweaks.registries.BlueBlocks;

import java.util.UUID;

import static com.simibubi.create.content.kinetics.base.HorizontalKineticBlock.HORIZONTAL_FACING;
import static uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer.AmmoDeployerBlock.FACING;

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

    AmmoDeployerFakePlayer player;

    public HorizontalAmmoDeployerBlockEntity(BlockPos pos, BlockState state) {
        super(BlueBlockEntityTypes.HORIZONTAL_AMMO_DEPLOYER_BLOCK_ENTITY.get(), pos, state);
        timer = -1;
        redstoneLocked = false;
        //temp hardcoded default 10sec
        cooldownMax = 200;
        //temp hardcoded default 1
        distanceSetting = 2;
        //defaults to the same facing as the block
        placementSetting = AmmoDeployerBlockEntity.TransformationSettings.RIGHT;
    }

    @Override
    public void clearContent() {

    }

    public void drops() {
        /*
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);

         */
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

        if (timer > 0) {
            timer--;
        }

        if (!isWhitelisted()) {
            return;
        }

        if (timer <= 0) {
            if (!player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && canPlaceBlock()) {
                placeBlock();
                timer = cooldownMax;
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
        return Block.byItem(player.getItemInHand(InteractionHand.MAIN_HAND).getItem()) instanceof BigCannonMunitionBlock;
    }

    //WIP might not be needed?
    private boolean canPlaceBlock() {
        return true;
    }

    private void placeBlock()
    {
        //DeployerBlockEntity Activate section
        Vec3 movementVector = getMovementVector();
        Direction direction = getBlockState().getValue(HORIZONTAL_FACING);
        Vec3 center = VecHelper.getCenterOf(worldPosition);
        BlockPos clickedPos = worldPosition.relative(direction, distanceSetting);
        player.setXRot(direction == Direction.UP ? -90 : direction == Direction.DOWN ? 90 : 0);
        player.setYRot(direction.toYRot());

        activateInner(player, center, clickedPos, movementVector, player.getItemInHand(InteractionHand.MAIN_HAND));
    }

    private void activateInner(AmmoDeployerFakePlayer player, Vec3 vec, BlockPos clickedPos, Vec3 extensionVector, ItemStack invStack) {
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

        //testing
        Direction test = player.getNearestViewDirection();
        if (test == Direction.SOUTH) {
            result = new BlockHitResult(result.getLocation(), calcPlacementDirection(player.getDirection()), clickedPos, result.isInside());
        }

        else {
            if (result.getBlockPos() != clickedPos)
                result = new BlockHitResult(result.getLocation(), calcPlacementDirection(player.getNearestViewDirection()), clickedPos, result.isInside());
        }
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

    private Direction calcPlacementDirection(Direction direction) {
        switch (placementSetting) {
            case FORE:
                //default nothing
                break;
            //opposite
            case BACK:
                direction = direction.getOpposite();
                break;
            //clockwise x
            case RIGHT:
                direction = rotateRight(direction);
                break;
            //counterclockwise x
            case LEFT:
                direction = rotateLeft(direction);
                break;
            case UP:
                direction = rotateUp(direction);
                break;
            case DOWN:
                direction = rotateDown(direction);
                break;

        }

        return direction;
    }

    private Direction rotateRight(Direction direction) {
        switch (direction) {
            case NORTH, SOUTH, EAST, WEST:
                direction = direction.getClockWise(Direction.Axis.Y);
                break;
        }
        return direction;
    }

    private Direction rotateLeft(Direction direction) {
        return rotateRight(direction).getOpposite();
    }

    private Direction rotateUp(Direction direction) {
        switch (direction) {
            case NORTH:
                direction = direction.getCounterClockWise(Direction.Axis.Z);
                break;
            case SOUTH:
                direction = direction.getClockWise(Direction.Axis.Z);
                break;
            case EAST:
                direction = direction.getCounterClockWise(Direction.Axis.X);
                break;
            case WEST:
                direction = direction.getClockWise(Direction.Axis.X);
                break;
        }
        return direction;
    }

    private Direction rotateDown(Direction direction) {
        return rotateUp(direction).getOpposite();
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
        if (getBlockState().is(BlueBlocks.HORIZONTAL_AMMO_DEPLOYER_BLOCK.get()))
            return Vec3.ZERO;
        return Vec3.atLowerCornerOf(getBlockState().getValue(HORIZONTAL_FACING)
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
            Vec3 initialPos = VecHelper.getCenterOf(worldPosition.relative(getBlockState().getValue(HORIZONTAL_FACING)));
            player.setPos(initialPos.x, initialPos.y, initialPos.z);
        }
        //invHandler = createHandler();
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        compound.putInt("Timer", timer);
        compound.putInt("DistanceSetting", distanceSetting);
        compound.putBoolean("Powered", redstoneLocked);
        if (owner != null)
            compound.putUUID("Owner", owner);

        if (player != null) {
            ListTag invNBT = new ListTag();
            player.getInventory()
                    .save(invNBT);
            compound.put("Inventory", invNBT);
            compound.put("HeldItem", player.getMainHandItem().saveOptional(registries));
            //compound.put("Overflow", NBTHelper.writeItemList(overflowItems, registries));
        } else if (deferredInventoryList != null) {
            compound.put("Inventory", deferredInventoryList);
        }



        super.write(compound, registries, clientPacket);

        if (!clientPacket)
            return;
        if (player == null)
            return;
        compound.put("HeldItem", player.getMainHandItem().saveOptional(registries));
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        timer = compound.getInt("Timer");
        distanceSetting = compound.getInt("DistanceSetting");
        redstoneLocked = compound.getBoolean("Powered");
        if (compound.contains("Owner"))
            owner = compound.getUUID("Owner");

        deferredInventoryList = compound.getList("Inventory", Tag.TAG_COMPOUND);
        //overflowItems = NBTHelper.readItemList(compound.getList("Overflow", Tag.TAG_COMPOUND), registries);\

        if (compound.contains("HeldItem")) {
            heldItem = ItemStack.parseOptional(registries, compound.getCompound("HeldItem"));
        }

        super.read(compound, registries, clientPacket);

        if (!clientPacket)
            return;
    }

}
