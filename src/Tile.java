import javax.swing.JButton;

public class Tile {
	private JButton button;
	
	private int currentX = 0;
	private int currentY = 0;
	private int completedX = 0;
	private int completedY = 0;
	

	public Tile(int num) {
		String number = "" + num;
		button = new JButton(number);
	}

}
