/*
Ali Bartu Konca 2021400177 01/05/2023
The Pools class is an extension of the BorderMap class. Pool is an extension because it will access the
same data fields as the BorderMap.Pools is used for finding the pools, their volumes and the score.
Finds the pools by using another map the moneyHeights. It starts by filling the map with money to the maximum
height,then it iterates over the map and spills the excess money.This iteration goes on until no changes have been
made on the map. Then we find the set the final map with the pools named.
 */

import java.util.ArrayList;

public class Pools extends BorderMap {

    private ArrayList<int[]> poolCoordinates = new ArrayList<>();
    //Holds the coordinates of a pool.

    private static ArrayList<Pools> allPools = new ArrayList<>();
    //An arrayList which holds all the pools.

    private int volume;

    private static int poolCounter;
    //Counts the number of pools.

    private static int[][] moneyHeights = new int[rows][columns];
    //Another map which we will use to find the length of money stored on a square.

    private static String[][] poolMap = new String[rows][columns];
    //The poolMap is the same as the current map(The one that is changed with the inputs) except the pools are named with letters.


    /**
     * No-arg constructor
     */

    Pools() {
    }

    public static void initializeMoneyHeights() {
        /*
        A method that creates the money heights map. If a square is valued -1 on the board map we
        set its value to 0,else we find the max height of the board, and assign the value the difference
        between the max height and the height of the square.
         */

        int maxHeightOfBoard = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (map[i][j] > maxHeightOfBoard)
                    maxHeightOfBoard = map[i][j];
                //Find the max height of the board.
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (borderMap[i][j] != -1) {
                    moneyHeights[i][j] = maxHeightOfBoard - map[i][j];
                    if (moneyHeights[i][j] == 0)
                        //We can identify some borders early on since they hold no money.
                        borderMap[i][j] = -1;
                } else
                    moneyHeights[i][j] = 0;
            }
        }

    }

    public static void findMoneyHeights() {
        //The method for spilling the money until it reaches the equilibrium.(changeMade stays false)
        /*
        If at some point of the iteration we make a change we want to keep the value of changeMade true,
        so we need another boolean value changeMadeThisIteration.If it becomes true at some point, it sets
        the value of changeMade to true.
        */
        boolean changeMade = true;
        while (changeMade) {
            changeMade = false;
            //Will stay false if no change is made.
            for (int i = 1; i < rows - 1; i++) {
                for (int j = 1; j < columns - 1; j++) {
                    //We set the for loop this way since the edges have no money.
                    if (borderMap[i][j] != -1) {
                        //They have no money so it can't spill.
                        boolean changeMadeThisIteration = spillToNeighbour(i, j);
                        /*This method spills the excess money to its neighbours and also returns true
                        if it makes any changes on the board.If it does not return true for any square
                        on the map changeMade stays false, and we break the while loop.(We have reached the equilibrium)
                         */
                        if (changeMadeThisIteration) {
                            changeMade = true;
                            //We don't enter here if changeMadeThisIteration becomes false,so it stays true as we want it to.
                            //Prints the map before the inputs are given.
                            for (int k = 0; k < rows; k++) {
                                System.out.printf("%3d", k);
                                for (int l = 0; l < columns; l++) {
                                    if (l != columns - 1 || k == 0)
                                        System.out.printf("%3d", moneyHeights[k][l]);
                                    else
                                        System.out.printf("%3d ", moneyHeights[k][l]);
                                }
                                System.out.println();
                            }
                            System.out.print("    ");

                            for (String indices : letterIndices)
                                System.out.printf("%2s ", indices);
                            System.out.println();
                        }


                    }
                }
            }
        }
    }

    private static boolean spillToNeighbour(int r, int c) {
        /*
        This method spills the money on a square if one of its neighbours total height (money height + block height)
        is smaller than this square. We make the current square's total height the same as the neighbour.
         */
        boolean changeMade = false;
        for (int i = 0; i < 8; i++) {

            int newRow = r + rowNeighbours[i];
            int newColumn = c + columnNeighbours[i];

            int neighbourTotalHeight = map[newRow][newColumn] + moneyHeights[newRow][newColumn];
            int currentTotalHeight = map[r][c] + moneyHeights[r][c];

            if (neighbourTotalHeight < currentTotalHeight) {
                //If a neighbour's total height is smaller...
                moneyHeights[r][c] = neighbourTotalHeight - map[r][c];
                /*
                Change this square's money height to the difference between the
                neighbour's total height and this square's block height.
                */
                changeMade = true;
                //We have updated the map so this becomes true.

                if (moneyHeights[r][c] <= 0) {
                    borderMap[r][c] = -1;
                    //It doesn't hold any money, so we set its value to -1.
                    moneyHeights[r][c] = 0;
                    //Money height can't be negative, so we set it to 0.
                    break;
                    //Now that it holds no money it can't spill, so we stop checking.
                }
            }
        }
        return changeMade;
    }

    public static void findPools() {
    /*
     A method which finds pools. When it finds a 0 valued square it creates a new pool. And checks each
     connected 0 valued squares with the connectedPools method.
     Adds each connected 0-valued squares' coordinates to the pool's coordinates ArrayList.
     And sets the volumes of the pools' it finds.
     */

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < columns - 1; j++) {
                //We set the for loop this way since the edges can't be 0.
                if (borderMap[i][j] == 0) {
                    //If it is an unmarked pool...
                    allPools.add(new Pools());
                    //We create a new pool.
                    Pools currentPool = allPools.get(allPools.size() - 1);
                    //Get the last pool created.
                    currentPool.connectedPools(i, j);
                    //Find the pools connected to this one.
                    currentPool.setVolume();
                    //And set its volume.
                }
            }
        }
        poolCounter = allPools.size();
        //We have this many unconnected pools.


    }

    private void connectedPools(int initialRow, int initialCol) {
        /*
        Checks all the connected squares to a given point.First it adds the current square's value to the coordinates.
        Then, for every coordinate in poolCoordinates we check their neighbours. As we add coordinates to poolCoordinates
        we keep checking those coordinates' neighbours. So we don't need another loop.
         */

        borderMap[initialRow][initialCol] = 1;
        //This point turns to 1 to indicate we have visited this square and that it is a pool.
        int[] coordinate = {initialRow, initialCol};

        poolCoordinates.add(coordinate);
        //Add the current coordinate to the coordinates of the pool.

        for (int i = 0; i < poolCoordinates.size(); i++) {
            //poolCoordinates.size() updates as we add new elements. So this loop goes until we can't find any more neighbours.

            int[] currentCoordinate = poolCoordinates.get(i);

            int currentRow = currentCoordinate[0];
            int currentCol = currentCoordinate[1];

            for (int j = 0; j < 8; j++) {
                //We check the neighbours...
                int newRow = currentRow + rowNeighbours[j];
                int newCol = currentCol + columnNeighbours[j];

                if (newRow > 0 && newRow < rows - 1 && newCol > 0 && newCol < columns - 1 && borderMap[newRow][newCol] == 0) {
                    //We check this way since the edges can't be pools.
                    //If a neighbour is an unmarked pool...
                    int[] neighbourCoordinate = {newRow, newCol};
                    poolCoordinates.add(neighbourCoordinate);
                    //... we add its value to the coordinates of the pool.
                    borderMap[newRow][newCol] = 1;
                    //Now that we have assigned this square we change its value to 1.
                }
            }
        }
    }

    public static void findScore() {
        //Finds the score. For every unconnected pool, we find their volumes, and we add their square roots.
        for (Pools pool : allPools) {
            score += Math.pow(pool.volume, 0.5);
            //Score is a data field in the Map class.
        }
    }

    public static void setPoolMap() {
        //A method that sets the poolMap which is the final map that will be printed.

        String[] poolNames = Map.letterIndices(poolCounter);
        //We will use the same naming technique as the column indices, so we use that method.

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                int[] coordinate = {i, j};
                poolMap[i][j] = "" + map[i][j];
                //Turn the integer values in map to string by concatenating empty string.
                if (borderMap[i][j] == 1) {
                    //If it belongs to a pool
                    for (int k = 0; k < poolCounter; k++) {
                        Pools pool = allPools.get(k);
                        ArrayList<int[]> coordinates = pool.poolCoordinates;
                        if (containsArray(coordinates, coordinate)) {
                            //We name it according to the pool's index.
                            poolMap[i][j] = poolNames[k].replace("i", "I").toUpperCase();
                            break;
                            //We break this loop since we found the pool.
                        }
                    }
                }
            }
        }
    }

    private void setVolume() {
        //Set the volume of the given pool.
        int volume = 0;
        for (int[] coordinate : poolCoordinates) {
            int xCoordinate = coordinate[0];
            int yCoordinate = coordinate[1];
            volume += moneyHeights[xCoordinate][yCoordinate];
        }
        this.volume = volume;
    }

    public static void printPoolMap() {
        //A method to print the pool map.

        for (int i = 0; i < rows; i++) {
            System.out.printf("%3s", i);
            for (int j = 0; j < columns; j++) {
                if (j != columns - 1)
                    System.out.printf("%3s", poolMap[i][j]);
                else
                    System.out.printf("%3s ", poolMap[i][j]);
            }
            System.out.println();
        }
        System.out.print("    ");

        for (String indices : letterIndices)
            System.out.printf("%2s ", indices);
        System.out.println();
    }

    private static boolean containsArray(ArrayList<int[]> coordinates, int[] coordinate) {
        //A method to check if a given ArrayList<int[]> contains a given int[].
        //The .contains method doesn't work as we want it to because it checks the given array's reference not its content.
        for (int[] coordinate2 : coordinates) {
            if (coordinate2[0] == coordinate[0] && coordinate2[1] == coordinate[1])
                /*I know the int[] will hold 2 values, so I compare the given array's first and second elements with
                each array's first and second element in the ArrayList. */
                return true;
        }
        return false;
    }

    public static ArrayList<Pools> getAllPools(){
        return allPools;
    }

    public static String[][] getPoolMap(){
        return poolMap;
    }

    public ArrayList<int[]> getPoolCoordinates(){return poolCoordinates;}

    public int getVolume(){return volume;}
}
