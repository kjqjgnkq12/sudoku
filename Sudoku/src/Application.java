
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Application extends JFrame implements MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JTextField[][] cell = new JTextField[9][9];
	private JTextField correctCellsText;
	private JLabel correctCellsLabel;
	private JLabel marksModeLabel;
	private JLabel winLabel;
	private JTextArea controlsTextArea;
	private JButton generateButton;
	private JButton playButton;
	
	private Font marksFont;
	private Font valueFont;
	private Font valuePresetFont;

	private Color colorSelection = Color.RED;
	private Border defaultBorder;
	
	private Board board;
	private boolean readyToPlay = false;
	private boolean events = false;
	private boolean highlight = true;

	Application() throws Exception {

		this.setLayout(null);
		addKeyListener(this);
		
		defaultBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				cell[i][j] = new JTextField("");
				cell[i][j].setHorizontalAlignment(JTextField.CENTER);
				cell[i][j].setBounds(5 + j * 80, 5 + i * 80, 80, 80);
				cell[i][j].setEditable(false);
				cell[i][j].setBorder(defaultBorder);
				add(cell[i][j]);
				cell[i][j].addMouseListener(this);
				cell[i][j].addKeyListener(this);
			}
		initFonts();
		initBorders();
		initComponents();
	}

	public static void main(String[] args) throws Exception {
		Application app = new Application();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
		app.setResizable(true);
		app.setSize(1024, 768);
		app.setTitle("Sudoku");
		app.setResizable(false);
		app.setBackground(Color.GRAY);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object source = e.getSource();
		if (events) { 
			for (int i = 0; i < 9; i++)
				for (int j = 0; j < 9; j++)
					if (source == cell[i][j]) {
						cell[i][j].setBackground(colorSelection);
						clearWindow();
						board.select(j, i);
						if (highlight)
							board.highlight();
						updateWindow();
					}
		}
		else if (!events && source == generateButton) { 
			clearWindow();
			readyToPlay = false;
			int filled = Integer.parseInt(correctCellsText.getText());
			if ( filled >= 20 && filled <= 80 ) {
				correctCellsText.setBackground(Color.WHITE);
				board = new Board(filled);
				if (highlight)
					board.highlight();
				updateWindow();
				readyToPlay = true;
				playButton.setEnabled(true);
				winLabel.setVisible(false);
			}
			else
				correctCellsText.setBackground(Color.RED);
		}
		else if (!events && source == playButton && readyToPlay) {
			events = true;
			readyToPlay = false;
			playButton.setEnabled(false);
			generateButton.setEnabled(false);
			correctCellsText.setEnabled(false);
			controlsTextArea.setVisible(true);
			if (highlight)
				board.highlight();
			updateWindow();
			cell[0][0].requestFocus();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	private void updateWindow() {
		board.setColor();
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				if (board.isVisible(j, i)) {
					cell[i][j].setText("" + board.getValue(j, i));
					if (board.isPreset(j, i))
						cell[i][j].setFont(valuePresetFont);
					else
						cell[i][j].setFont(valueFont);
				}
				else { 
					boolean[] mark = board.getMark(j,i);
					String text = "";
					for (int k = 1; k <= 9; k++) {
						if (mark[k-1])
							text += "" + k;
						else
							text += " ";
					}
					cell[i][j].setFont(marksFont);
					cell[i][j].setText(text);
				}
				cell[i][j].setBackground(board.getColor(j, i));
			}
		
	}

	private void clearWindow() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				cell[i][j].setText("");
				cell[i][j].setBackground(Color.WHITE);
			}
	}
	private boolean checkIfWon() {
		int filled = 0;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				if (board.isVisible(j, i) && board.getValue(j, i) == board.getCorrect(j, i))
					filled++;
			}
		if (filled == 81) {
			events = false;
			winLabel.setVisible(true);
			generateButton.setEnabled(true);
			playButton.setEnabled(true);
			correctCellsText.setEnabled(true);
			controlsTextArea.setVisible(false);
		}
		return false;
	}
	
	private void initBorders() {
		Border boldLeft, boldRight, boldTop, boldBottom, boldCorner;

		boldTop = BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK);
		boldTop = BorderFactory.createCompoundBorder(defaultBorder, boldTop);
		boldLeft = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK);
		boldLeft = BorderFactory.createCompoundBorder(defaultBorder, boldLeft);
		boldBottom = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK);
		boldBottom = BorderFactory.createCompoundBorder(defaultBorder, boldBottom);
		boldRight = BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK);
		boldRight = BorderFactory.createCompoundBorder(defaultBorder, boldRight);

		cell[3][0].setBorder(boldTop);
		cell[3][1].setBorder(boldTop);
		cell[3][2].setBorder(boldTop);
		cell[3][4].setBorder(boldTop);
		cell[3][6].setBorder(boldTop);
		cell[3][7].setBorder(boldTop);
		cell[3][8].setBorder(boldTop);

		cell[0][3].setBorder(boldLeft);
		cell[1][3].setBorder(boldLeft);
		cell[2][3].setBorder(boldLeft);
		cell[4][3].setBorder(boldLeft);
		cell[6][3].setBorder(boldLeft);
		cell[7][3].setBorder(boldLeft);
		cell[8][3].setBorder(boldLeft);

		cell[5][0].setBorder(boldBottom);
		cell[5][1].setBorder(boldBottom);
		cell[5][2].setBorder(boldBottom);
		cell[5][4].setBorder(boldBottom);
		cell[5][6].setBorder(boldBottom);
		cell[5][7].setBorder(boldBottom);
		cell[5][8].setBorder(boldBottom);

		cell[0][5].setBorder(boldRight);
		cell[1][5].setBorder(boldRight);
		cell[2][5].setBorder(boldRight);
		cell[4][5].setBorder(boldRight);
		cell[6][5].setBorder(boldRight);
		cell[7][5].setBorder(boldRight);
		cell[8][5].setBorder(boldRight);

		boldCorner = BorderFactory.createMatteBorder(2, 2, 0, 0, Color.BLACK);
		boldCorner = BorderFactory.createCompoundBorder(defaultBorder, boldCorner);
		cell[3][3].setBorder(boldCorner);

		boldCorner = BorderFactory.createMatteBorder(0, 2, 2, 0, Color.BLACK);
		boldCorner = BorderFactory.createCompoundBorder(defaultBorder, boldCorner);
		cell[5][3].setBorder(boldCorner);

		boldCorner = BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK);
		boldCorner = BorderFactory.createCompoundBorder(defaultBorder, boldCorner);
		cell[5][5].setBorder(boldCorner);

		boldCorner = BorderFactory.createMatteBorder(2, 0, 0, 2, Color.BLACK);
		boldCorner = BorderFactory.createCompoundBorder(defaultBorder, boldCorner);
		cell[3][5].setBorder(boldCorner);
	}
	private void initComponents() {
		correctCellsLabel = new JLabel("Wpisz iloœæ uzupe³nionych komórek (20-80)");
		correctCellsLabel.setBounds(730, 590, 250, 20);
		correctCellsLabel.setHorizontalAlignment(JLabel.CENTER);
		add(correctCellsLabel);
		correctCellsText = new JTextField("");
		correctCellsText.setHorizontalAlignment(JTextField.CENTER);
		correctCellsText.setBounds(730, 615, 250, 20);
		add(correctCellsText);
		generateButton = new JButton("Generuj planszê");
		generateButton.setBounds(730, 640, 250, 40);
		add(generateButton);
		generateButton.addMouseListener(this);
		generateButton.addKeyListener(this);
		playButton = new JButton("Rozpocznij grê");
		playButton.setBounds(730, 685, 250, 40);
		playButton.setEnabled(false);
		add(playButton);
		playButton.addMouseListener(this);
		playButton.addKeyListener(this);
		marksModeLabel = new JLabel("Tryb notatek");
		marksModeLabel.setBounds(715, 10, 300, 50);
		marksModeLabel.setFont(valueFont);
		marksModeLabel.setHorizontalAlignment(JLabel.CENTER);
		marksModeLabel.setVisible(false);
		add(marksModeLabel);
		winLabel = new JLabel("WYGRANA");
		winLabel.setForeground(Color.GREEN);
		winLabel.setHorizontalAlignment(JLabel.CENTER);
		winLabel.setBounds(715, 65, 300, 50);
		winLabel.setFont(valueFont);
		winLabel.setVisible(false);
		add(winLabel);
		controlsTextArea = new JTextArea("Sterowanie: \nzaznaczanie komórek - strza³ki lub LPM \nwpisywanie - klawisze 1-9 \nusuwanie - backspace lub escape \ntryb notatek - spacja");
		controlsTextArea.setBounds(730, 480, 250, 500);
		controlsTextArea.setBackground(null);
		controlsTextArea.setLineWrap(true);
		controlsTextArea.setWrapStyleWord(false);
		controlsTextArea.setVisible(false);
		add(controlsTextArea);
	}
	private void initFonts() {
		marksFont = new Font("Dialog", 0, 12);
		valueFont = new Font("Dialog", 0, 50);
		valuePresetFont = new Font("Dialog", Font.BOLD, 65);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (events) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			board.moveLeft();
			 if (highlight)
				board.highlight();
		}
		else if (key == KeyEvent.VK_RIGHT){
			board.moveRight();
			if (highlight)
				board.highlight();
		}
		else if (key == KeyEvent.VK_UP){
			board.moveUp();
			if (highlight)
				board.highlight();
		}
		else if (key == KeyEvent.VK_DOWN){
			board.moveDown();
			if (highlight)
				board.highlight();
		}
		else if (key == KeyEvent.VK_SPACE) {
			board.changeInputMode();
			if (marksModeLabel.isVisible())
				marksModeLabel.setVisible(false);
			else
				marksModeLabel.setVisible(true);
		}
		else if (key == KeyEvent.VK_1) {board.input(1);checkIfWon();}
		else if (key == KeyEvent.VK_2) {board.input(2);checkIfWon();}
		else if (key == KeyEvent.VK_3) {board.input(3);checkIfWon();}
		else if (key == KeyEvent.VK_4) {board.input(4);checkIfWon();}
		else if (key == KeyEvent.VK_5) {board.input(5);checkIfWon();}
		else if (key == KeyEvent.VK_6) {board.input(6);checkIfWon();}
		else if (key == KeyEvent.VK_7) {board.input(7);checkIfWon();}
		else if (key == KeyEvent.VK_8) {board.input(8);checkIfWon();}
		else if (key == KeyEvent.VK_9) {board.input(9);checkIfWon();}
			
		else if (key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_ESCAPE) {
			board.deleteValue(); 
			clearWindow();
		}
		
		updateWindow();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
