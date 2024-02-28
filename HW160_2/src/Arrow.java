/*
Ali Bartu Konca 2021400177 15/04/2023
Arrow class is used for drawing the arrow when it is active.
It uses the movement start time to find where the arrow will be.(x = x0 + v * t)
 */

public class Arrow {
    private static boolean active = false;
    //We draw the arrow when it is active.
    private static final int PERIOD_ARROW = 1500;
    private static final double Y_START = Environment.GAME_SCALE_Y[0] - Environment.Y_GAME_SIZE / 2;
    //Y_START is the arrow's mid-point's starting position.
    //Y_GAME_SIZE is the arrow's length.
    private static double yCoor;
    //yCoor is the current y coordinate of the arrow's mid-point.
    private static double xCoor;
    private static final double VELOCITY_ARROW = Environment.Y_GAME_SIZE / PERIOD_ARROW;
    private static final double VELOCITY_CONSTANT = 1.05;
    //VELOCITY_CONSTANT is the constant we multiply the velocity and time with.Found by trial and error.
    private static long startTime;
    //The arrow's movement starting time.

    Arrow() {
    }

    public static void drawArrow() {
        //The method for drawing the arrow.And moving it by using the time passed since its activation.

        if (Arrow.isActive()) {
            StdDraw.picture(xCoor, yCoor, "arrow.png");

            int arrowMovementTime = (int) (System.currentTimeMillis() - startTime);
            //This is the duration the arrow has been active for.

            yCoor = Y_START + VELOCITY_CONSTANT * VELOCITY_ARROW * arrowMovementTime;
            //This gives the current y coordinate of the arrow's mid-point.(X = X0 + V*t)
            if (2 * yCoor > Environment.Y_GAME_SIZE) {
                //When the arrow hits the ceiling.
                Arrow.deactivate();
            }
        }
    }

    public static void deactivate() {
        //Deactivates the arrow, so we won't draw it or check if it hits a ball.
        active = false;
        Arrow.resetYCoor();
        //We reset the y coordinate for the next activation.
    }

    public static void resetYCoor() {
        //Resets the arrow's position.
        yCoor = Y_START;
    }

    public static void setStartTime(long time) {
        //We set the arrow's movement's start time to now.
        // (Will be used when it is not active and player hits the space bar)
        startTime = time;
    }

    public static double getArrowHead() {
        //The arrow's head's Y coordinate.
        return yCoor + Environment.Y_GAME_SIZE / 2;
    }

    public static boolean isActive() {
        return active;
    }

    //Basic setter and getter methods below this point.

    public static void setActive() {
        active = true;
    }

    public static void setxCoor(double x) {
        xCoor = x;
    }

    public static double getxCoor() {
        return xCoor;
    }



}
