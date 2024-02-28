/*
Ali Bartu Konca 2021400177 15/04/2023
Player class is used for drawing the player,setting the arrow active if the "Space" key is pressed and moves
the player if the player is in the suitable spot(Between X_AXIS[0] and X_AXIS[1]) when "RIGHT" or "LEFT" keys
are pressed.
 */


import java.awt.event.KeyEvent;

public class Player {

    private static double xCoor;
    private static final int PERIOD_PLAYER = 6000;
    private static final double VELOCITY_PLAYER = Environment.X_SIZE / PERIOD_PLAYER;
    private static final double HEIGHT_PLAYER = Environment.Y_GAME_SIZE / 8;
    private static final double WIDTH_PLAYER = HEIGHT_PLAYER * 27 / 37;
    private static final int PLAYER_MOVEMENT_TIME = 37;
    private static final double X_COOR_CHANGE = VELOCITY_PLAYER * PLAYER_MOVEMENT_TIME;
    //PLAYER_MOVEMENT_TIME is the constant I multiply the velocity with.Found the value by trial and error.

    Player() {

    }

    public static void drawPlayer() {
        //The method for drawing the player, setting the arrow active and moving the player with keyboard inputs.

        if ((!(Arrow.isActive())) && StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
            /*,
            Checks if the arrow is currently not active and if the space key is pressed
            if these are true we set the arrow active, so we can later draw it (in the arrow class)
            and we set the arrow's X coordinate the current X coordinate of the player,
            also we set the start time of the arrow which we will use when drawing it.
             */
            Arrow.setActive();
            Arrow.setStartTime(System.currentTimeMillis());
            Arrow.setxCoor(xCoor);
        }

        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
            /*
             We move the player right if, when the player moves player's right
             side's x coordinate is still smaller than SCALE_X[1].
             */
            if (!(X_COOR_CHANGE + xCoor + WIDTH_PLAYER / 2 > Environment.SCALE_X[1])) {
                xCoor += X_COOR_CHANGE;
            }
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            /*
             We move the player left if, when the player moves player's left
             side's x coordinate is still bigger than SCALE_X[0].
             */
            if (!(xCoor - X_COOR_CHANGE - WIDTH_PLAYER / 2 < Environment.SCALE_X[0])) {
                xCoor -= X_COOR_CHANGE;
            }
        }
        StdDraw.picture(xCoor, HEIGHT_PLAYER / 2, "player_back.png", WIDTH_PLAYER, HEIGHT_PLAYER);
    }

    //Basic getter and setter methods below this point.

    public static void setxCoor() {
        xCoor = Environment.SCALE_X[1] / 2;
    }

    public static double getxCoor() {
        return xCoor;
    }

    public static double getHeightPlayer() {
        return HEIGHT_PLAYER;
    }

    public static double getWidthPlayer() {
        return WIDTH_PLAYER;
    }
}
