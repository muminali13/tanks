package com.muminali13.tanks.object;


public class PlayerState {

    public enum State {
        NOT_MOVING,
        START_MOVING,
        IS_MOVING
    }

    private Player player;
    private State state;


    public PlayerState(Player player) {
        this.player = player;
        this.state = State.NOT_MOVING;

    }

    public void update() {
        switch (state) {
            case IS_MOVING:
                if (player.velocityX == 0 && player.velocityY == 0) {
                    state = State.NOT_MOVING;
                }
                break;
            case NOT_MOVING:
                if (player.velocityX != 0 || player.velocityY != 0) {
                    state = State.START_MOVING;
                }
                break;
            case START_MOVING:
                if (player.velocityX != 0 || player.velocityY != 0) {
                    state = State.IS_MOVING;
                }
                break;
        }
    }

    public State getState() {
        return state;
    }
}
