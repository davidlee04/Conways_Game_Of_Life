import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/*
 *  1. Check out the Wikipedia page on Conway's Game of Life to familiarize yourself
 *     with the concept.
 *     
 *	https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 */

/*
 *  2. Run the ConwaysGOL.jar to see a demo of the final product.
 */

/* 
 *  3. Create the program on your own or fill in the code under the comments to complete the project.
 *
 */

public class ConwaysGameOfLife extends JPanel implements ActionListener{
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final int CELLS_PER_ROW = 350;
	
	private boolean isRunning = false;
	
	private JFrame window;
	private JPanel inputPanel;
	private JButton startStopButton;
	private JButton randomizeButton;
	private JButton clearButton;
	private JLabel speedLabel;
	private JTextField speedField;
	
	private WorldPanel gamePanel;
	
	public static void main(String[] args) {
		new ConwaysGameOfLife().launchGame();
	}
	
	public void launchGame() {
		//build the window and start the simulation
		window = new JFrame();
		inputPanel = new JPanel();
		gamePanel = new WorldPanel(WIDTH, HEIGHT, CELLS_PER_ROW);
		startStopButton = new JButton();
		randomizeButton = new JButton();
		clearButton = new JButton();
		speedLabel = new JLabel();
		speedField = new JTextField();
		
		window.add(this);
		this.setPreferredSize(new Dimension(WIDTH, 30+HEIGHT));
		this.add(inputPanel);
		this.add(gamePanel);
		inputPanel.add(startStopButton);
		inputPanel.add(speedLabel);
		inputPanel.add(speedField);
		inputPanel.add(randomizeButton);
		inputPanel.add(clearButton);
		
		startStopButton.addActionListener(this);
		randomizeButton.addActionListener(this);
		clearButton.addActionListener(this);
		
		gamePanel.setBackground(Color.LIGHT_GRAY);
		inputPanel.setPreferredSize(new Dimension(WIDTH, 30));
		startStopButton.setText("START");
		randomizeButton.setText("RANDOMIZE");
		clearButton.setText("CLEAR");
		speedLabel.setText("DELAY:");
		speedField.setPreferredSize(new Dimension(60, 20));
		speedField.setText("60");
		speedField.setEditable(true);
		
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		isRunning = true;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//if startStopButton is pressed,
		if(e.getSource() == startStopButton) {
			if(isRunning) {
				startStopButton.setText("START");
				isRunning = false;
				gamePanel.stopAnimation();
			} else if(isRunning == false) {
				startStopButton.setText("STOP");
				isRunning = true;
				gamePanel.setAnimationDelay(Integer.parseInt(speedField.getText()));
				gamePanel.startAnimation();				
			}
			
		}
			// toggle isRunning to the opposite of its current state
			// start or stop the animation based on the state of isRunning
		if(e.getSource() == randomizeButton) {
			gamePanel.randomizeCells();
		}
		// if ranomizeButton is pressed
			// call randomizeCells
		if(e.getSource() == clearButton) {
			gamePanel.clearCells();
		}
		// if clearButton is pressed
			//call clearCells
	}
	
}
