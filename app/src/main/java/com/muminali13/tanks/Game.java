package com.muminali13.tanks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.muminali13.tanks.object.Circle;
import com.muminali13.tanks.object.Enemy;
import com.muminali13.tanks.object.Player;
import com.muminali13.tanks.object.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoop gameLoop;

    private final Player player;
    private final Joystick joystick;

    private List<Enemy> enemies = new ArrayList<>();
    private List<Spell> spells = new ArrayList<>();

    private int joystickPointerID = 0;
    private int spellsToCast = 0;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setFocusable(true);
        gameLoop = new GameLoop(this, surfaceHolder);
        joystick = new Joystick(200, 800, 80, 40);

        player = new Player(ContextCompat.getColor(getContext(), R.color.player), joystick, 500, 500, 30);
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
        player.update();

        while (spellsToCast > 0) {
            spellsToCast--;
            spells.add(new Spell(getContext(), player));
        }
        if (Enemy.readyToSpawn()) {
            enemies.add(new Enemy(getContext(), player));
        }

        for (Enemy e : enemies) {
            e.update();
        }

        for (Spell s : spells) {
            s.update();

        }


        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Circle enemy = enemyIterator.next();
            if (Circle.isColliding(enemy, player)) {
                enemyIterator.remove();
                continue;
            }

            Iterator<Spell> spellIterator = spells.iterator();
            while (spellIterator.hasNext()) {
                Spell spell = spellIterator.next();
                if (spell.expired()) {
                    spellIterator.remove();
                }
                else  if (Circle.isColliding(spell, enemy)) {
                    enemyIterator.remove();
                    break;
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        player.draw(canvas);
        joystick.draw(canvas);

        for (Enemy e : enemies) {
            e.draw(canvas);
        }

        for (Spell s : spells) {
            s.draw(canvas);
        }

        drawUPS(canvas);
        drawFPS(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed()) {
                    // Joystick was already pressed before this event -> new touch -> cast spell
                    spellsToCast++;
                } else if (joystick.checkPressed((int) event.getX(), (int) event.getY())) {
                    // Joystick was just pressed -> tell the joystick
                    joystickPointerID = event.getPointerId(event.getActionIndex());
                    joystick.setPressed(true);
                } else {
                    // Joystick was not pressed -> cast spell
                    spellsToCast++;

                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.isPressed()) {
                    joystick.setActuator(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                // Joystick was just released -> tell the joystick
                if (joystickPointerID == event.getPointerId(event.getActionIndex())) {
                    joystick.setPressed(false);
                    joystick.resetActuator();
                    return true;
                }
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
