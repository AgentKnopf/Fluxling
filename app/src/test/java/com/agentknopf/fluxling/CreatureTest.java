package com.agentknopf.fluxling;

import com.agentknopf.fluxling.model.Creature;
import com.agentknopf.fluxling.model.CreatureKt;
import com.agentknopf.fluxling.model.Rectangle;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by agentknopf on 28.12.16.
 */
public final class CreatureTest {

    private static final String TAG = CreatureTest.class.getCanonicalName();

    @Test
    public void createName() {
        for (int i = 0, size = 100; i < size; i++) {
            String name = CreatureKt.createName();
            assertNotNull(name);
            assertTrue(name.length() > 0);
            System.out.println("Created Name = " + name);
        }
    }
}
