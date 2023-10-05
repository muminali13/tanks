package com.muminali13.tanks;

import com.muminali13.tanks.object.GameObject;

public class GameDisplay {

    private double gameToDisplayOffsetY;
    private double gameToDisplayOffsetX;
    private double displayCenterX;
    private double gameCenterX;
    private double displayCenterY;
    private double gameCenterY;

    private GameObject centerObject;

    public GameDisplay(GameObject centerObject, int width, int height) {

        this.centerObject = centerObject;

        displayCenterX = width/2.0;
        displayCenterY = height/2.0;
    }

    public void update() {
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();

        gameToDisplayOffsetX = displayCenterX - gameCenterX;
        gameToDisplayOffsetY = displayCenterY - gameCenterY;
    }
    public double gameToDisplayCoordinateX(double gameX) {
        return gameX + gameToDisplayOffsetX;
    }
    public double gameToDisplayCoordinateY(double gameY) {
        return gameY + gameToDisplayOffsetY;
    }
}
