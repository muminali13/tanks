package com.muminali13.tanks.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.muminali13.tanks.R;


public class SpriteSheet {

    private Bitmap bitmap;
    private int w, h;

    public SpriteSheet(Context context, int w, int h) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);
        this.w = w;
        this.h = h;
    }

    public Sprite[] getPlayerSpriteArray() {
        return new Sprite[] {
                new Sprite(this, new Rect(0, 0, w, h), w, h),
                new Sprite(this, new Rect(w, 0, w*2, h), w, h),
                new Sprite(this, new Rect(w*2, 0, w*3, h), w, h)
        };
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
