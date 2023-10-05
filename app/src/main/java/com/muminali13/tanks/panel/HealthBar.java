package com.muminali13.tanks.panel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.muminali13.tanks.GameDisplay;
import com.muminali13.tanks.R;
import com.muminali13.tanks.object.Player;

public class HealthBar {

    private int width, height, margin;
    private int maxWidth = 100;

    private Paint borderPaint;
    private Paint healthPaint;

    private Player player;

    public HealthBar(Context context, Player player) {
        this.player = player;
        this.width =  maxWidth;
        this.height = 15;
        this.margin = 3;

        borderPaint = new Paint();
        borderPaint.setColor(ContextCompat.getColor(context, R.color.health_bar_border));

        healthPaint = new Paint();
        healthPaint.setColor(ContextCompat.getColor(context, R.color.health_bar));
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {

        float x = (float) player.getPositionX();
        float y = (float) player.getPositionY();
        float distanceAbove = 35;
        float healthPercent = (float) player.getHealth() / (float) Player.MAX_HEALTH;

        width = (int) (maxWidth * healthPercent);
        float l = x - width/2;
        float r = l + width;
        float b = y - distanceAbove;
        float t = b - height;

        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinateX(l - margin),
                (float) gameDisplay.gameToDisplayCoordinateY(t - margin),
                (float) gameDisplay.gameToDisplayCoordinateX(r + margin),
                (float) gameDisplay.gameToDisplayCoordinateY(b + margin),
                borderPaint
        );
        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinateX(l),
                (float) gameDisplay.gameToDisplayCoordinateY(t),
                (float) gameDisplay.gameToDisplayCoordinateX(r),
                (float) gameDisplay.gameToDisplayCoordinateY(b),
                healthPaint
        );
        canvas.drawText(":" + player.getHealth(), 100, 100, borderPaint);
    }
}
