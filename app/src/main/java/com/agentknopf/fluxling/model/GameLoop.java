package com.agentknopf.fluxling.model;

import com.agentknopf.fluxling.actions.AddCreature;
import com.agentknopf.fluxling.actions.EventBus;
import com.agentknopf.fluxling.actions.GameActions;
import com.agentknopf.fluxling.actions.MoveAll;
import com.agentknopf.fluxling.actions.UiAction;
import com.agentknopf.fluxling.actions.UiActions;
import com.agentknopf.fluxling.view.ViewCallback;
import com.fluxling.agentknopf.fluxling.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.INSTANCE.register(this);
        thread.start();
    }

    public void stop() {
        loop.stop();
        EventBus.INSTANCE.unRegister(this);
    }

    private static class Loop implements Runnable {

        private static final String TAG = Loop.class.getCanonicalName();
        private static final int SLEEP_DURATION = 1000;
        private static final int MAX_CREATURES = 100;
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
        private synchronized void pause() {
            paused = true;
        }

        /**
         * Resumes where it last left off.
         */
        private synchronized void resume() {
            paused = false;
        }

        public boolean isPaused() {
            return paused;
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
                long startTime = System.currentTimeMillis();

                if (Store.INSTANCE.creatureCount() < MAX_CREATURES) {
                    //Add a new creature at a random location
                    Store.INSTANCE.post(new AddCreature(CreatureKt.createCreature(),
                            new CreatureInfo(new Point(UtilsKt.getInt(1, width), UtilsKt.getInt(1, height)))));
                }
                //Move every creature by half their width into an arbitrary direction
                Store.INSTANCE.post(new MoveAll());

                callback.render(Store.INSTANCE);

                //Calculate frame time
                long frameTime = System.currentTimeMillis() - startTime;
                if (frameTime > 0) {
                    Log.i(TAG, "FPS: " + Math.round(1000 / frameTime));
                }
            }
        }
    }

    /**
     * Eventbus method.
     *
     * @param action action that occurred.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAction(GameActions action) {
        switch (action) {
            case TOGGLE_PAUSE_RESUME:
                if (loop.isPaused()) {
                    loop.resume();
                    EventBus.INSTANCE.post(new UiAction(loop.callback.getString(R.string.action_resumed), UiActions.SHOW_MESSAGE));
                    EventBus.INSTANCE.post(new UiAction(String.valueOf(R.drawable.ic_pause), UiActions.CHANGE_FAB_ICON));
                } else {
                    loop.pause();
                    EventBus.INSTANCE.post(new UiAction(loop.callback.getString(R.string.action_paused), UiActions.SHOW_MESSAGE));
                    EventBus.INSTANCE.post(new UiAction(String.valueOf(R.drawable.ic_play), UiActions.CHANGE_FAB_ICON));
                }
            default:
                break;
        }
    }
}
