package com.agentknopf.fluxling.model;

import com.agentknopf.fluxling.actions.AddCreature;
import com.agentknopf.fluxling.actions.MoveAll;
import com.agentknopf.fluxling.view.ViewCallback;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * The game loop computates the current game state and triggers a render at the end of each cycle.l
 *
 * Created by agentknopf on 28.12.16.
 */
public final class GameLoop {

    private final Thread thread;
    private final Loop loop;

    public GameLoop(int width, int height, @NonNull ViewCallback viewCallback) {
        thread = new Thread(loop = new Loop(width, height, viewCallback));
    }

    public void start() {
        thread.start();
    }

    public void suspend() {
        loop.stop();
    }

    private static class Loop implements Runnable {

        private static final String TAG = Loop.class.getCanonicalName();
        private static final int SLEEP_DURATION = 1000;
        private static final int MAX_CREATURES = 20;
        private boolean paused = false;
        private boolean running = true;

        final int height;
        final int width;

        final ViewCallback callback;

        private Loop(int width, int height, @NonNull ViewCallback viewCallback) {
            this.height = height;
            this.width = width;
            this.callback = viewCallback;
        }

        synchronized void stop() {
            resume();
            running = false;
        }

        /**
         * Finishes current cycle and then pauses.
         */
        synchronized void pause() {
            paused = true;
        }

        /**
         * Resumes where it last left off.
         */
        synchronized void resume() {
            paused = false;
        }

        @Override
        public void run() {
            Log.i(TAG, "Started Game Loop.");
            while (running) {
                while (paused) {
                    //We're currently paused, so just sleep
                    try {
                        Thread.sleep(SLEEP_DURATION);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Pausing of game loop has been interrupted.", e);
                    }
                }

                if (Store.INSTANCE.creatureCount() < MAX_CREATURES) {
                    //Add a new creature at a random location
                    Store.INSTANCE.post(new AddCreature(CreatureKt.createCreature(),
                            new CreatureInfo(new Point(UtilsKt.getInt(1, width), UtilsKt.getInt(1, height)))));
                }
                //Move every creature by half their width into an arbitrary direction
                Store.INSTANCE.post(new MoveAll());

                callback.render(Store.INSTANCE);
            }
        }
    }
}
