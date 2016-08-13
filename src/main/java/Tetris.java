

import java.awt.event.KeyEvent;

/**
 *  Tetris class - It contains the basic functionality of the game.
 */
public class Tetris
{

    private Field field;                //The field with the cells
    private Figure figure;              //Figure

    private boolean isGameOver;         //game over?

    public Tetris(int width, int height)
    {
        field = new Field(width, height);
        figure = null;
    }

    /**
     * Getter variable field.
     */
    public Field getField()
    {
        return field;
    }

    /**
     * Getter variable figure.
     */
    public Figure getFigure()
    {
        return figure;
    }

    /**
     *  The main program loop.
      * It's all important actions occur
     */
    public void run() throws Exception
    {
        //Create an object "observer of the keyboard" and we start it.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //We put out the initial value of the variable "game over" FALSE
        isGameOver = false;
        //create the first figure from the top in the middle: x - width half, y - 0.
        figure = FigureFactory.createRandomFigure(field.getWidth() / 2, 0);

        //until the game is over
        while (!isGameOver)
        {
            //"Observer" contains events keystrokes?
            if (keyboardObserver.hasKeyEvents())
            {
                //get the first event from the queue
                KeyEvent event = keyboardObserver.getEventFromTop();
                //If the same character 'q' - out of the game.
                if (event.getKeyChar() == 'q') return;
                //If the "left arrow" - move the figure to the left
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    figure.left();
                //If the "right arrow" - move the figure to the right
                else if (event.getKeyCode() ==  KeyEvent.VK_RIGHT)
                    figure.right();
                //If the key code is 12 ("number 5 on the additional keyboard.") - Turn figure
                else if (event.getKeyCode() ==  12)
                    figure.rotate();
                //If the "gap" - figure drops down to max
                else if (event.getKeyCode() ==  KeyEvent.VK_SPACE)
                    figure.downMaximum();
            }

            step();             //move next step
            field.print();      //print condition "field"
            Thread.sleep(300);  //pause 300 ml
        }

        //write message "Game Over"
        System.out.println("Game Over");
    }

    public void step()
    {
        //the figure down
        figure.down();

        // If you put a figure on the current site can not be
        if (!figure.isCurrentPositionAvailable())
        {
            figure.up();                    //raising back
            figure.landed();                //land

            isGameOver = figure.getY() <= 1;//if the figure landed at the top - the game is over

            field.removeFullLines();        //remove the fill line

            figure = FigureFactory.createRandomFigure(field.getWidth() / 2, 0); //create new figure
        }
    }

    /**
     * Setter for figure
     */
    public void setFigure(Figure figure)
    {
        this.figure = figure;
    }

    /**
     * Setter for field
     */
    public void setField(Field field)
    {
        this.field = field;
    }

    public static Tetris game;
    public static void main(String[] args) throws Exception
    {
        game = new Tetris(10, 20);
        game.run();
    }
}
