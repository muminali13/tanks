package com.muminali13.tanks.graphics;

import android.graphics.Canvas;

import com.muminali13.tanks.GameDisplay;
import com.muminali13.tanks.object.Player;

public class Animator {

    private Sprite[] sprites;

    private int index;
    private int count;
    private int period;

    public Animator(Sprite[] sprites) {
        this.sprites = sprites;

        period = 20;
        count = period;
    }

    public void draw(Canvas canvas, Player player, GameDisplay gameDisplay) {
        int x = (int) gameDisplay.gameToDisplayCoordinateX(player.getPositionX());
        int y = (int) gameDisplay.gameToDisplayCoordinateY(player.getPositionY());

        switch (player.getPlayerState().getState()) {
            case IS_MOVING:
                count--;
                if (count <= 0) {
                    count = period;
                    if (index == 2) index = 1;
                    else index = 2;
                }
                drawFrame(canvas, x, y, sprites[index]);
                break;
            case NOT_MOVING:
                drawFrame(canvas, x, y, sprites[0]);
                break;
            case START_MOVING:
                count = period;
                drawFrame(canvas, x, y, sprites[index]);
                break;

        }
    }

    public void drawFrame(Canvas canvas, int x, int y, Sprite sprite) {
        sprite.draw(canvas, x, y);
    }
}
