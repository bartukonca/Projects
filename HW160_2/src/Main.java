/*
Ali Bartu Konca 2021400177 15/04/2023
The Main class is used for calling the environment class' play method which is the game.
And when the game is over we take "Y" or "N" inputs for continuing the game
or terminating the program.
 */


import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {

        boolean playGame = true;

        while (playGame) {

            boolean keyPressed = false;
            /*
            keyPressed checks if we press "Y" or "N" once the game ends.
            We reset it every time the game is played.
             */
            Environment.play();

            while (!keyPressed) {
                //We only take "Y" or "N" as inputs.

                if (StdDraw.isKeyPressed(KeyEvent.VK_Y)) {
                    //If "Y" is pressed we replay the game playGame is already true so no change there.
                    keyPressed = true;
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_N)) {
                    //If "N" is pressed we quit the game so playGame becomes false.
                    keyPressed = true;
                    playGame = false;
                }
            }
        }
        System.exit(0);
    }
}