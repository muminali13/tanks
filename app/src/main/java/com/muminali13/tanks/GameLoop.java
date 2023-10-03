package com.muminali13.tanks;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    public static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1000.0 / MAX_UPS;

    private boolean isRunning = false;

    private Game game;
    private SurfaceHolder surfaceHolder;

    private double averageUPS;
    private double averageFPS;


    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    public void run() {
        super.run();

        Canvas canvas = null;

        int updateCount = 0;
        int frameCount = 0;

        long startTime, elapsedTime, sleepTime;

        startTime = System.currentTimeMillis();
        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas();

                synchronized (canvas) {
                    game.update();
                    updateCount++;

                    game.draw(canvas);
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    frameCount++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime;

            sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            while (sleepTime < 0 && updateCount < MAX_UPS-1) {
                game.update();
                updateCount++;

                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime);
                averageFPS = frameCount / (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

        }
    }

}
