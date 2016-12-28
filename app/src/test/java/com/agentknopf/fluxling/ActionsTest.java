package com.agentknopf.fluxling;

import com.agentknopf.fluxling.actions.AddCreature;
import com.agentknopf.fluxling.actions.RemoveCreature;
import com.agentknopf.fluxling.model.AppState;
import com.agentknopf.fluxling.model.Creature;
import com.agentknopf.fluxling.model.CreatureInfo;

import org.junit.Test;

import static com.agentknopf.fluxling.TestUtils.randomCreature;
import static com.agentknopf.fluxling.TestUtils.randomInfo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests actions.
 *
 * Created by agentknopf on 29.12.16.
 */
public final class ActionsTest {

    @Test
    public void removeCreature() {
        final Creature creature = randomCreature();
        final CreatureInfo info = randomInfo();
        AppState state = new AppState();
        //Add two creatures
        state = new AddCreature(creature, info).execute(state);
        state = new AddCreature(randomCreature(), randomInfo()).execute(state);
        assertEquals(2, state.creatureCount());

        state = new RemoveCreature(creature).execute(state);
        assertEquals(1, state.creatureCount());
        assertFalse(state.getCreatureList().containsKey(creature));
    }

}
