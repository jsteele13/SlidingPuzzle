 import javax.swing.AbstractButton;
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
				count++;
				
			}
		}
		isFinished = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
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
        
        
        JButton one = new JButton ("1");
        JButton two = new JButton ("2");
        JButton three = new JButton ("3");
        JButton four = new JButton ("4");
        JButton five = new JButton ("5");
        JButton six = new JButton ("6");
        
        frame.add(one);
        frame.add(two);
        frame.add(three);
        frame.add(four);
        frame.add(five);
        frame.add(six);
      
        
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
}
