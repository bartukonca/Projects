/*
Ali Bartu Konca 2021400177 15/04/2023

The environment class is used for playing the game.
It has one method which plays the game.
The method sets the canvas, creates the initial 3 balls, and draws everything and animates them
in a gameContinue while loop.This gameContinue turns false if the time runs out,if all balls are destroyed,
or if one of the balls hit the player. We also have a gameLoss variable which is true if the player destroys
all the balls and false if the time runs out or a ball hits the player.Based on this gameLoss we put a different
text when the game ends.
 */

import java.awt.*;
import java.util.ArrayList;

public class Environment {

    public static final int TOTAL_GAME_DURATION = 40000;
    public static final int PAUSE_DURATION = 5;
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 500;
    public static final double[] SCALE_X = {0.0, 16.0};
    public static final double X_SIZE = SCALE_X[1] - SCALE_X[0];
    public static final double X_CENTER = (SCALE_X[1] + SCALE_X[0]) / 2;
    public static final double[] SCALE_Y = {-1.0, 9.0};
    public static final double Y_SIZE = SCALE_Y[1] - SCALE_Y[0];
    public static final double Y_CENTER = (SCALE_Y[1] + SCALE_Y[0])/2;
    public static final double[] BAR_SCALE_Y = {-1.0, 0.0};
    public static final double Y_BAR_SIZE = BAR_SCALE_Y[1] - BAR_SCALE_Y[0];
    public static final double Y_BAR_CENTER = (BAR_SCALE_Y[1] + BAR_SCALE_Y[0]) / 2;
    public static final double[] GAME_SCALE_Y = {0.0, 9.0};
    public static final double Y_GAME_SIZE = GAME_SCALE_Y[1] - GAME_SCALE_Y[0];
    public static final double Y_GAME_CENTER = (GAME_SCALE_Y[1] + GAME_SCALE_Y[0]) / 2;
    public static final double GRAVITY = 0.0000008 * Y_GAME_SIZE;
    public static final double HEIGHT_MULTIPLIER = 1.75;
    public static final double MIN_POSSIBLE_HEIGHT = Player.getHeightPlayer() * 1.4;
    public static final double MIN_POSSIBLE_RADIUS = Y_GAME_SIZE * 0.0175;
    public static final double RADIUS_MULTIPLIER = 2.;
    public static boolean gameContinue = true;
    public static boolean gameLoss;
    public static boolean canvasSet = false;
    /*
    We have canvasSet for not setting the canvas again if we want to play the game one more time.
    If we don't have it the game screen pops up each time we play.
    */

    public static void play() {
        /*
        This is the main method which plays one game.It sets the canvas,sets the initial balls,
        and in a loop which breaks when the game is over, draws everything in a certain order.
         */
        if (!canvasSet) {
            //If we have not already set the canvas...
            StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
            StdDraw.setXscale(SCALE_X[0], SCALE_X[1]);
            StdDraw.setYscale(SCALE_Y[0], SCALE_Y[1]);
            StdDraw.enableDoubleBuffering();
            canvasSet = true;
        }

        Player.setxCoor();
        Arrow.resetYCoor();
        //We set the initial conditions.

        ArrayList<Ball> balls = new ArrayList<>();
        //The arrayList which will hold all the ball objects.

        long startTime = System.currentTimeMillis();
        //The game's starting time.Will be used for the time bar.

        balls.add(new Ball(SCALE_X[1] / 4, 0.5, 0));

        balls.add(new Ball(SCALE_X[1] / 3, 0.5, 1));
        balls.get(1).setxVelocityBall(-Ball.getxVelocityMagnitude());

        balls.add(new Ball(SCALE_X[1] / 4, 0.5, 2));

        ArrayList<Integer> poppedBallIndices = new ArrayList<>();
        /*
        The arrayList which we hold the popped balls' indices, it will be used for
        drawing the balls.(If a ball's index is here it won't be drawn and won't be checked for collisions.)
         */
        while (gameContinue) {
            //We will first draw the background because everything will be on it.
            StdDraw.clear();
            StdDraw.picture(X_CENTER, Y_CENTER,"background.png", X_SIZE, Y_SIZE);

            Arrow.drawArrow();
            //We draw the arrow before the player and the bar because the arrow should be behind them.
            Player.drawPlayer();

            long time = System.currentTimeMillis();
            int timePass = (int) (time - startTime);
            //The time passed since the start of the game.
            Bar.drawBar(timePass);

            StdDraw.pause(PAUSE_DURATION);

            if (balls.size() == poppedBallIndices.size()) {
                //When all the balls are destroyed...
                /*
                I check this before draw balls because if I check
                it after the last ball will remain on the screen.
                */
                gameContinue = false;
                gameLoss = false;
                Arrow.deactivate();
            }

            for (int i = 0; i < balls.size(); i++) {
                /*
                We check each possible index...
                 */

                if (!poppedBallIndices.contains(i)) {
                    /*
                    And if they are not in this arrayList we draw the corresponding ball.
                    (We draw them and also check their collisions in the same method.)
                     */
                    balls.get(i).drawBall(balls, poppedBallIndices);
                }
            }

            StdDraw.show();
        }

        StdDraw.picture(X_CENTER, SCALE_Y[1] / 2.18, "game_screen.png",
                SCALE_X[1] / 3.18, SCALE_Y[1] / 4);

        Font font = new Font("Helvetica", Font.BOLD, 30);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(font);
        //We draw the game screen.

        if (gameLoss) {
            //If we lost the game the text says "Game Over".
            StdDraw.text(X_CENTER, Y_GAME_CENTER, "Game Over!");
        } else {
            //Else it says "You won"
            StdDraw.text(X_CENTER, Y_GAME_CENTER, "You Won!");
        }

        font = new Font("Helvetica", Font.ITALIC, 15);
        StdDraw.setFont(font);
        StdDraw.text(X_CENTER, SCALE_Y[1] / 2.3, "To Replay Click \"Y\"");
        StdDraw.text(X_CENTER, SCALE_Y[1] / 2.6, "To Quit Click \"N\"");
        StdDraw.show();

        //reset the game
        gameContinue = true;
    }
}
