/*
Ali Bartu Konca 2021400177 01/05/2023
The Main class reads the input.txt file. Creates the initial map based on it.
Then takes 10 coordinate inputs and increases the values of those squares.
Prints the map with each input. Finds the pools and prints the map with the pools named.
Finally,prints the pools names and their volumes.

 */

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Ali_Bartu_Konca {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("input.txt");
        Scanner inputFile = new Scanner(file);
        String[] rowsAndColumns = inputFile.nextLine().trim().split(" ");
        //We will split the first line of the input.txt to two. The first number will be the column number and the second will be the row number.

        int rows = Integer.parseInt(rowsAndColumns[1]);
        int columns = Integer.parseInt(rowsAndColumns[0]);

        Map.initializeMap(rows, columns);
        //Initializes the map using the number of rows and columns. Creates the necessary letter indices for the rows based on the number of it.

        int currentRow = 0;
        //The current row index.

        while (currentRow < rows) {
            String line = inputFile.nextLine();
            String[] lineArr = line.split(" ");

            for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
                //currentColumn is the current column index.

                int value = Integer.parseInt(lineArr[currentColumn]);
                Map.setMap(currentRow, currentColumn, value);
            }
            currentRow++;
        }

        Map.printBeforeInputMap();
        /* Difference between this method and the printAfterInputMap method is that
        in the second one the first row has a whitespace after it.
        In this method we don't have a whitespace there. */
        System.out.println();

        BorderMap.findBordersAndPools();
        //The main method which finds the pools and their heights.

        for (Pools pool : Pools.getAllPools()) {

            int[] firstSquareOfPool = new int[2];
            //We get the firstSquare's coordinates to find the pool's name.

            firstSquareOfPool[0] = pool.getPoolCoordinates().get(0)[0];
            firstSquareOfPool[1] = pool.getPoolCoordinates().get(0)[1];

            String nameOfPool = Pools.getPoolMap()[firstSquareOfPool[0]][firstSquareOfPool[1]];

            System.out.println(nameOfPool + ":" + pool.getVolume());


        }


    }
}
