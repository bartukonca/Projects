/*
Ali Bartu Konca 2021400177 01/05/2023
The Borders class is an extension of the Map class. It is used for creating
a new helpful map the borderMap that helps us identify the squares which there is
no money (-1 valued squares), the squares with money on them and haven't been assigned
to a pool (0 valued squares), and the squares with money and have been assigned to a pool (1 valued squares)
I call it the "border" map since they separate the regions with money.
 */


public class BorderMap extends Map {
    protected static int[][] borderMap = new int[rows][columns];
    /*
    In this borderMap the 0 numbered squares are the unchecked squares,
    the -1 numbered squares are the squares which hold no money
    and the 1 numbered squares are the squares that can hold money.
     */


    /**
     * No-arg constructor
     */
    BorderMap() {
    }

    public static void findBordersAndPools() {
        /*
        This method (except the findPools method) helps us find the squares that can't hold any money.
        We start with the edges of the board. Change their value in the boardMap to -1 since they can't hold money.
        Then we check every -1 valued square's neighbours. If the neighbour's height is greater than or equal to this square's
        height we change that squares value to -1. If not it remains 0.
         */

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i == 0) || (i == rows - 1) || (j == 0) || (j == columns - 1))
                    borderMap[i][j] = -1;
                //Turn the edges to -1.
            }
        }

        //The following methods find the pools and the score and prints the final map.


        Pools.initializeMoneyHeights();
        Pools.findMoneyHeights();
        Pools.findPools();
        Pools.findScore();
        Pools.setPoolMap();
        Pools.printPoolMap();


    }

}
