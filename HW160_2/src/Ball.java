/*
Ali Bartu Konca 2021400177 15/04/2023
The ball class is used for drawing the balls,checking player,wall and arrow collisions.
When the ball is hit by an arrow if the ball's level is higher than 0 it splits into two smaller level balls.
When the ball hits the player the game ends and when the ball hits a wall it bounces back elastically.
The ball's X_velocity is constant and ball's y coordinate changes with
(y = y0 + t * initial_y_velocity + (t^2 * gravity)/2) where initial_y_velocity is found by
(initial_y_velocity = sqrt(2 * MAX_HEIGHT*GRAVITY)).
 */

import java.util.ArrayList;

public class Ball {

    private long movementStartTime;
    /*
    This is the ball's movement's start time for a cycle of ballistic motion.
    Will be used to calculate the y coordinate
    based on the time passed since this moment.
     */
    private double initialYCoor;
    /*
    The initial y coordinate which the ball starts its current cycle of ballistic motion.
     */
    private double xCoor;
    private double yCoor;
    private double initialYVelocityBall;
    private int level;
    private static final int PERIOD_BALL = 15000;
    private static final double X_VELOCITY_MAGNITUDE = Environment.X_SIZE / PERIOD_BALL;
    //I later use this to make a ball's x_velocity in the negative direction.
    private double xVelocityBall = X_VELOCITY_MAGNITUDE;
    private static final double BALL_MOVEMENT_TIME = 38;
    //A constant I multiply the X_Velocity with.Found by trial and error.
    private double maxHeight;
    private double radius;

    Ball() {
    }

    Ball(double x, double y, int level) {
        this.setxCoor(x);
        this.setInitialYCoor(y);
        this.setyCoor(y);
        //We set y_coor separately because in the beginning the y_coor is uninitialized.
        this.setLevel(level);
        this.setMovementStartTime();
    }

    private double ballTrajectory(int time) {
        return (initialYCoor + time * initialYVelocityBall - Environment.GRAVITY * Math.pow(time, 2) / 2);
        //Finds the ball's y_coor based on the time passed from the start of the ballistic motion.
    }

    public void arrowHit(ArrayList<Ball> balls, ArrayList<Integer> indices) {
        //To check if this ball is hit by the arrow.
        double r = this.getRadius();
        double x = this.getxCoor();
        double y = this.getyCoor();

        if ((Arrow.isActive()) && (Math.abs(Arrow.getxCoor() - x) < r) && (Arrow.getArrowHead() > y - r)) {
            /*
            Checks if the arrow is active,if the arrow is in the range covered by the left and right
            sides of the ball and if the ball is lower than the arrow's head.
            */
            if (balls.size() - indices.size() != 1) {
                Arrow.deactivate();
                //So the arrow doesn't disappear when we pop the last ball.
            }
            indices.add(balls.indexOf(this));
            //We add the popped ball's index to this arrayList which holds the popped balls' indices.

            if (this.getLevel() != 0) {
                /*
                Two balls will spawn from the same point as the main
                ball. One of them will be in the other direction
                */
                balls.add(new Ball(x, y, this.getLevel() - 1));

                balls.get(balls.size() - 1).setxVelocityBall(-Ball.X_VELOCITY_MAGNITUDE);
                //Set the added ball's velocity in the negative direction.

                balls.add(new Ball(x, y, this.getLevel() - 1));
                /*
                These balls will start ballistic motion from this (x,y) and time.
                 */
            }
        }
    }

