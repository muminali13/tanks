package com.muminali13.tanks.object;


import android.content.Context;

import androidx.core.content.ContextCompat;

import com.muminali13.tanks.GameLoop;
import com.muminali13.tanks.R;


public class Enemy extends Circle {

    private final double SPEED_PIXELS_PER_SECOND = 100;
    private final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private Player player;

    public Enemy(Context context, Player player, double radius, double positionX, double positionY) {
        super(ContextCompat.getColor(context, R.color.enemy), radius, positionX, positionY);
        this.player = player;
    }

    @Override
    public void update() {

        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        double dist = Math.sqrt(distanceToPlayerX * distanceToPlayerX + distanceToPlayerY * distanceToPlayerY);

        double dirX = distanceToPlayerX/dist;
        double dirY = distanceToPlayerY/dist;

        velocityX = dirX * MAX_SPEED;
        velocityY = dirY * MAX_SPEED;

        positionX += velocityX;
        positionY += velocityY;
    }
}
