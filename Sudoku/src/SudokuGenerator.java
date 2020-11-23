
import java.util.Random;

public class SudokuGenerator {
	private static Random random = new Random();
	private static int [][] sudoku = new int[9][9];
	public static int[][] generate()
	{
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) 
				sudoku[i][j] = 0;
    	fillBox(0, 0);
    	fillBox(3, 3);
    	fillBox(6, 6);
    	fillRemaining(0, 3);
    	return sudoku;
	}
    private static void fillBox(int x, int y) 
    { 
        int value; 
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < 3; j++) 
            { 
                do
                    value = random.nextInt(9) + 1; 
                while (isUsedInBox(x, y, value)); 
                sudoku[x+i][y+j] = value; 
            } 
    }  
    private static boolean fillRemaining(int i, int j) 
    { 
    	if (j>=9 && i<8) 
        { 
            i = i + 1; 
            j = 0; 
        } 
        if (i>=9 && j>=9) 
            return true; 
  
        if (i < 3) 
        { 
            if (j < 3) 
                j = 3; 
        } 
        else if (i < 6) 
        { 
            if (j==(int)(i/3)*3) 
                j =  j + 3; 
        } 
        else
        { 
            if (j == 6) 
            { 
                i = i + 1; 
                j = 0; 
                if (i>=9) 
                    return true; 
            } 
        } 
  
        for (int num = 1; num <= 9; num++) 
        { 
            if (CheckIfSafe(i, j, num)) 
            { 
                sudoku[i][j] = num; 
                if (fillRemaining(i, j+1)) 
                    return true; 
  
                sudoku[i][j] = 0; 
            } 
        } 
        return false; 
    } 
    private static boolean isUsedInBox(int x, int y, int num) 
    { 
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < 3; j++) 
                if (sudoku[x+i][y+j] == num) 
                    return true; 
        return false; 
    }
  
    private static boolean CheckIfSafe(int i,int j,int num) 
    {  
        return (!isUsedInRow(i, num) && 
                !isUsedInColumn(j, num) && 
                !isUsedInBox(i-i%3, j-j%3, num)); 
    } 
	  
    private static boolean isUsedInRow(int i,int num) 
    { 
        for (int j = 0; j < 9; j++) 
           if (sudoku[i][j] == num) 
                return true; 
        return false; 
    } 
  
    private static boolean isUsedInColumn(int j,int num) 
    { 
        for (int i = 0; i < 9; i++) 
            if (sudoku[i][j] == num) 
                return true; 
        return false; 
    } 

	public static int [][] generateBlank(int filledTab[][], int filledFields){
		int blankTab[][] = new int[9][9];
		for (int i = 0; i < 9; i++) 
			for (int j = 0; j < 9; j++) 
				blankTab[i][j] = filledTab[i][j];
			
		Random generator = new Random();
		int x,y;
		for (int i = 0; i < 81 - filledFields; i++) {
			do {
				x = generator.nextInt(9);
				y = generator.nextInt(9);
			}
			while (blankTab[x][y] == 0);
			blankTab[x][y] = 0;	
		}
		return blankTab;
	}
}
