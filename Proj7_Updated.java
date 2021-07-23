import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

import javax.security.auth.x500.X500Principal;

public class Proj7 {
	
	public static int two = 2;
	public static int one = 1;
	public int changeflag = 0;
	public int cycleCount = 0;
	public static int numNb = 5;
	public int size = 1;
	public int EightConn = 9;
	public int[][]  firstAry;
	public int[][] secondAry; 
	
	public int numrows; 
	public int numCols;
	public int minVal;
	public int maxVal; 
	
	
	public static void main(String[] args) throws IOException {
		
		
		Proj7 p7 = new Proj7();
		
		Scanner infile1 = new Scanner(new FileReader(args[0]));
		BufferedWriter outfile1 = new BufferedWriter(new FileWriter(args[1]));
		BufferedWriter outfile2 = new BufferedWriter(new FileWriter(args[2]));

		
		int intItem;

		intItem = infile1.nextInt();
		p7.numrows = intItem;

		intItem = infile1.nextInt();
		p7.numCols = intItem;

		intItem = infile1.nextInt();
		p7.minVal = intItem;

		intItem = infile1.nextInt();
		p7.maxVal = intItem;
		
		
		p7.firstAry = new int[p7.numrows + two][p7.numCols + two];
		p7.secondAry = new int[p7.numrows + two][p7.numCols + two];
		
		
		p7.firstAry = p7.setZero(p7.firstAry);
		p7.secondAry = p7.setZero(p7.secondAry);
		

		p7.firstAry  = p7.loadImg(infile1, p7.firstAry);

		outfile2.write("This print is before thinning");
		outfile2.write("\n");
		p7.printPretty(p7.firstAry, outfile2);

		
		do {
			
			p7.changeflag = 0;

			
			p7.NorthThinning (p7.firstAry, p7.secondAry);
			p7.copyArys(p7.firstAry, p7.secondAry);
			
			p7.SouthThinning (p7.firstAry, p7.secondAry);
			p7.copyArys(p7.firstAry, p7.secondAry);

			p7.WestThinning (p7.firstAry, p7.secondAry);
			p7.copyArys(p7.firstAry, p7.secondAry);
			
			p7.EastThinning (p7.firstAry, p7.secondAry);
			p7.copyArys(p7.firstAry, p7.secondAry);
			
			
			p7.cycleCount++;
			
			
			if (p7.cycleCount % 2 == 0) {
				

				outfile2.write("result of thinning : cycle – " +p7.cycleCount);
				outfile2.write("\n");
				p7.printPretty(p7.secondAry, outfile2);


			}

		}
		
		while (p7.changeflag > 0);
		
		
		outfile1.write(String.valueOf(p7.numrows) + " ");
		outfile1.write(String.valueOf(p7.numCols) + " ");
		outfile1.write(String.valueOf(0) + " ");
		outfile1.write(String.valueOf(1) + " ");
		outfile1.write("\n");
		
		for (int row = 1; row < p7.secondAry.length - 1; row++) {
			for (int col = 1; col < p7.secondAry[0].length - 1; col++) {
				outfile1.write(String.valueOf(p7.secondAry[row][col] + " "));
			}
			outfile1.write("\n");
}


	
		
		
		outfile1.close();
		outfile2.close();
	}


	private void copyArys(int[][] firstAry2, int[][] secondAry2) {
		for (int row = 0; row < this.firstAry.length; row++) {
			for (int col = 0; col < this.firstAry[0].length ; col++) {	
				this.firstAry[row][col] = this.secondAry [row][col];
			}
		}
	}


	private  void WestThinning(int[][] firstAry2, int[][] secondAry2) {
		for (int row = 1; row < this.firstAry.length - 1; row++) {
			for (int col = 1; col < this.firstAry[0].length - 1; col++) {
				
				if ( ( this.firstAry[row][col] <= 0 )|| (this.firstAry[row][col-1] > 0 ))
				{
					this.secondAry[row][col] = this.firstAry[row][col];
					
				}
				else if (this.firstAry[row][col] > 0)   {
					boolean x = checkConditions (this.firstAry, row, col, 'w');
					if ( x == true) {
						this.secondAry[row][col] = 0;
						this.changeflag++;

					}
					
					else {

						this.secondAry[row][col] = this.firstAry[row][col];

					}
					
				}
				
			}
			}
		
	}


