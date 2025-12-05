package org.sample.fleeonsight.ProcessTick;

import net.minecraft.entity.passive.AnimalEntity;
import org.sample.fleeonsight.AnimalSystem.AnimalStateMachine.AnimalStateMachine;
import org.sample.fleeonsight.AnimalSystem.AnimalStateMachine.CowStateMachine;
import org.sample.fleeonsight.AnimalSystem.AnimalStateMachine.PigStateMachine;
import org.sample.fleeonsight.AnimalSystem.AnimalStateMachine.SheepStateMachine;

// Provides access to different animal AI state machines
public class ProcessAnimalAISupport {
    private final static SheepStateMachine SheepAI = new SheepStateMachine();
    private final static PigStateMachine PigAI = new PigStateMachine();
    private final static CowStateMachine CowAI = new CowStateMachine();

    // Returns the appropriate AI state machine for the given animal
    public static AnimalStateMachine getAnimalAI(AnimalEntity animal) {
        if (animal instanceof net.minecraft.entity.passive.SheepEntity) {
            return SheepAI;
        } else if (animal instanceof net.minecraft.entity.passive.PigEntity) {
            return PigAI;
        } else if (animal instanceof net.minecraft.entity.passive.CowEntity) {
            return CowAI;
        } else {
            throw new IllegalArgumentException("Unsupported animal type");
        }
    }
}
