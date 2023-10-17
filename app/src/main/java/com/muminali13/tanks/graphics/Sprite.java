package com.muminali13.tanks.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private SpriteSheet spriteSheet;
    private Rect rect;
    private Rect pos;
    private int w, h;

    public Sprite(SpriteSheet spriteSheet, Rect rect, int w, int h) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
        this.w = w;
        this.h = h;
        pos = new Rect();
    }

    public void draw(Canvas canvas, int x, int y) {
        pos.left = x- w/2;
        pos.right = pos.left + w;
        pos.top = y - h/2;
        pos.bottom = pos.top + h;
        canvas.drawBitmap(spriteSheet.getBitmap(), rect, pos, null);
    }
}
