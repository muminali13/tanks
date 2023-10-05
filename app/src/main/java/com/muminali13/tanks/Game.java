package com.muminali13.tanks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.muminali13.tanks.object.Circle;
import com.muminali13.tanks.object.Enemy;
import com.muminali13.tanks.object.Player;
import com.muminali13.tanks.object.Spell;
import com.muminali13.tanks.panel.DebugOverlay;
import com.muminali13.tanks.panel.GameOver;
import com.muminali13.tanks.panel.Joystick;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoop gameLoop;

    private GameDisplay gameDisplay;
    private final Player player;
    private final Joystick joystick;

    private List<Enemy> enemies;
    private List<Spell> spells;

    private GameOver gameOver;
    private DebugOverlay debug;

    private int joystickPointerID = 0;
    private int spellsToCast = 0;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        debug = new DebugOverlay(getContext(), gameLoop);

        gameOver = new GameOver(getContext());
        joystick = new Joystick(200, 800, 80, 40);

        player = new Player(getContext(), joystick, 500, 500, 30);
        enemies = new ArrayList<>();
        spells = new ArrayList<>();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(player, displayMetrics.widthPixels, displayMetrics.heightPixels);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    public void update() {

        if (player.getHealth() <= 0) {
            return;
        }

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
                player.setHealth(player.getHealth() - 1);
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

        gameDisplay.update();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (player.getHealth() <= 0) {
            gameOver.draw(canvas);
            return;
        }
        player.draw(canvas, gameDisplay);

        for (Enemy e : enemies) {
            e.draw(canvas, gameDisplay);
        }

        for (Spell s : spells) {
            s.draw(canvas, gameDisplay);
        }

        joystick.draw(canvas);
        debug.draw(canvas);
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

    public void pause() {
        gameLoop.stopLoop();
    }
}
