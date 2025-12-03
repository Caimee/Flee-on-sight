package org.sample.fleeonsight_.PlayerSystem;

import net.minecraft.entity.player.PlayerEntity;
import org.sample.fleeonsight_.LogicAttributes;

import static org.sample.fleeonsight_.LogicAttributes.SNEAK_RANGE;

public class PlayerStateManager {

    //player的sneaking状态机
    public static void updateSneakingState(PlayerEntity player, PlayerState state) {
        if (!state.isSneaking && player.isSneaking()) {
            state.isSneaking = true;
        }
        if (state.isSneaking && !player.isSneaking()) {
            state.isSneaking = false;
        }
    }

    public static void playerStateExecute(PlayerEntity player, PlayerState state) {
        if (state.isSneaking) {
            state.detectionRange = SNEAK_RANGE;
        }
        if (!state.isSneaking) {
            state.detectionRange = LogicAttributes.DEFAULT_DETECTION_RANGE;
        }
    }
}
