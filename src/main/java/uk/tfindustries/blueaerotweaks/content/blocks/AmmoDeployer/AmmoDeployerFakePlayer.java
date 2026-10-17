package uk.tfindustries.blueaerotweaks.content.blocks.AmmoDeployer;

import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.fml.common.EventBusSubscriber;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AmmoDeployerFakePlayer extends DeployerFakePlayer {

    public AmmoDeployerFakePlayer(ServerLevel world, @Nullable UUID owner) {
        super(world, owner);
    }
}
