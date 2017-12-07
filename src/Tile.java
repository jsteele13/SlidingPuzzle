import javax.swing.JButton;

public class Tile {
	private JButton button;
	private int currentRow = 0;
	private int currentCol = 0;
	

	public Tile(int num) {
		String number = "" + num;
		button = new JButton(number);
	}

}
