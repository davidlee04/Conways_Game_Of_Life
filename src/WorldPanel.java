import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class WorldPanel extends JPanel implements MouseListener, ActionListener {
	private int cellsPerRow;
	private int cellSize;
	private Cell[][] cells;
	private Timer timer;

	public WorldPanel(int w, int h, int cpr) {
		setPreferredSize(new Dimension(w, h));
		addMouseListener(this);
		timer = new Timer(500, this);
		this.cellsPerRow = cpr;

		// calculate the cellSize
		cellSize = w / cpr;

		// initialize the cells array
		cells = new Cell[cpr][cpr];

		// initialize each cell in the array
		for (int i = 0; i < w; i += cellSize) {
			for (int j = 0; j < h; j += cellSize) {
				cells[i / cellSize][j / cellSize] = new Cell(i, j, cellSize);
			}
		}

	}

	public void randomizeCells() {
		// make each cell alive or dead randomly
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				int aliveOrDead = new Random().nextInt(2);
				if (aliveOrDead == 0) {
					cells[i][j].isAlive = true;
				} else if (aliveOrDead == 1) {
					cells[i][j].isAlive = false;
				}
			}
		}
		repaint();
	}

	public void clearCells() {
		// set isAlive to false for all cells
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j].isAlive = false;
			}
		}
		repaint();
	}

	public void startAnimation() {
		timer.start();
	}

	public void stopAnimation() {
		timer.stop();
	}

	public void setAnimationDelay(int sp) {
		timer.setDelay(sp);
	}

	@Override
	public void paintComponent(Graphics g) {
		// iterate through the cells and draw them
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				cells[i][j].draw(g);
			}
		}
	}

	// advances world one step
	public void step() {
		// initialize the numLivingNbors variable to be the same size as the cells
		int[][] numLivingNbors = new int[cells.length][cells.length];

		// iterate through the cells and populate the numLivingNbors array with their
		// neighbors
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				numLivingNbors[i][j] = getLivingNeighbors(i, j);
			}
		}
		for (int i = 0; i < numLivingNbors.length; i++) {
			for (int j = 0; j < numLivingNbors.length; j++) {
				cells[i][j].liveOrDie(numLivingNbors[i][j]);
			}
		}
		repaint();
	}

	// returns an array list of the 8 or less neighbors of the
	// cell identified by x and y
	public int getLivingNeighbors(int x, int y) {
		int livingNeighbors = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (x != i && y != j) {
					if (x + i >= 0 && y + j >= 0 && x + i <= cellsPerRow - 1 && y + j <= cellsPerRow - 1
							&& cells[x + i][y + j].isAlive == true) {
						livingNeighbors++;
					}
				}
			}
		}

		// add 1 to livingNeighbors for each
		// neighboring cell that is alive

		return livingNeighbors;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// IGNORE
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// IGNORE

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// IGNORE

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// get the location of the mouse
		int mouseX = e.getX() / cellSize;
		int mouseY = e.getY() / cellSize;

		// toggle the cell at that location to either alive or dead
		// based on its current state
		if (cells[mouseX][mouseY].isAlive == true) {
			cells[mouseX][mouseY].isAlive = false;
		} else if (cells[mouseX][mouseY].isAlive == false) {
			cells[mouseX][mouseY].isAlive = true;
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// IGNORE

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		step();
	}

}
