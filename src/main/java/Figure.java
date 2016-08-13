/**
 * Class Figure describes the figure of Tetris
 */
public class Figure
{
    //metritsa which determines the shape of the figures : 1 - not an empty cell , 0 - empty
    private int[][] matrix;
    //coordinates
    private int x;
    private int y;

    public Figure(int x, int y, int[][] matrix)
    {
        this.x = x;
        this.y = y;
        this.matrix = matrix;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int[][] getMatrix()
    {
        return matrix;
    }

    /**
     * turn figure .
     * For the sake of simplicity - just around the main diagonal 
     */
    public void rotate()
    {
        int[][] matrix2 = new int[3][3];

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                matrix2[i][j] = matrix[j][i];
            }
        }

        matrix = matrix2;
    }

    /**
     * Move the figure to the left.
     * Check to see if she got out of bounds and / or climbed on whether occupied cells .
     */
    public void left()
    {
        x--;
        if (!isCurrentPositionAvailable())
            x++;
    }

    /**
     * Двигаем фигурку вправо.
     * Проверяем не вылезла ли она за границу поля и/или не залезла ли на занятые клетки.
     */
    public void right()
    {
        x++;
        if (!isCurrentPositionAvailable())
            x--;
    }

    /**
     * Move the figure up.
     * Used if the figure had climbed to the occupied cells .
     */
    public void up()
    {
        y--;
    }

    /**
     * Move figure down.
     */
    public void down()
    {
        y++;
    }

    /**
     * Move figure down until until it climbed up on someone.
     */
    public void downMaximum()
    {
        while (isCurrentPositionAvailable())
        {
            y++;
        }

        y--;
    }

    /**
     * Check - whether the figure can be located at the current position
     */
    public boolean isCurrentPositionAvailable()
    {
        Field field = Tetris.game.getField();

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (matrix[i][j] == 1)
                {
                    if (y + i >= field.getHeight())
                        return false;

                    Integer value = field.getValue(x + j, y + i);
                    if (value == null || value == 1)
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * Landing figure - add all of its non-empty cells to the cells of the field .
     */
    public void landed()
    {
        Field field = Tetris.game.getField();

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (matrix[i][j] == 1)
                    field.setValue(x + j, y + i, 1);
            }
        }
    }
}
