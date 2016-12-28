package com.agentknopf.fluxling;

import com.agentknopf.fluxling.model.Creature;
import com.agentknopf.fluxling.model.CreatureInfo;
import com.agentknopf.fluxling.model.CreatureKt;
import com.agentknopf.fluxling.model.Point;
import com.agentknopf.fluxling.model.Rectangle;
import com.agentknopf.fluxling.model.UtilsKt;

import android.text.TextUtils;

/**
 * Created by agentknopf on 29.12.16.
 */

public final class TestUtils {

    private static final int MAX_HEIGHT = 150;
    private static final int MAX_WIDTH = 90;
    private static final int MIN_VALUE = 1;
    private static final int COLOR = 256;

    private TestUtils() {
    }

    public static Creature randomCreature() {
        return new Creature(CreatureKt.createName(), new Rectangle(UtilsKt.getInt(MIN_VALUE, MAX_WIDTH), UtilsKt.getInt(MIN_VALUE, MAX_HEIGHT), COLOR));
    }

    public static CreatureInfo randomInfo() {
        return new CreatureInfo(new Point(UtilsKt.getInt(1, MAX_WIDTH), UtilsKt.getInt(1, MAX_HEIGHT)));
    }

}
