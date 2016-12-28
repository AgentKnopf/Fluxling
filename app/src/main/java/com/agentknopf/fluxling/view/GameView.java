package com.agentknopf.fluxling.view;

import com.agentknopf.fluxling.model.Creature;
import com.agentknopf.fluxling.model.CreatureInfo;
import com.agentknopf.fluxling.model.GameLoop;
import com.agentknopf.fluxling.model.Store;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Iterator;
import java.util.Map;

/**
 * Draws the entire game board.
 *
 * Created by agentknopf on 28.12.16.
 */
public class GameView extends SurfaceView implements ViewCallback {

    private static final String TAG = GameView.class.getCanonicalName();
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;
    private GameLoop loop;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, String.format("Screen changed dimensions to %s x %s", w, h));
        if (loop == null) {
            loop = new GameLoop(w, h, this);
            loop.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void render(Store store) {
        if (holder == null) {
            Log.i(TAG, "Created holder and paint instance.");
            //First run, initialize
            holder = getHolder();
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            Log.i(TAG, "Rendering started...");
            Creature creature;
            CreatureInfo info;

            final Iterator<Map.Entry<Creature, CreatureInfo>> iterator = store.getIterator();
            while (iterator.hasNext()) {
                Map.Entry<Creature, CreatureInfo> entry = iterator.next();
                creature = entry.getKey();
                info = entry.getValue();
                paint.setColor(creature.getDimensions().getColor());
                canvas.drawRect(new Rect(info.getDrawingOrigin().getX(), info.getDrawingOrigin().getY(),
                        info.getDrawingOrigin().getX() + creature.getDimensions().getWidth(),
                        info.getDrawingOrigin().getY() + creature.getDimensions().getHeight()), paint);
            }
            Log.i(TAG, "Rendering finished...");
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
