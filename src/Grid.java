import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

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
public class Grid {

    /* The cells which make up the game board */
    protected Cell[][] cells;

    /* Size of the game board. By default 9x9 */
    protected int xCells = 9, yCells = 9;

    /* Amount of mines in the grid. By default 10 */
    protected int numMines = 10;

    /**
     * Construct a default grid of 9x9.
     */
    public Grid() {
        this(9, 9);
    }

    /**
     * Construct a new grid of the specified dimension.
     *
     * @param x
     * @param y
     */
    public Grid(int x, int y) {
        this.xCells = x;
        this.yCells = y;
        this.cells = new Cell[xCells][yCells];
        for (int i = 0; i < xCells; i++)
            for (int j = 0; j < yCells; j++)
                cells[i][j] = new Cell(i, j);
        setupMines();
        calculateMines();
    }

    /**
     * Randomly distribute the requested number of mines
     * around the game board.
     */
    private void setupMines() {
        if (numMines > (xCells * yCells)) {
            JOptionPane.showMessageDialog(null, "You cant have more mines than you have cells.");
            System.exit(-1);
        }

        int placedMines = 0;
        while (placedMines < numMines) {
            int x = (int) (Math.random() * xCells);
            int y = (int) (Math.random() * yCells);

            if (cells[x][y].getType() != Cell.Type.MINE) {
                cells[x][y].setType(Cell.Type.MINE);
                placedMines++;
            }
        }
    }

    /**
     * Keep an internal count of how many mines surround
     * each cell.
     */
    public void calculateMines() {
        for (int i = 0; i < xCells; i++)
            for (int j = 0; j < yCells; j++)
                cells[i][j].surroundingBombs = surroundingMines(cells[i][j]);
    }

    /**
     * Mark down a cell that the user thinks is a mine.
     *
     * @param x
     *         - the x coordinate of the cell
     * @param y
     *         - the y coordinate of the cell
     */
    public void markMine(int x, int y) {
        final Cell cell = cells[x][y];
        final Cell.Type type = cell.getType();

        // unflag the cell if its already marked
        if (type == Cell.Type.FLAGGED)
            cell.setType(Cell.Type.EMPTY);
        else if (type == Cell.Type.FLAGGED_MINE)
            cell.setType(Cell.Type.MINE);

            // flag the cell
        else if (type == Cell.Type.MINE)
            cell.setType(Cell.Type.FLAGGED_MINE);
        else
            cell.setType(Cell.Type.FLAGGED);
    }

    /**
     * Open a cell on the game board.
     * <p></p>
     * This recursively the opening of all adjacent cells until
     * the board should not be opened anymore.
     *
     * @param x
     *         - the x coordinate of the cell
     * @param y
     *         - the y coordinate of the cell
     */
    public void openCell(int x, int y) {
        final Cell cell = cells[x][y];

        // Force open cells
        if (cell.getState() == Cell.State.OPEN) {
            forceOpenCells(x, y);
        }

        // Dont open a flagged cell
        if (cell.getType() == Cell.Type.FLAGGED ||
                cell.getType() == Cell.Type.FLAGGED_MINE)
            return;

        // Open cell up and check if its a mine
        cell.setState(Cell.State.OPEN);
        if (cell.getType() == Cell.Type.MINE) {
            JOptionPane.showMessageDialog(null, "You opened a mine. Good game.");
            return;
        }

        // Stop opening when we get to a cell that has mines around it
        if (cell.surroundingBombs > 0) {
            return;
        }

        // Recursively open adjacent cells
        List<Cell> surroundingCells = getSurroundingCells(cell);
        for (Cell c : surroundingCells) {
            if (c.getState() == Cell.State.CLOSED)
                openCell(c.x, c.y);
        }
    }

    /**
     * Quickly open adjacent cells if the correct number
     * of cells have been marked as a mine around the targeted cell.
     *
     * @param x
     *         - x coordinate of the cell
     * @param y
     *         - y coordinate of the cell
     */
    public void forceOpenCells(int x, int y) {
        final Cell cell = cells[x][y];

        List<Cell> surroundingCells = getSurroundingCells(cell);

        int surroundingFlagged = 0;
        for (Cell c : surroundingCells) {
            final Cell.Type type = c.getType();
            if (type == Cell.Type.FLAGGED || type == Cell.Type.FLAGGED_MINE)
                surroundingFlagged++;
        }
        // Return if the correct number hasnt been marked
        if (surroundingFlagged < cell.surroundingBombs) return;

        // Open the adjacent cells
        for (Cell c : surroundingCells) {
            if (c.getState() == Cell.State.CLOSED)
                openCell(c.x, c.y);
        }
    }

    /**
     * Get a list containing all cells directly adjacent to
     * the targeted cell.
     *
     * @param cell
     *         - the targeted cell
     * @return LinkedList containing all directly adjacent cells.
     */
    private List<Cell> getSurroundingCells(Cell cell) {
        List<Cell> surrounding = new LinkedList<Cell>();
        final int x = cell.x;
        final int y = cell.y;

        if (cell.x > 0) {
            surrounding.add(cells[x - 1][y]);
            if (cell.y > 0)
                surrounding.add(cells[x - 1][y - 1]);
            if (cell.y < yCells - 1)
                surrounding.add(cells[x - 1][y + 1]);
        }
        if (cell.x < xCells - 1) {
            surrounding.add(cells[x + 1][y]);
            if (cell.y < yCells - 1)
                surrounding.add(cells[x + 1][y + 1]);
            if (cell.y > 0)
                surrounding.add(cells[x + 1][y - 1]);
        }
        if (cell.y > 0)
            surrounding.add(cells[x][y - 1]);
        if (cell.y < yCells - 1)
            surrounding.add(cells[x][y + 1]);

        return surrounding;
    }

    /**
     * Calculate how many mines are directly adjacent to
     * the specified cell.
     *
     * @param cell
     *         - the cell to check
     * @return the number of mines adjacent to the cell (0..8)
     */
    private int surroundingMines(Cell cell) {
        List<Cell> surrounding = getSurroundingCells(cell);

        int mineCount = 0;
        for (Cell c : surrounding)
            if (c.getType() == Cell.Type.MINE)
                mineCount++;
        cell.surroundingBombs = mineCount;

        return mineCount;
    }

    /**
     * Draw the game cells onto the screen
     *
     * @param g
     *         - graphics object to draw with
     */
    public void draw(Graphics2D g) {
        for (int x = 0; x < xCells; x++)
            for (int y = 0; y < yCells; y++)
                cells[x][y].draw(g);
    }
}
