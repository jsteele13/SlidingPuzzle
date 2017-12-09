import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
//NEXT STEPS:
// 1) UI update to allow buttons to switch
// 2) know when it wins!
// 3) make pretty/larger
// 4) rework for a picture
public class SlidingPuzzle extends JPanel implements ActionListener {
	private JButton[][] board;
	private int size = 3;
	private int emptyRow; // row coordinate of empty tile
	private int emptyCol; // col coordinate of empty tile
	private boolean isFinished;
	private Container pane;

	public SlidingPuzzle(Container p) {
		board = new JButton[size][size]; 
		HashMap<Integer, Boolean> buttonsMade = new HashMap<Integer, Boolean>(); //Hashmap of buttons already made
		
		//Loop through grid picking random numbers 
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				//generates random number not already picked
				Integer random;
				do { 
					random = (int)(Math.random() * 9) + 1;
				} while (buttonsMade.containsKey(random));
				//makes blank button (non-existepnt button #9)
				if(random == 9) { 
					board[i][j] = new JButton("");
					board[i][j].setEnabled(false);
					emptyRow = i;
					emptyCol = j;
				} 
				//makes all other buttons (numbered 1-8)
				else {
					board[i][j] = new JButton("" + random);
				}
				buttonsMade.put(random, true);
				board[i][j].setActionCommand("move" + random);
				board[i][j].addActionListener(this);
			}
		}
		isFinished = false;
		pane = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand().substring(4));
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j].getText().equals(e.getActionCommand().substring(4))) {
					if(Math.abs(i - emptyCol) == 1 ^ Math.abs(j - emptyRow) == 1) {
						JButton temp = board[i][j];
						board[i][j] = board[emptyRow][emptyCol];
						board[emptyRow][emptyCol] = temp;
						return;	
					}
				}
			}
		}
		//arrangeButtons(pane);
	}

						
//					if (i == 0) {
//						if (board[i + 1][j].getText().equals("")) {
//							JButton temp = board[i + 1][j];
//							board[i + 1][j] = board[i][j];
//							board[i][j] = temp;
//							return;
//						}
//					}
//					if (i == board.length - 1) {
//						if (board[i - 1][j].getText().equals("")) {
//							JButton temp = board[i - 1][j];
//							board[i - 1][j] = board[i][j];
//							board[i][j] = temp;
//							return;
//						}
//					}
//					if (j == 0) {
//						if (board[i][j + 1].getText().equals("")) {
//							JButton temp = board[i][j + 1];
//							board[i][j + 1] = board[i][j];
//							board[i][j] = temp;
//							return;
//						}
//					}
//					if (j == board[0].length - 1) {
//						if (board[i][j - 1].getText().equals("")) {
//							JButton temp = board[i][j - 1];
//							board[i][j - 1] = board[i][j];
//							board[i][j] = temp;
//							return;
//						}
//					}

	public boolean checkFinished() {
		int count = 1;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!board[i][j].getText().equals("" + count)) {
					return false;
				}
				count++;
			}
		}
		return true;
	}

	public void arrangeButtons(Container pane) {
		pane.removeAll();
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(size, size));
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				buttons.add(board[i][j]);
			}
		}
		pane.add(buttons);
		//updateUI();
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
