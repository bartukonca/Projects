/*
Ali Bartu Konca 2021400177 15/04/2023
The Bar class is used for drawing the bar and the time bar. The time bar's color only changes by the green value
and to find how it changes linearly we take the green value as 225 when t=0 and when t=GAME_DURATION green value
becomes 0.We find the length of the time bar similarly.When t = 0 length is the x-axis and when t = GAME_DURATION
length becomes 0.
 */


public class Bar {

    private static final double TIME_BAR_HEIGHT = 0.5;

    Bar() {
    }

    //Only added greenValue because red value is always 225 and blue value is 0. They do not change.
    private static int newGreenValue(int time) {
        //green value decreases linearly and this is its equation.
        int value = (Environment.TOTAL_GAME_DURATION - time) * 225 / Environment.TOTAL_GAME_DURATION;
        if (value <= 0) {
            /*
            Green value can't be negative,so we set it to zero also
            if it is negative this means that we have run out of time, so we set
            gameLoss and gameContinue to true and false respectively.
            */
            Environment.gameContinue = false;
            Environment.gameLoss = true;
            return 0;
        }
        return value;
    }

    private static double barLength(int time) {
        /*length of the time bar decreases linearly and this is its equation.
        this will return where(coordinate) the line will end (line will go from 0 to barLength).
        */
        double length = (double) (Environment.TOTAL_GAME_DURATION - time) * Environment.X_SIZE
                / Environment.TOTAL_GAME_DURATION;


        if (length <= 0) {
            /*
            Bar length can't be negative,so we set it to zero also
            if it is negative this means that we have run out of time, so we set
            gameLoss and gameContinue to true and false respectively.
            */
            Environment.gameLoss = true;
            Environment.gameContinue = false;
            return 0;
        }
        return length;


    }

    public static void drawBar(int time) {
        //The method for drawing the bar and the time bar.
        /*
        time will be calculated in the Environment class.It represents the time
        passed since the beginning of the game.
        */

        StdDraw.picture(Environment.X_CENTER, Environment.Y_BAR_CENTER, "bar.png",
                Environment.X_SIZE, Environment.Y_BAR_SIZE);

        int greenValue = newGreenValue(time);
        double barLength = barLength(time);

        StdDraw.setPenColor(225, greenValue, 0);
        StdDraw.filledRectangle(barLength / 2, Environment.Y_BAR_CENTER,
                barLength / 2, TIME_BAR_HEIGHT / 2);
    }

}
