package com.muminali13.tanks.object;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.muminali13.tanks.GameDisplay;


public abstract class Circle extends GameObject {

    protected double radius;
    protected Paint paint;

    public Circle(int color, double radius, double positionX, double positionY) {
        super(positionX, positionY);

        this.radius = radius;

        paint = new Paint();
        paint.setColor(color);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawCircle(
                (float) gameDisplay.gameToDisplayCoordinateX(positionX),
                (float) gameDisplay.gameToDisplayCoordinateY(positionY),
                (float) radius,
                paint
        );
    }

    public static boolean isColliding(Circle c1, Circle c2) {
        return (distanceBetweenObjects(c1, c2) < c1.radius + c2.radius);
    }
}