    public void wallHit() {
        //Checks if the ball hits a wall(floor is included)
        double r = this.getRadius();
        double x = this.getxCoor();
        double y = this.getyCoor();
        double vx = this.getxVelocityBall();

        if ((x - r <= Environment.SCALE_X[0]) | (x + r >= Environment.SCALE_X[1])) {
            /*
            If the ball's right side hits the right wall or if the balL's
            left side hits the left wall reverse its x_velocity.
             */
            this.setxVelocityBall(-vx);
        }
        if (y - r <= Environment.GAME_SCALE_Y[0]) {
            /*
            If the ball hits the floor we start another cycle for its ballistic motion.
            We set its movement start time to now.We set its initial_y_coor to the floor's edge
            (the ball is in direct contact with the floor, so it doesn't start below the floor or above it)
            We also set its y_coor to this point to draw it here.
            (If we don't it looks like it goes below the floor.)
             */
            this.setMovementStartTime();
            this.setInitialYCoor(Environment.GAME_SCALE_Y[0] + r);
            this.setyCoor(Environment.GAME_SCALE_Y[0] + r);
        }
    }

    private void playerHit() {
        //Checks if the ball hits the player.
        double yBall = this.getyCoor();
        double radius = this.getRadius();
        double xBall = this.getxCoor();
        double xPlayer = Player.getxCoor();
        double xDistance = Math.abs(xPlayer - xBall);
        double yDistance = Math.abs(Player.getHeightPlayer() / 2 - yBall);

        if ((!(xDistance > Player.getWidthPlayer() / 2 + radius) && !(yDistance > Player.getHeightPlayer() / 2 + radius)) &&
                (xDistance <= Player.getWidthPlayer() / 2 | (yDistance <= Player.getHeightPlayer() / 2))) {
            //Checks if the ball collides with the player (Which is a rectangle).

            Environment.gameLoss = true;
            Environment.gameContinue = false;
            /*
            If they collide the game finishes so gameContinue becomes false,
             and we also lose the game so gameLoss becomes true.
             */
            Arrow.deactivate();
        }
    }

    public void drawBall(ArrayList<Ball> balls, ArrayList<Integer> indices) {
        //This method is for drawing the balls and checking for collisions.

        /*
        Indices is the arrayList which we keep the popped balls' indices.
        (When we add balls to the balls arrayList the indices of the popped
        balls won't change because we add the new
        ones to the end of the list.)
         */

        long currentTime = System.currentTimeMillis();
        //We use this time to find the Y_position of the ball.
        double x = this.getxCoor();
        double y = this.getyCoor();

        StdDraw.picture(x, y, "ball.png", 2. * this.getRadius(), 2. * this.getRadius());
        /*
        We multiply by 2 because the length and width of the
        balls are the length of the diameter which is
        2 times the radius.
        */
        this.arrowHit(balls, indices);
        this.playerHit();
        //Checks if the ball hits the player or the arrow.
        this.setxCoor(x + xVelocityBall * BALL_MOVEMENT_TIME);
        this.setyCoor(ballTrajectory((int) (currentTime - movementStartTime)));
        this.wallHit();
        //We check the wall hits later so the ball doesn't clip through the walls.
    }

    public void setLevel(int level) {
        this.level = level;
        this.radius =Environment.MIN_POSSIBLE_RADIUS * Math.pow(Environment.RADIUS_MULTIPLIER, level);
        this.maxHeight =Environment.MIN_POSSIBLE_HEIGHT * Math.pow(Environment.HEIGHT_MULTIPLIER, level);
        this.initialYVelocityBall = Math.sqrt(2 * maxHeight * Environment.GRAVITY);
        /*
        The max height and the radius are found by the level, so we set both of them here,
        and the initial_y_velocity is found from the max height.
         */
    }

    // Basic getter and setter methods below this point.

    public int getLevel() {
        return this.level;
    }

    public void setxVelocityBall(double v) {
        this.xVelocityBall = v;
    }

    public double getxVelocityBall() {
        return this.xVelocityBall;
    }

    public void setxCoor(double x) {
        this.xCoor = x;
    }

    public void setyCoor(double y) {
        this.yCoor = y;
    }

    public void setInitialYCoor(double y) {
        this.initialYCoor = y;
    }

    public double getxCoor() {
        return this.xCoor;
    }

    public double getyCoor() {
        return this.yCoor;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setMovementStartTime() {
        this.movementStartTime = System.currentTimeMillis();
    }

    public static double getxVelocityMagnitude() {
        return X_VELOCITY_MAGNITUDE;
    }
}
