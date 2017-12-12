import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;



/**
 * REFERENCE:
 * https://www.sitepoint.com/randomizing-sliding-puzzle-tiles/
 */
@SuppressWarnings("serial")
public class SlidingPuzzle extends JPanel implements ActionListener {
	private JButton[][] board;
	private int size = 3;
	private int emptyRow; // row coordinate of empty tile
	private int emptyCol; // column coordinate of empty tile
	private static final String ORANGE = "#f37021";
	private static final String BLUE = "#003366";
	
	
	public SlidingPuzzle(Container p) {
		board = new JButton[size][size]; 
		int[] oneDBoard = new int[size*size];
		HashMap<Integer, Boolean> buttonsMade = new HashMap<Integer, Boolean>(); //Hash map of buttons already made
		int random;
		//Loop through grid picking random numbers 
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				
				
				//generates random number not already picked
				do { 
					random = (int)(Math.random() * size*size) + 1;
				} while (buttonsMade.containsKey(random));
			
				//makes blank button (non-existent button numbered last)
				if(random == size*size) { 
					board[i][j] = new JButton("");
					board[i][j].setPreferredSize(new Dimension(100,100));
					board[i][j].setFont(new Font("Roboto", Font.PLAIN, 40));
					board[i][j].setEnabled(false);
					board[i][j].setBackground(Color.decode(BLUE));
					board[i][j].setBorderPainted(false);
					emptyRow = i;
					emptyCol = j;
				} else { 		//makes all other buttons (numbered 1 to size*size-1)
					board[i][j] = new JButton("" + random);
					board[i][j].setPreferredSize(new Dimension(100,100));
					board[i][j].setFont(new Font("Roboto", Font.PLAIN, 40));
					board[i][j].setBackground(Color.decode(BLUE));
					board[i][j].setForeground(Color.decode(ORANGE));
					board[i][j].setOpaque(true);
					board[i][j].setBorderPainted(false);
				}
				oneDBoard[i*size + j] = random;
				buttonsMade.put(random, true);
				board[i][j].setActionCommand("" + random);
				board[i][j].addActionListener(this);
			}
		}
		
		//swap two numbers if impossible
		int inversions = countInversions(oneDBoard);
		if((size % 2 == 1 && inversions % 2 == 1) 
				|| (size % 2 == 0 && (inversions + size - emptyRow - 1) % 2 == 1)) {
			if(emptyRow == 0) { //switch last two if one of first two is empty
				swap(board[size - 1][size - 2], board[size - 1][size - 1]);
				board[size - 1][size - 2].setEnabled(true);
				board[size - 1][size - 2].setBackground(Color.decode(BLUE));
				board[size - 1][size - 2].setForeground(Color.decode(ORANGE));
				//board[size - 1][size - 2].setOpaque(true);
			} else {
				swap(board[0][0], board[0][1]); //switch first twos
				board[0][0].setEnabled(true);
				board[0][0].setBackground(Color.decode(BLUE));
				board[0][0].setForeground(Color.decode(ORANGE));
				//board[0][0].setOpaque(true);
			}
		}
	}

	
	
	/**
	 * Return the number of inversions of the puzzle.
	 * @param arr the board's array
	 * @return the number of inversions
	 */
	public int countInversions(int[] arr) {
		int inversion = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] == arr.length) {
					break;
				} else {
					if (arr[i] > arr[j]) {
						inversion++;
					}
				}
			}
		}
		return inversion;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j].getText().equals(e.getActionCommand())) {
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

	
	
	/**
	 * Function to swap to buttons, disabling one and enabling the other
	 * 
	 * @param one
	 * @param empty
	 */
	public void swap(JButton one, JButton empty) {
		String oldText = new String(one.getText());
		String newText = new String(empty.getText());
		
		one.setText(newText);
		one.setActionCommand(newText);
		one.setBackground(null);
		one.setForeground(null);
		
		
		empty.setText(oldText);
		empty.setActionCommand(oldText);
		empty.setBackground(Color.decode(BLUE));
		empty.setForeground(Color.decode(ORANGE));
		empty.setOpaque(true);
		
		
		one.setEnabled(false);
		empty.setEnabled(true);
	}

	
	
	/**
	 * Return: true if buttons in right order, false otherwise
	 * 
	 * @return whether the game has finished
	 */
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

	
	/**
	 * Display message box.
	 * 
	 * @param infoMessage The text box
	 * @param titleBar the title bar
	 */
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
	
	/**Arrange the buttons on the board
	 * 
	 * @param panel
	 */
	public void arrangeButtons(Container panel) {
		panel.removeAll();
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(size, size));
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
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
