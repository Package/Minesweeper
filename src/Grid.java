import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.LinkedList;

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

	/* Construct a new grid with a default size */
	public Grid() {
		this(9, 9);
	}

	/* Construct a new grid using the provided size */
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
	
	/* Add mines into the game */
	private void setupMines() {
		if (numMines > (xCells*yCells)) {
			JOptionPane.showMessageDialog(null, "You cant have more mines than you have cells.");
			System.exit(-1);
		}
		
		int placedMines = 0;
		while (placedMines < numMines) {
			int x = (int) (Math.random()*xCells);
			int y = (int) (Math.random()*yCells);

			if (cells[x][y].getType() != Cell.Type.MINE) {
				cells[x][y].setType(Cell.Type.MINE);
				placedMines++;
			}
		}
	}

    public void calculateMines() {
        for (int i = 0; i < xCells; i++)
            for (int j = 0; j < yCells; j++)
                cells[i][j].surroundingBombs = surroundingMines(cells[i][j]);
    }

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

	public void openCell(int x, int y) {
		final Cell cell = cells[x][y];
		
		// dont open a flagged cell
		if (cell.getType() == Cell.Type.FLAGGED ||
			cell.getType() == Cell.Type.FLAGGED_MINE)
			return;
			
		// open cell up and check if its a mine
		cell.setState(Cell.State.OPEN);
		if (cell.getType() == Cell.Type.MINE) {
			JOptionPane.showMessageDialog(null, "You opened a mine. Good game.");
			return;
		}
		
		// stop opening when we get to a cell that has mines around it
		if (cell.surroundingBombs > 0) {
			return;
		}		
		
		// recursively open adjacent cells 
		List<Cell> surroundingCells = getSurroundingCells(cell);
		for (Cell c : surroundingCells) {
			if (c.getState() == Cell.State.CLOSED)
				openCell(c.x, c.y);
		}
	}
		
	/* Get all adjacent cells */
	private List<Cell> getSurroundingCells(Cell cell) {
		List<Cell> surrounding = new LinkedList<Cell>();
        final int x = cell.x;
        final int y = cell.y;

		if (cell.x > 0) {
			surrounding.add(cells[x-1][y]);
			if (cell.y > 0)
				surrounding.add(cells[x-1][y-1]);
			if (cell.y < yCells-1)
				surrounding.add(cells[x-1][y+1]);
		}
		if (cell.x < xCells-1) {
			surrounding.add(cells[x+1][y]);
			if (cell.y < yCells-1)
                surrounding.add(cells[x+1][y+1]);
			if (cell.y > 0)
				surrounding.add(cells[x+1][y-1]);
		}
		if (cell.y > 0)
			surrounding.add(cells[x][y-1]);
		if (cell.y < yCells-1)
			surrounding.add(cells[x][y+1]);
			
		return surrounding;
	}
	
	/* Calculate how many mines surround a cell */
	private int surroundingMines(Cell cell) {
		List<Cell> surrounding = getSurroundingCells(cell);
			
		int mineCount = 0;
		for (Cell c : surrounding)
			if (c.getType() == Cell.Type.MINE)
				mineCount++;
		cell.surroundingBombs = mineCount;
		
		return mineCount;
	}
	
	/* Draw the game board to the screen */
	public void draw(Graphics2D g) {
		for (int x = 0; x < xCells; x++)
			for (int y = 0; y < yCells; y++)
				cells[x][y].draw(g);
	}
}
