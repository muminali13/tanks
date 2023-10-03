package com.muminali13.tanks;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private int outerCircleCX;
    private int outerCircleCY;
    private int innerCircleCX;
    private int innerCircleCY;

    private int innerCircleRadius;
    private int outerCircleRadius;

    private double actuatorX;
    private double actuatorY;

    private Paint outerCirclePaint;
    private Paint innerCirclePaint;

    private boolean pressed = false;

    public Joystick(int cx, int cy, int outerCircleRadius, int innerCircleRadius) {

        outerCircleCX = cx;
        innerCircleCX = cx;

        outerCircleCY = cy;
        innerCircleCY = cy;

        this.innerCircleRadius = innerCircleRadius;
        this.outerCircleRadius = outerCircleRadius;

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.DKGRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCX = (int) (outerCircleCX + actuatorX*outerCircleRadius);
        innerCircleCY = (int) (outerCircleCY + actuatorY*outerCircleRadius);

    }

    public void draw(Canvas canvas) {

        canvas.drawCircle(outerCircleCX, outerCircleCY, outerCircleRadius, outerCirclePaint);
        canvas.drawCircle(innerCircleCX, innerCircleCY, innerCircleRadius, innerCirclePaint);

    }

    public boolean checkPressed(int touchX, int touchY) {
        pressed = (touchX - outerCircleCX)*(touchX - outerCircleCX) + (touchY - outerCircleCY)*(touchY - outerCircleCY) < outerCircleRadius*outerCircleRadius;
        return pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void setActuator(double touchX, double touchY) {

        double deltaX = touchX - outerCircleCX;
        double deltaY = touchY - outerCircleCY;
        double sqDist = deltaX*deltaX + deltaY*deltaY;

        if (sqDist <= outerCircleRadius*outerCircleRadius) {
            actuatorX = deltaX / outerCircleRadius;
            actuatorY = deltaY / outerCircleRadius;
        } else {
            double dist = Math.sqrt(sqDist);
            actuatorX = deltaX / dist;
            actuatorY = deltaY / dist;
        }
    }

    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }
}
