package com.agentknopf.fluxling;

import com.agentknopf.fluxling.actions.AddCreature;
import com.agentknopf.fluxling.actions.MoveAll;
import com.agentknopf.fluxling.model.AppState;
import com.agentknopf.fluxling.model.Creature;
import com.agentknopf.fluxling.model.CreatureInfo;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.agentknopf.fluxling.TestUtils.randomCreature;
import static com.agentknopf.fluxling.TestUtils.randomInfo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by agentknopf on 28.12.16.
 */
public final class StateTest {

    @Test
    public void emptyStateList() {
        AppState state = new AppState();
        assertEquals(0, state.creatureCount());
    }

    @Test
    public void reduceStateAddToList() {
        AppState state = new AppState();

        state = state.reduceState(new AddCreature(randomCreature(), randomInfo()));
        state = state.reduceState(new AddCreature(randomCreature(), randomInfo()));
        assertEquals(2, state.creatureCount());
    }


    @Test
    public void reduceStateAddToListWithOneInitial() {
        Map<Creature, CreatureInfo> map = new HashMap<>();
        map.put(randomCreature(), randomInfo());
        AppState state = new AppState(map);

        state = state.reduceState(new AddCreature(randomCreature(), randomInfo()));
        state = state.reduceState(new AddCreature(randomCreature(), randomInfo()));
        assertEquals(3, state.creatureCount());
    }

    @Test
    public void reduceStateMoveAll() {
        Map<Creature, CreatureInfo> map = new HashMap<>();
        map.put(randomCreature(), randomInfo());
        map.put(randomCreature(), randomInfo());
        map.put(randomCreature(), randomInfo());
        AppState originalState = new AppState(map);
        AppState modifiedState = originalState.reduceState(new MoveAll());

        for (Creature creature : modifiedState.getCreatureList().keySet()) {
            //We didn't change the keys
            assertTrue(originalState.getCreatureList().containsKey(creature));
            assertNotEquals(originalState.getCreatureList().get(creature), modifiedState.getCreatureList().get(creature));
        }
    }
}
