
import java.awt.Color;

public class Cell {
	private int value;
	private boolean visible;
	private Color color;
	
	private final int correct;
	private final boolean preset;

	private boolean[] mark = new boolean[9];

	public Cell(int correct, boolean preset) {
		this.value = correct;
		this.correct = correct;
		this.preset = preset;
		this.visible = preset;
	}

	public int getValue() {return value;}
	public int getCorrect() {return correct;}
	public boolean[] getMark() {return mark;}
	public boolean isVisible() {return visible;}
	public boolean isPreset() {return preset;}
	public Color getColor() {return color;}
	
	public void setMark(int m, boolean b) {mark[m] = b;}
	public void setVisible(boolean v) {this.visible = v;}
	public void setColor(Color color) {this.color = color;}
	
	public void setValue(int number) {
		if (!preset)
			this.value = number;
	}

	public void setMark() {
		for (int i = 0; i < 9; i++) {
			mark[i] = false;
		}
	}

	public void setMark(int m) { 
		if (mark[m])
			mark[m] = false;
		else
			mark[m] = true;
	}
}