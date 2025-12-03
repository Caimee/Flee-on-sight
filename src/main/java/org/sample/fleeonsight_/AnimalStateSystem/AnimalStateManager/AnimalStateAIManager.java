package org.sample.fleeonsight_.AnimalStateSystem.AnimalStateManager;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import org.sample.fleeonsight_.PlayerSystem.PlayerState;
import org.sample.fleeonsight_.AnimalStateSystem.Animalstate.MobState;

import static org.sample.fleeonsight_.LogicAttributes.*;

public interface AnimalStateAIManager {

    //logic of fleeing state machine
    default void updateFleeingState(LivingEntity animal, PlayerEntity player, MobState MobState, PlayerState playerState) {
        double distance = animal.distanceTo(player);

        if (!MobState.isFleeing && distance <= playerState.detectionRange && FOVcheck(animal, player)) {
            MobState.isFleeing = true;
        }
        if (MobState.isFleeing && distance >= STOP_RANGE) {
            MobState.isFleeing = false;
            animal.setAttacker(player);//stop fleeing and then panic wander
        }
    }

    //logic of friendly state machine
    default void updateFriendlyState(LivingEntity animal, PlayerEntity player, MobState state) {
        if (!state.isFriendly && FOVcheck(animal, player) && player.isHolding(Items.WHEAT) && (animal.distanceTo(player) < 8)) {
            state.isFriendly = true;//entry friendly state
        }
        if (state.isFriendly && animal.getAttacker() == player) {
            state.isFriendly = false;//exit friendly state
        }
    }

    default boolean FOVcheck(LivingEntity animal, PlayerEntity player) {
        Vec3d vec = player.getEntityPos().subtract(animal.getEntityPos()).normalize();// vector from animal to player
        Vec3d facing = Vec3d.fromPolar(0, animal.getHeadYaw()).normalize();// vector animal's head
        double dot = facing.dotProduct(vec);
        return dot > Math.cos(Math.toRadians(ANGLE * 0.5));// ANGLE is the whole FOV
    }

    //logic of flee
    default void applyFlee_logic(MobEntity animal, PlayerEntity player) {
        Vec3d fromPlayer = animal.getEntityPos().subtract(player.getEntityPos()).normalize();
        Vec3d fleeDir = fromPlayer.multiply(26.5);
        Vec3d targetPos = animal.getEntityPos().add(fleeDir);
        animal.getNavigation().startMovingTo(
                targetPos.x,
                targetPos.y,
                targetPos.z,
                FLEE_SPEED
        );
    }

}
