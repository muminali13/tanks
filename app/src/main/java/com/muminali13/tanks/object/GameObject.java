package com.muminali13.tanks.object;

import android.graphics.Canvas;

public abstract class GameObject {

    protected double positionX;
    protected double positionY;

    protected double velocityX;
    protected double velocityY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void update();

    public abstract void draw(Canvas canvas);

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public static double distanceBetweenObjects(GameObject o1, GameObject o2) {
        double a = o1.positionX - o2.positionX;
        double b = o1.positionY - o2.positionY;
        return Math.sqrt(a * a + b * b);
    }
}
