package com.muminali13.tanks.object;

import com.muminali13.tanks.GameLoop;
import com.muminali13.tanks.Joystick;


public class Player extends Circle {

    private final double SPEED_PIXELS_PER_SECOND = 400;
    private final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private Joystick joystick;

    public Player(int color, Joystick joystick, double positionX, double positionY, double radius) {
        super(color, radius, positionX, positionY);

        this.joystick = joystick;
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

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
}
