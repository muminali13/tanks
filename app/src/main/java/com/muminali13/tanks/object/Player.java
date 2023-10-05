package com.muminali13.tanks.object;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.muminali13.tanks.GameDisplay;
import com.muminali13.tanks.GameLoop;
import com.muminali13.tanks.panel.Joystick;
import com.muminali13.tanks.R;
import com.muminali13.tanks.panel.HealthBar;


public class Player extends Circle {

    public static final int MAX_HEALTH = 10;
    private int health;

    private final double SPEED_PIXELS_PER_SECOND = 400;
    private final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private Joystick joystick;
    private HealthBar healthBar;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(ContextCompat.getColor(context, R.color.player), radius, positionX, positionY);

        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
        this.health = MAX_HEALTH;
    }

    public void update() {

        velocityX = joystick.getActuatorX() * MAX_SPEED;
        velocityY = joystick.getActuatorY() * MAX_SPEED;

        positionX += velocityX;
        positionY += velocityY;

        if (velocityX != 0 && velocityY != 0) {
            directionX = joystick.getActuatorX();
            directionY = joystick.getActuatorY();
        }
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        super.draw(canvas, gameDisplay);
        healthBar.draw(canvas, gameDisplay);
    }

    public int getHealth() {
        return health;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setHealth(int health) {
        if (health >= 0)
            this.health = health;
    }
}
