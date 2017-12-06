 import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class SlidingPuzzle extends JPanel implements ActionListener{
	private JButton[][] board;
	private int size = 3;
	
	private boolean isFinished;
	
	public SlidingPuzzle() {
		board = new JButton[size][size];
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			for(int j = 0; j  <board[0].length; j++) {
				board[i][j] = new JButton("" + count);
				board[i][j].setActionCommand("move" + count);
				board[i][j].addActionListener(this);
				count++;
			}
		}
		isFinished = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < board.length; i++) {
			for(int j = 0; j  <board[0].length; j++) {
				if (board[i][j].getText().equals(e.getActionCommand().substring(4))) {
					if (i == 0) {
						if (board[i + 1][j].getText().equals("")) {
							JButton temp = board[i + 1][j];
							board[i + 1][j] = board[i][j];
							board[i][j] = temp;
							return;
						}
					}
					if (i == board.length - 1) {
						if (board[i - 1][j].getText().equals("")) {
							JButton temp = board[i - 1][j];
							board[i - 1][j] = board[i][j];
							board[i][j] = temp;
							return;
						}
					}
					if (j == 0) {
						if (board[i][j + 1].getText().equals("")) {
							JButton temp = board[i][j + 1];
							board[i][j + 1] = board[i][j];
							board[i][j] = temp;
							return;
						}
					}
					if (j == board[0].length - 1) {
						if (board[i][j - 1].getText().equals("")) {
							JButton temp = board[i][j - 1];
							board[i][j - 1] = board[i][j];
							board[i][j] = temp;
							return;
						}
					}
				}
			}
		}
	}
	
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
    /**
     * Create the GUI and show it.  For thread safety, 
     * this method should be invoked from the 
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
 
        //Create and set up the window.
        JFrame frame = new JFrame("SlidingPuzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100, 100);
        //Create and set up the content pane.
        SlidingPuzzle newContentPane = new SlidingPuzzle();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
//        //TEST
//        JPanel panel = new JPanel();
//        GroupLayout layout = new GroupLayout(panel);
//        
//        layout.setHorizontalGroup(
//        		layout.createSequentialGroup(
//        				.addComponent().board[0][0])
//        				.addcomponent(board[0][1])
//        						)));
        
        
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
}
