/*
Ali Bartu Konca 2021400177 01/05/2023
The Map class has methods that initializes the map,prints the map,
finds the necessary letters to name the rows and the pools
and increases the value of a given square by one.

 */


public class Map {

    protected static int rows;

    protected static int columns;

    protected static double score;

    protected static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    //Will use ALPHABET to make indices for the columns and to name the pools.

    protected static int[][] map;

    protected static String[] letterIndices;

    protected static int[] rowNeighbours = {-1, -1, -1, 0, 0, 1, 1, 1};
    //Will use these to check a square's neighbours. I used these a number of times, so I decided to just make them constant.
    protected static int[] columnNeighbours = {-1, 0, 1, -1, 1, -1, 0, 1};

    /**
     * No-arg constructor
     */
    Map() {
    }

    //Some getter and setter methods...

    public static double getScore() {
        return score;
    }

    public static void setMap(int r, int c, int v) {
        map[r][c] = v;
    }

    public static String[] getLetterIndices() {
        return letterIndices;
    }

    public static void initializeMap(int r, int c) {
        //Creates the map and sets some data fields.
        rows = r;
        columns = c;
        letterIndices = letterIndices(columns);
        map = new int[r][c];
    }

    public static void printAfterInputMap() {
        //Prints the map after the inputs are given.
        //I use formatting so the numbers or the column indices don't shift if they have 2 letters/numbers.
        for (int i = 0; i < rows; i++) {
            System.out.printf("%3d", i);
            for (int j = 0; j < columns; j++) {
                if (j != columns - 1)
                    System.out.printf("%3d", map[i][j]);
                else
                    System.out.printf("%3d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.print("    ");

        for (String indices : letterIndices)
            System.out.printf("%2s ", indices);
        System.out.println();
    }

    public static void printBeforeInputMap() {
        //Prints the map before the inputs are given.
        for (int i = 0; i < rows; i++) {
            System.out.printf("%3d", i);
            for (int j = 0; j < columns; j++) {
                if (j != columns - 1 || i == 0)
                    System.out.printf("%3d", map[i][j]);
                else
                    System.out.printf("%3d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.print("    ");

        for (String indices : letterIndices)
            System.out.printf("%2s ", indices);
        System.out.println();
    }

    protected static String[] letterIndices(int columns) {
        //Returns an array of letters to help us name the rows and the pools.

        String[] arr = new String[columns];
        for (int k = 0; k < columns; k++) {
            String rowIndex;
            if (k < ALPHABET.length())
                rowIndex = "" + ALPHABET.charAt(k);
                //Turn char to string by concatenating empty string.
            else {
                int firstLetterIndex = (k - ALPHABET.length()) / (ALPHABET.length());
                int secondLetterIndex = (k - ALPHABET.length()) % (ALPHABET.length());
                rowIndex = "" + ALPHABET.charAt(firstLetterIndex) + ALPHABET.charAt(secondLetterIndex);
                //Turn char to string by concatenating empty string.
            }
            arr[k] = rowIndex;
            //Add the resulting word to the array.
        }
        return arr;
    }

    public static void addBlock(int row, int column) {
        //Helps us increase the value of the square given in an input.
        map[row][column]++;
    }
}