	private void EastThinning(int[][] firstAry2, int[][] secondAry2) {
		for (int row = 1; row < this.firstAry.length - 1; row++) {
			for (int col = 1; col < this.firstAry[0].length - 1; col++) {
				
				if ( ( this.firstAry[row][col] <= 0 )|| (this.firstAry[row][col+1] > 0 ))
				{
					this.secondAry[row][col] = this.firstAry[row][col];
					
				}
				else if (this.firstAry[row][col] > 0)   {
					boolean x = checkConditions (firstAry2, row, col, 'e');
					if ( x == true) {
						this.secondAry[row][col] = 0;
						this.changeflag++;

					}
					
					else {

						this.secondAry[row][col] = this.firstAry[row][col];

					}
					
				}
				
			}
			}
		
	}


	private void SouthThinning(int[][] firstAry2, int[][] secondAry2) {
		for (int row = 1; row < this.firstAry.length - 1; row++) {
			for (int col = 1; col < this.firstAry[0].length - 1; col++) {
				
				if ( ( this.firstAry[row][col] <= 0 )|| (this.firstAry[row+1][col] > 0 ))
				{
					this.secondAry[row][col] = this.firstAry[row][col];
					
				}
				else if (this.firstAry[row][col] > 0)   {
					boolean x = checkConditions (firstAry2, row, col, 's');
					if ( x == true) {
						this.secondAry[row][col] = 0;
						this.changeflag++;

					}
					
					else {
						this.secondAry[row][col] = this.firstAry[row][col];

					}
					
				}
				
			}
			}
		
	}
	


	private  void NorthThinning(int[][] firstAry2, int[][] secondAry2) {
		
		for (int row = 1; row < this.firstAry.length - 1; row++) {
			for (int col = 1; col < this.firstAry[0].length - 1; col++) {
				
				if ( ( this.firstAry[row][col] <= 0 )|| (this.firstAry[row-1][col] > 0 ))
				{
					this.secondAry[row][col] = this.firstAry[row][col];
					
				}
				else if (this.firstAry[row][col] > 0) {
					boolean x = checkConditions (firstAry2, row, col, 'n');
					if ( x == true) {
						this.secondAry[row][col] = 0;
						this.changeflag++;

					}
					
					else {
						this.secondAry[row][col] = this.firstAry[row][col];

					}
					
				}
				
			}
			}
		
	}


	private boolean checkConditions(int[][] firstAry2, int row, int col, char d) {
		
		boolean flip = true;
		if ( d == 'n') {
			flip = checknorthCond(this.firstAry, row, col);
		}
		else if ( d == 's') {
			 flip = checsouthhCond(this.firstAry, row, col);
		}
		else if ( d == 'w') {
			flip = checkwestCond(this.firstAry, row, col);
		}
		else if ( d == 'e') {
			 flip = checkeasthCond(this.firstAry, row, col);
		}
		
		if (flip == true) {
			this.changeflag++;
			return true;
		}
		
		else {

			return false;
		}
		
	}


	private boolean checkwestCond(int[][] firstAry2, int row, int col) {
		boolean cond = true;
		int[] allNeighs = new int[EightConn];
		int index = 0;
		for (int x = row - size; x <= row + size; x++) {
			for (int y = col - size; y <= col + size; y++) {
				allNeighs[index] = this.firstAry[x][y];
				index++;
			}
		}
		
		int num = 0;
		for (int i = 0; i < allNeighs.length; i++) {
				int x = allNeighs[i];
					if (x > 0) {
        		num++;
						} 
    }


		if ( (num >= 5)) {
			cond = true;
		}
		else {
			cond = false;
		}

		
		
		if (allNeighs[6] == 1 && allNeighs[7] == 0) {
			cond = false;
		}
		if (allNeighs[0] == 1 && allNeighs[1] == 0) {
			cond = false;
		}
		if (allNeighs[5] == 0) {
			cond = false;
		}
		
		

		

		
		
		return cond;
	}


