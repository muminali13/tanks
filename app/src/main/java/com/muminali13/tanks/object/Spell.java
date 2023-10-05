package com.muminali13.tanks.object;


import android.content.Context;

import androidx.core.content.ContextCompat;

import com.muminali13.tanks.GameLoop;
import com.muminali13.tanks.R;

public class Spell extends Circle {

    private final double SPEED_PIXELS_PER_SECOND = 1000;
    private final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private final Player spellcaster;

    private int count = (int) GameLoop.MAX_UPS * 2;

    public Spell(Context context, Player player) {
        super(ContextCompat.getColor(context, R.color.spell), 20, player.positionX, player.positionY);
        this.spellcaster = player;

        velocityX = spellcaster.getDirectionX()* MAX_SPEED;
        velocityY = spellcaster.getDirectionY()* MAX_SPEED;
    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;

        count--;
    }

    public boolean expired() {
        return count <= 0;
    }
}
