package com.muminali13.tanks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoop gameLoop;

    private final Player player;
    private final Joystick joystick;


    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setFocusable(true);
        gameLoop = new GameLoop(this, surfaceHolder);

        player = new Player(getContext(), 500, 500, 30);
        joystick = new Joystick(200, 800, 80, 40);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        gameLoop.startLoop();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public void update() {
        joystick.update();
        player.update(joystick);

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        player.draw(canvas);
        joystick.draw(canvas);


        drawUPS(canvas);
        drawFPS(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.checkPressed((int) event.getX(), (int) event.getY())) {
                    joystick.setPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.isPressed()) {
                    joystick.setActuator(event.getX(), event.getY());
                }
//                player.setPositionX();
//                player.setPositionY();
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setPressed(false);
                joystick.resetActuator();
                return true;
        }

        return super.onTouchEvent(event);

    }

    private void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());

        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.primary);
        paint.setColor(color);
        paint.setTextSize(20);
        canvas.drawText("UPS: " + averageUPS, 20, 25, paint);
    }

    private void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());

        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.primary);
        paint.setColor(color);
        paint.setTextSize(20);

        canvas.drawText("FPS: " + averageFPS, 20, 80, paint);
    }
}