	private boolean checkeasthCond(int[][] firstAry2, int row, int col) {
		boolean cond = true;
		int[] allNeighs = new int[EightConn];
		int index = 0;
		for (int x = row - size; x <= row + size; x++) {
			for (int y = col - size; y <= col + size; y++) {
				allNeighs[index] = this.firstAry[x][y];
				index++;
			}
		}
		
		int num = 0;
		for (int i = 0; i < allNeighs.length; i++) {
				int x = allNeighs[i];
					if (x > 0) {
        		num++;
						} 
    }


		if ( (num >= 5)) {
			cond = true;
		}
		else {
			cond = false;
		}

		
		if (allNeighs[7] == 0 && allNeighs[8] == 1) {
			cond = false;
		}
		if (allNeighs[1] == 0 && allNeighs[2] == 1) {
			cond = false;
		}
		if (allNeighs[3] == 0) {
			cond = false;
		}

		
		
		return cond;
		
	}


	private boolean checsouthhCond(int[][] firstAry2, int row, int col) {
		boolean cond = true;
		int[] allNeighs = new int[EightConn];
		int index = 0;
		for (int x = row - size; x <= row + size; x++) {
			for (int y = col - size; y <= col + size; y++) {
				allNeighs[index] = this.firstAry[x][y];
				index++;
			}
		}
		
		int num = 0;
		for (int i = 0; i < allNeighs.length; i++) {
				int x = allNeighs[i];
					if (x > 0) {
        		num++;
						} 
    }


		if ( (num >= 5)) {
			cond = true;
		}
		else {
			cond = false;
		}

		
		if (allNeighs[3] == 0 && allNeighs[6] == 1) {
			cond = false;
		}
		if (allNeighs[5] == 0 && allNeighs[8] == 1) {
			cond = false;
		}
		if (allNeighs[1] == 0) {
			cond = false;
		}
		
		


		return cond;
	}


	private boolean checknorthCond(int[][] firstAry2, int row, int col) {
	
		boolean cond = true;
		int[] allNeighs = new int[EightConn];
		int index = 0;
		for (int x = row - size; x <= row + size; x++) {
			for (int y = col - size; y <= col + size; y++) {
				allNeighs[index] = this.firstAry[x][y];
				index++;
			}
		}
		
		int num = 0;
		for (int i = 0; i < allNeighs.length; i++) {
				int x = allNeighs[i];
					if (x > 0) {
        		num++;
						} 
    }


		if ( (num >= 5)) {
			cond = true;
		}
		else {
			cond = false;
		}

		
		if (allNeighs[0] == 1 && allNeighs[3] == 0) {
			cond = false;
		}
		if (allNeighs[2] == 1 && allNeighs[5] == 0) {
			cond = false;
		}
		if (allNeighs[7] == 0) {
			cond = false;
		}
		
		
		return cond;
	}


	


	private int[][] setZero(int[][] ary) {
		for (int[] row : ary)
			Arrays.fill(row, 0);
		return ary;	}
	
	
	
	public int[][] loadImg(Scanner infile1, int[][] Ary) {
		int intItem;
		for (int row = 1; row < Ary.length - one; row++) {
			for (int col = 1; col < Ary[0].length - one; col++) {
				intItem = infile1.nextInt();
				Ary[row][col] = intItem;

			}
		}

		return Ary;
	}

	private void printPretty(int[][] zeroframedAry, BufferedWriter bw) throws IOException {
		for (int row = 1; row < zeroframedAry.length - 1; row++) {
			for (int col = 1; col < zeroframedAry[0].length - 1; col++) {
				int x = zeroframedAry[row][col];
				if (x > 9) {
					bw.write(String.valueOf(x)  + " ");
				} else if (x <=9 && x > 0) {
					bw.write(String.valueOf(x) + "  ");
					
				} 
				else {
					bw.write(" " + " " + " ");

				}

			}
			bw.write("\n");
		}

	}
}
