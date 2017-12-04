import javax.swing.JButton;

public class Tile {
	private JButton button;
	
	public Tile(int num) {
		String number = "" + num;
		button = new JButton(number);
	}
}
