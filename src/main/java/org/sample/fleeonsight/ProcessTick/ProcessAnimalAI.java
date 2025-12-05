package org.sample.fleeonsight.ProcessTick;


import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.sample.fleeonsight.AnimalSystem.AnimalGroupManager.AnimalGroupFleeManager;
import org.sample.fleeonsight.AnimalSystem.AnimalStateMachine.AnimalStateMachine;
import org.sample.fleeonsight.AnimalSystem.Animalstate.MobState;
import org.sample.fleeonsight.PlayerSystem.PlayerState;
import org.sample.fleeonsight.PlayerSystem.PlayerStateMachine;

import java.util.List;

import static org.sample.fleeonsight.EntityUtils.*;
import static org.sample.fleeonsight.ProcessTick.ProcessAnimalAISupport.*;

// Main class to process animal AI each tick
public class ProcessAnimalAI {

    // Process AI for all relevant animals in the world
    public static void processAnimalAI(ServerWorld world) {
        var sheepGroup = getAllLoadedSheep(world);
        var pigGroup = getAllLoadedPig(world);
        var cowGroup = getAllLoadedCow(world);
        processAI(world, sheepGroup);
        processAI(world, cowGroup);
        processAI(world, pigGroup);
    }

    // Generic method to process AI for a group of animals
    public static void processAI(ServerWorld world, List<? extends AnimalEntity> animalGroup) {
        if (animalGroup == null || animalGroup.isEmpty()) {
            return;
        }
        AnimalStateMachine aiHandler = getAnimalAI(animalGroup. get(0));
        for (AnimalEntity animal : animalGroup) {
            PlayerEntity player = getNearbyPlayer(world, animal);
            if (player == null) {
                continue;
            }
            PlayerState playerState = getPlayerState(player);
            PlayerStateMachine.updateSneakingState(player, playerState);
            PlayerStateMachine.playerStateExecute(playerState);
            MobState animalState = getMobState(animal);
            aiHandler.updateFriendlyState(animal, player, animalState);
            aiHandler.updateFleeingState(animal, player, animalState, playerState);
            AnimalGroupFleeManager.manageGroupFlee(animal);
            if (!animalState.isFriendly && animalState.isFleeing) {
                aiHandler.applyFlee_logic(animal, player);
            }
        }
    }
}
