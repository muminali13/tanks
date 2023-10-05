package com.muminali13.tanks.object;


import android.content.Context;

import androidx.core.content.ContextCompat;

import com.muminali13.tanks.GameLoop;
import com.muminali13.tanks.R;


public class Enemy extends Circle {

    private static final int SPAWNS_PER_MINUTE = 20;
    private static final int UPDATES_PER_SPAWN = (int) GameLoop.MAX_UPS * 60 / SPAWNS_PER_MINUTE;
    private static int updatesUntilNextSpawn = 0;

    private static final double SPEED_PIXELS_PER_SECOND = 250;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private Player player;

    public Enemy(Context context, Player player, double radius, double positionX, double positionY) {
        super(ContextCompat.getColor(context, R.color.enemy), radius, positionX, positionY);
        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(ContextCompat.getColor(context, R.color.enemy), 30, Math.random() * 1000, Math.random() * 1000);
        this.player = player;
    }

    public static boolean readyToSpawn() {

        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        }
        updatesUntilNextSpawn--;
        return false;
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
