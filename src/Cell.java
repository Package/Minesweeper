import java.awt.*;

/**
 * Copyright (C) <2014>
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE
 * SOFTWARE.
 */
public class Cell {

    /* Types of cell */
    public enum Type {
        MINE, EMPTY, FLAGGED, FLAGGED_MINE
    }

    /* Cell states */
    public enum State {
        OPEN, CLOSED
    }

    /* How many bombs are surrounding this cell? */
    protected int surroundingBombs = 0;

    /* (X,Y) coordinate of this cell on the game board */
    protected int x, y;

    /* Has this cell been revealed? */
    protected State state;

    /* The type of cell */
    protected Type type;

    /* The font to render cells with */
    protected static final Font FONT = new Font("serif", Font.PLAIN, 30);

    /* Default colour of a cell*/
    protected static final Color BACKGROUND = new Color(0xd0d0d0);

    /* Colour of a cell when its been opened */
    protected static final Color OPENED = new Color(0xeeeeee);

    /* The different colours a cell value can be */
    private static final Color[] CELL_COLOURS = {
            new Color(0xffffff),
            new Color(0x0000FE), // 1
            new Color(0x186900), // 2
            new Color(0xAE0107), // 3
            new Color(0x000177), // 4
            new Color(0x8D0107), // 5
            new Color(0x007A7C), // 6
            new Color(0x902E90), // 7
            new Color(0x000000), // 8
    };

    /* Construct a new cell */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = Type.EMPTY;
        this.state = State.CLOSED;
    }

    /* Set the cell type*/
    public void setType(Type type) {
        this.type = type;
    }

    /* Get the cell type*/
    public Type getType() {
        return type;
    }

    /* Set the cell state */
    public void setState(State state) {
        this.state = state;
    }

    /* Get the cell state */
    public State getState() {
        return state;
    }

    /* Draw the cell to the game board */
    public void draw(Graphics2D g) {
        final int s = 30; // size of a cell
        g.setColor(BACKGROUND);
        g.fillRect(x * s, y * s, s, s);
        g.setFont(FONT);

        if (state == State.OPEN) {
            g.setColor(OPENED);
            g.fillRect(x * s, y * s, s, s);
            if (type == Type.MINE) {
                g.setColor(Color.BLACK);
                g.fillOval((x * s) + 8, ((y * s) + 9), s / 2, s / 2);
                g.setColor(Color.WHITE);
                g.fillOval((x * s) + 10, ((y * s) + 12), 5, 5);
            } else if (surroundingBombs > 0) {
                g.setColor(CELL_COLOURS[surroundingBombs]);
                g.drawString(String.valueOf(surroundingBombs), (x * s) + 8,
                        ((y * s) + s) - 4);
            }
        }
        if (type == Type.FLAGGED || type == Type.FLAGGED_MINE) {
            g.setColor(Color.RED);
            g.fillRect((x * s) + 8, ((y * s) + 6), 15, 5);
            g.setColor(Color.BLACK);
            g.fillRect((x * s) + 14, ((y * s) + 11), 3, 15);
        }

        g.setColor(Color.GRAY); // outline
        g.drawRect(x * s, y * s, s, s); // outline
    }
}
