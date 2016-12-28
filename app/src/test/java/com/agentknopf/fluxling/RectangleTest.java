package com.agentknopf.fluxling;

import com.agentknopf.fluxling.model.Point;
import com.agentknopf.fluxling.model.Rectangle;
import com.agentknopf.fluxling.model.RectangleKt;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by agentknopf on 28.12.16.
 */
public final class RectangleTest {

    @Test
    public void createOffset() {
        final Rectangle rectangle = new Rectangle(100, 90, 50);
        final Random random = new Random();
        for (int i = 0, size = 100; i < size; i++) {
            Point point = RectangleKt.createOffset(rectangle, random);
            assertEquals("Absolute X value must not change", 50, Math.abs(point.getX()));
            assertEquals("Absolute Y value must not change", 45, Math.abs(point.getY()));
            System.out.println("Transformed to " + point.toString());
        }
    }
}
