
import java.awt.Color;

public class Board {

	private int[][] blankSudoku;
	private int[][] solvedSudoku;
	private static Cell[][] grid = new Cell[9][9];
	
	private int x = 4, y = 4;
	private Color defaultColor = Color.white;
	private Color selectionColor = new Color(250, 165, 125);
	private Color highlightColor = new Color(240, 235, 210);
	private int inputMode = 1; //1 - values 2 - marks

	public Board(int filledFields) {
		
		this.solvedSudoku = SudokuGenerator.generate();
		this.blankSudoku = SudokuGenerator.generateBlank(solvedSudoku, filledFields);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (blankSudoku[i][j] == 0)
					grid[i][j] = new Cell(solvedSudoku[i][j], false);
				else
					grid[i][j] = new Cell(solvedSudoku[i][j], true);
			}
		}
	}

	public void input(int input) {
		if (inputMode == 1) {//values
			grid[y][x].setValue(input);
			grid[y][x].setVisible(true);
			grid[y][x].setMark();
			
			int xBox = x / 3;
			int yBox = y / 3;
			for (int i = 0; i < 3; i++) 
				for (int j = 0; j < 3; j++) 
					grid[i + yBox*3][j + xBox*3].setMark(input - 1, false);
					
			for (int i = 0; i < 9; i++)
				grid[i][x].setMark(input - 1, false);
					
			for (int i = 0; i < 9; i++)
				grid[y][i].setMark(input - 1, false);
		}
		else if (!grid[y][x].isVisible()){
			grid[y][x].setMark(input - 1);
		}
	}
	public void deleteValue() {
		if (inputMode == 1 && !grid[y][x].isPreset())
			grid[y][x].setVisible(false);
	}
	
	public void highlight() {
		this.clearColors();
		int xBox = x / 3;
		int yBox = y / 3; 
		
		for (int i = 0; i < 3; i++) 
			for (int j = 0; j < 3; j++) 
				grid[i + yBox*3][j + xBox*3].setColor(highlightColor);
		
		for (int i = 0; i < 9; i++)
			grid[i][x].setColor(highlightColor);
		
		for (int i = 0; i < 9; i++)
			grid[y][i].setColor(highlightColor);
		this.setColor();
	}
	public void clearColors() {
		for (int i = 0; i < 9; i++) 
			for (int j = 0; j < 9; j++) 
				grid[i][j].setColor(defaultColor);
	}
	public void changeInputMode() {
		inputMode = (inputMode + 1) % 2;
	}
	
	public boolean[] getMark() {return grid[this.y][this.x].getMark();}
	public boolean[] getMark(int x, int y) {return grid[y][x].getMark();}
	public int getValue() {return grid[this.y][this.x].getValue();}
	public int getValue(int x, int y) {return grid[y][x].getValue();}
	public int getCorrect(int x, int y) {return grid[y][x].getCorrect();}
	public Color getColor(int x, int y) {return grid[y][x].getColor();}
	public boolean isVisible(int x, int y) {return grid[y][x].isVisible();}
	public boolean isPreset(int x, int y) {return grid[y][x].isPreset();}
	
	public void moveUp() {if (y > 0) y--;}
	public void moveDown() {if (y < 8) y++;}
	public void moveLeft() {if (x > 0) x--;}
	public void moveRight() {if (x < 8) x++;}
	public void select(int x, int y) {this.x = x; this.y = y;
	System.out.println("" + grid[y][x].getCorrect() + " " + grid[y][x].getValue());
	}
	public void setColor() {grid[this.y][this.x].setColor(selectionColor);}
	public void setColor(Color color, int x, int y) {grid[y][x].setColor(color);}

}
