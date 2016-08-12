package big01;

import java.util.ArrayList;

/**
 * Class Field describes the " field cells " of Tetris game
 */
public class Field
{
    //width and height
    private int width;
    private int height;

    //field matrix : 1 - the cell is occupied , 0 - free
    private int[][] matrix;

    public Field(int width, int height)
    {
        this.width = width;
        this.height = height;
        matrix = new int[height][width];
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int[][] getMatrix()
    {
        return matrix;
    }

    /**
     * The method returns the value that is contained in the matrix with coordinates (x, y)
     * If the coordinates are outside the matrix , the method returns null.
     */
    public Integer getValue(int x, int y)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
            return matrix[y][x];

        return null;
    }

    /**
     *  The method sets the passed value (value) in a cell matrix with coordinates (x, y)
     */
    public void setValue(int x, int y, int value)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
            matrix[y][x] = value;
    }

    /**
     * The method prints to the screen the current contents of the matrix
     */
    public void print()
    {
        //We create an array , which will "draw" the current state of the game
        int[][] canvas = new int[height][width];

        //Copy " field matrix " in the array
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                canvas[i][j] = matrix[i][j];
            }
        }

        //Copy the figure in an array , only the non-empty cells
        int left = Tetris.game.getFigure().getX();
        int top = Tetris.game.getFigure().getY();
        int[][] brickMatrix = Tetris.game.getFigure().getMatrix();

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (top + i >= height || left + j >= width) continue;
                if (brickMatrix[i][j] == 1)
                    canvas[top + i][left + j] = 2;
            }
        }


        //Prints " painted " on the screen , but start with a " frame boundaries " .
        System.out.println("---------------------------------------------------------------------------\n");

        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int index = canvas[i][j];
                if (index == 0)
                    System.out.print(" . ");
                else if (index == 1)
                    System.out.print(" X ");
                else if (index == 2)
                    System.out.print(" X ");
                else
                    System.out.print("???");
            }
            System.out.println();
        }


        System.out.println();
        System.out.println();
    }

    /**
     * Remove the fill line
     */
    public void removeFullLines()
    {
        //Create a list of storage lines
        ArrayList<int[]> lines = new ArrayList<int[]>();

        //Copy all non-empty line in the list.
        for (int i = 0; i <height; i++)
        {
            //count the number of units in a row - just summarize all of its values
            int count = 0;
            for (int j = 0; j < width; j++)
            {
                count += matrix[i][j];
            }

            //If the line amount is not equal to its width - adding to the list
            if (count != width)
                lines.add(0,matrix[i]);
        }

        //Add the missing lines in the top of the list .
        while (lines.size()<height)
        {
            lines.add(0,new int[width]);
        }

        //Transform the list back to the matrix
        matrix = lines.toArray(new int[height][width]);
    }
}
