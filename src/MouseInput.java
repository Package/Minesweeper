import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
public class MouseInput extends MouseAdapter {

    /* The game board */
    protected Grid grid;

    /* Left mouse button to open a cell */
    private static final int OPEN_CELL_BUTTON = 1;

    /* Right mouse button to mark a mine */
    private static final int MARK_MINE_BUTTON = 3;

    /* Construct a new input handler */
    public MouseInput(Grid grid) {
        this.grid = grid;
    }

    /**
     * Handle mouse press events
     *
     * @param e
     *         - the mouse press event
     */
    public void mousePressed(MouseEvent e) {
        final int s = 30; // cell size
        final int x = e.getX() / s;
        final int y = e.getY() / s;
        final int button = e.getButton();

        // clicked outside the board
        if (x < 0 || y < 0 || x > grid.xCells || y > grid.yCells)
            return;

        try {
            if (button == OPEN_CELL_BUTTON)
                grid.openCell(x, y);

            if (button == MARK_MINE_BUTTON)
                grid.markMine(x, y);

            grid.checkGameOver();
        } catch (Exception ignored) {
        }
    }
}