package uk.tfindustries.blueaerotweaks.registries;

import com.simibubi.create.content.kinetics.deployer.DeployerRenderer;
import com.simibubi.create.content.kinetics.deployer.DeployerVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import uk.tfindustries.blueaerotweaks.BlueAeroTweaks;
import uk.tfindustries.blueaerotweaks.content.AirFryer.AirFryerBlockEntity;
import uk.tfindustries.blueaerotweaks.content.AmmoDeployer.AmmoDeployerBlockEntity;

import java.util.function.Supplier;

import static uk.tfindustries.blueaerotweaks.BlueAeroTweaks.REGISTRATE;

public final class BlueBlockEntityTypes {

    //block entities
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BlueAeroTweaks.MODID);

    /*
    public static final Supplier<BlockEntityType<AirFryerBlockEntity>> MY_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "my_block_entity",
            // The block entity type, created using a builder.
            () -> BlockEntityType.Builder.of(
                            // The supplier to use for constructing the block entity instances.
                            AirFryerBlockEntity::new,
                            // A vararg of blocks that can have this block entity.
                            // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                            BlueBlocks.AIR_FRYER_BLOCK.get(), BlueBlocks.MY_BLOCK_2.get()
                    )
                    // Build using null; vanilla does some datafixer shenanigans with the parameter that we don't need.
                    .build(null)
    );

     */

    /*
    public static final BlockEntityEntry<AmmoDeployerBlockEntity> AMMO_DEPLOYER = REGISTRATE
            .blockEntity("ammo_deployer", AmmoDeployerBlockEntity::new)
            .visual(() -> DeployerVisual::new)
            .validBlocks(BlueBlocks.AMMO_DEPLOYER)
            .renderer(() -> DeployerRenderer::new)
            .register();

     */




    public static void register() {}
}
