//import javax.swing.AbstractButton;
//import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
import java.util.HashMap;
//NEXT STEPS:
// 1) UI update to allow buttons to switch 	(CHECK - MN)
// 2) know when it wins! 					(CHECK - MN)
// 3) make pretty/larger 					(CHECK(?) - MN)
// 4) rework for a picture
@SuppressWarnings("serial")
public class SlidingPuzzle extends JPanel implements ActionListener {
	private JButton[][] board;
	private int size = 4;
	private int emptyRow; // row coordinate of empty tile
	private int emptyCol; // col coordinate of empty tile
	private int lastEmptyRow;
	private int lastEmptyCol;
	
	
	public SlidingPuzzle(Container p) {
		board = new JButton[size][size]; 
		HashMap<Integer, Boolean> buttonsMade = new HashMap<Integer, Boolean>(); //Hashmap of buttons already made
	int count;
		//Loop through grid picking random numbers 
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				
				
//				//generates random number not already picked
				do { 
					count = (int)(Math.random() * size*size) + 1;
				} while (buttonsMade.containsKey(count));
				
				//makes blank button (non-existent button #9)
				if(count == size*size) { 
					board[i][j] = new JButton("");
					board[i][j].setPreferredSize(new Dimension(100,100));
					board[i][j].setFont(new Font("Roboto", Font.PLAIN, 40));
					board[i][j].setEnabled(false);
					emptyRow = i;
					emptyCol = j;
				} 
				//makes all other buttons (numbered 1-size*size)
				else {
					board[i][j] = new JButton("" + count);
					board[i][j].setPreferredSize(new Dimension(100,100));
					board[i][j].setFont(new Font("Roboto", Font.PLAIN, 40));
					board[i][j].setBackground(Color.decode("#003366"));
					board[i][j].setForeground(Color.decode("#f37021"));
				}
				buttonsMade.put(count, true);
				board[i][j].setActionCommand("move" + count);
				board[i][j].addActionListener(this);
				//count++;
			}
		}
	}

	
	public void randomize() {
		for(int i = 0; i < 1000; i++) {
			int rand = (int)(Math.random() * 4);
			switch(rand) {
			case 0: 
				if(emptyRow + 1 < board.length) {
					swap(board[emptyRow + 1][emptyCol], board[emptyRow][emptyCol]);
					emptyRow++;
				}
			case 1:
				if(emptyRow - 1 >= 0) {
					swap(board[emptyRow - 1][emptyCol], board[emptyRow][emptyCol]);
					emptyRow--;
				}
			case 2: 
				if(emptyCol + 1 < board[0].length) {
					swap(board[emptyRow][emptyCol + 1], board[emptyRow][emptyCol]);
					emptyCol++;
				}
			case 3: 
				if(emptyCol - 1 >= 0) {
					swap(board[emptyRow][emptyCol - 1], board[emptyRow][emptyCol]);
					emptyCol--;
				}
				
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand().substring(4));
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j].getText().equals(e.getActionCommand().substring(4))) {
					if((Math.abs(i - emptyRow) == 0 && Math.abs(j - emptyCol) == 1)
							^ (Math.abs(i - emptyRow) == 1 && Math.abs(j - emptyCol) == 0)){
						swap(board[i][j], board[emptyRow][emptyCol]);
						emptyRow = i;
						emptyCol = j;
						if (checkFinished()) {
							infoBox("YOU WIN!", "Result");
						}
						return;
					}
				}
			}
		}
	}

	
	public void swap(JButton one, JButton empty) {
		String oldText = new String(one.getText());
		String newText = new String(empty.getText());
		
		one.setText(newText);
		one.setActionCommand("move" + newText);
		one.setBackground(null);
		one.setForeground(null);
		
		
		empty.setText(oldText);
		empty.setActionCommand("move" + oldText);
		empty.setBackground(Color.decode("#003366"));
		empty.setForeground(Color.decode("#f37021"));
		
		
		one.setEnabled(false);
		empty.setEnabled(true);
	}

	
	//Return: true if buttons in right order, false otherwise

	public boolean checkFinished() {
		int count = 1;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!board[i][j].getText().equals("" + count)) {
					return false;
				}
				count++;
				if (count == size*size) {
					break;
				}
			}
		}
		return true;
	}

	
	// Display message box
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
	
	//Arrange the buttons on the board
	public void arrangeButtons(Container panel) {
		panel.removeAll();
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(size, size));
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				buttons.add(board[i][j]);
			}
		}
		panel.add(buttons);
	}

	
	
	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {

		// Create and set up the window.
		JFrame frame = new JFrame("SlidingPuzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(100, 100);
		// Create and set up the content pane.
		SlidingPuzzle puzzle = new SlidingPuzzle(frame.getContentPane());
		puzzle.setOpaque(true); // content panes must be opaque
		frame.setContentPane(puzzle);
		puzzle.arrangeButtons(frame.getContentPane());
		//puzzle.randomize();

		// Display the window.
		frame.pack();
		frame.setVisible(true);

	}
	

	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
