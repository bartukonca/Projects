/*
Ali Bartu Konca 2021400177 01/05/2023
The Main class reads the input.txt file. Creates the initial map based on it.
Then takes 10 coordinate inputs and increases the values of those squares.
Prints the map with each input. Finds the pools and prints the map with the pools named.
Finally,prints the score.

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

        // The incoming part is for picking a square and adding 1 to its value. It does this 10 times.
        /*
        for (int i = 1; i < 11; i++) {

            while (true) {
                //This loop goes on until we get a valid input.
                boolean nonValidRow = false;
                //We will use this to check if the given column index is an integer.

                System.out.println("Add stone " + i + " / 10 to coordinate:");

                Scanner input = new Scanner(System.in);
                String coordinate = input.nextLine().strip();
                //We strip the input to also include " a3" or "a3   " as correct inputs.

                int rowStartingIndex = 0;
                //We will set this to the index of the input string where the numbered part begins.

                for (int character = 0; character < coordinate.length(); character++) {

                    char currentCharacter = coordinate.charAt(character);

                    if (Character.isDigit(currentCharacter)) {
                        //When the current character is a digit...
                        rowStartingIndex = character;
                        //set its position to the start of the row index.
                        break;
                        //Break the loop since we found where the row index starts.
                    }
                }

                if (rowStartingIndex > 0) {

                    //It has to be bigger than 0 if not it means that there are no letters before it. So no column index.

                    String columnIndexString = coordinate.substring(0, rowStartingIndex);
                    //Column index is the substring from the start to the point where the digits start.
                    String rowIndexString = coordinate.substring(rowStartingIndex);
                    //Row index is the substring from the first digit to the end of the input.

                    try {
                        int rowIndex = Integer.parseInt(rowIndexString);
                        //We check if the second part of the input is an integer...
                    } catch (NumberFormatException e) {
                        //...if not we set the nonValidRow to true to take another input.
                        nonValidRow = true;
                    }

                    if (!nonValidRow) {
                        // If the row is valid...
                        //I used my own method because there could be issues with Arrays.binarySearch()
                        int columnIndex = arraySearch(Map.getLetterIndices(), columnIndexString);
                        //Find the index of the column in the array of letter indices.
                        int rowIndex = Integer.parseInt(rowIndexString);

                        if ((columnIndex >= 0) && rowIndex < rows) {
                            //If the given square is in the board.
                            Map.addBlock(rowIndex, columnIndex);
                            //Add 1 to the value of the given square.
                            Map.printAfterInputMap();
                            //And print the map.
                            System.out.println("---------------");
                            break;
                            //Break the while loop to take the next input.
                        }
                    }
                }
                //If we fail any if checks above we come to this point. We don't break the while loop, so we take the input again.
                System.out.println("Not a valid step!");
            }
        }
*/

        BorderMap.findBordersAndPools();
        //The main method which finds the pools and their heights and the score.

        //The incoming part is for printing an arbitrarily chosen scoring system for the course.

        /*
        String score = String.format("%.2f", Map.getScore());
        score = score.replace(",", ".");
        //The score is given as "19,51" but we want it to be "19.51".
        System.out.print("Final score: " + score);
         */
        for (Pools pool : Pools.getAllPools()) {

            int[] firstSquareOfPool = new int[2];
            //We get the firstSquare's coordinates to find the pool's name.

            firstSquareOfPool[0] = pool.getPoolCoordinates().get(0)[0];
            firstSquareOfPool[1] = pool.getPoolCoordinates().get(0)[1];

            String nameOfPool = Pools.getPoolMap()[firstSquareOfPool[0]][firstSquareOfPool[1]];

            System.out.println(nameOfPool + ":" + pool.getVolume());


        }


    }

    public static int arraySearch(String[] arr, String letter) {
        //A method to find the index of a given string in an array.
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(letter)) {
                index = i;
                break;
            }
        }
        return index;
    }
}