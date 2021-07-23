import java.io.*;
import java.util.*;

public class Project6 {
 
	public int numrows;
	public int numCols;
	public int minVal;
	public int maxVal;
	public int newMin = 100;
	public int newMax = 0;
	public int[][] zeroframedAry;
	public int[][] skeltonAry;
	static int two = 2;
	static int one = 1;
	static int EightConn = 9;
	static int fourConn = 4;

	static int size = 1;
	public static int numNb = 5; 

	public static void main(String[] args) throws IOException {

		Project6 p6 = new Project6();

		Scanner infile1 = new Scanner(new FileReader(args[0]));
		BufferedWriter outfile1 = new BufferedWriter(new FileWriter(args[1]));
		BufferedWriter outfile2 = new BufferedWriter(new FileWriter(args[2]));

		int intItem;

		intItem = infile1.nextInt();
		p6.numrows = intItem;

		intItem = infile1.nextInt();
		p6.numCols = intItem;

		intItem = infile1.nextInt();
		p6.minVal = intItem;

		intItem = infile1.nextInt();
		p6.maxVal = intItem;

		p6.zeroframedAry = new int[p6.numrows + two][p6.numCols + two];
		p6.skeltonAry = new int[p6.numrows + two][p6.numCols + two];

		p6.zeroframedAry = p6.setZero(p6.zeroframedAry);
		p6.skeltonAry = p6.setZero(p6.skeltonAry);

		p6.zeroframedAry = p6.loadImg(infile1, p6.zeroframedAry);

		p6.zeroframedAry = p6.compute8Distance(outfile1, p6.zeroframedAry);
		
		String str =  args[0];
		String skeletonfileNameString = str.substring(0, str.lastIndexOf('.'))  + "_skeleton" + ".txt";

		
		
		BufferedWriter outfile3 = new BufferedWriter(new FileWriter(skeletonfileNameString));

		
		p6.skeltonAry = p6.compute_localMaxima(p6.zeroframedAry, p6.skeltonAry);
		
		outfile1.write("Compute local maximma");
		outfile1.write("\n");
		p6.printPretty(p6.skeltonAry, outfile1);
		
		
		outfile3.write(String.valueOf(p6.numrows) + " ");
		outfile3.write(String.valueOf(p6.numCols) + " ");
		outfile3.write(String.valueOf(0) + " ");
		outfile3.write(String.valueOf(p6.newMax) + " ");
		outfile3.write("\n");
		p6.array2file(outfile3, p6.skeltonAry);

		
		outfile1.close();
		outfile3.close();	
		
		
		Scanner infile2 = new Scanner(new FileReader(skeletonfileNameString));
		p6.zeroframedAry = p6.setZero(p6.zeroframedAry);
		
		intItem = infile2.nextInt();
		p6.numrows = intItem;

		intItem = infile2.nextInt();
		p6.numCols = intItem;

		intItem = infile2.nextInt();
		p6.minVal = intItem;

		intItem = infile2.nextInt();
		p6.maxVal = intItem;
		
		
		p6.zeroframedAry = p6.loadImg(infile2, p6.zeroframedAry);

		
		str =  args[0];
		String expansionfileNameString = str.substring(0, str.lastIndexOf('.'))  + "_expansion" + ".txt";
		BufferedWriter outfile4 = new BufferedWriter(new FileWriter(expansionfileNameString));
		
		p6.zeroframedAry = p6.skeletonExpansion(p6.zeroframedAry, outfile2);

		
		outfile4.write(String.valueOf(p6.numrows) + " ");
		outfile4.write(String.valueOf(p6.numCols) + " ");
		outfile4.write(String.valueOf(0) + " ");
		outfile4.write(String.valueOf(1) + " ");
		outfile4.write("\n");
		p6.produceExp(p6.zeroframedAry, outfile4);
		
		
		
		outfile2.close();
		outfile4.close();
		
		
		
		

		
		

	}

	private void produceExp(int[][] zeroframedAry, BufferedWriter outfile4) throws IOException {
		for (int i = 1; i < zeroframedAry.length - 1; i++) {
			for (int j = 1; j < zeroframedAry[0].length - 1; j++) {
				if (this.zeroframedAry[i][j] >= 1) {
					outfile4.write("1 ");
				} else {
					outfile4.write("0 ");
				}

			}

			outfile4.write("\n");
		}
		
	}

	private int[][] skeletonExpansion(int[][] zeroframedAry, BufferedWriter outfile2) throws IOException {
		firstPass_Expension(zeroframedAry);
		outfile2.write("1st-pass of skeleton expansion");
		outfile2.write("\n");
		printPretty(zeroframedAry, outfile2);

		seconddPass_Expension(zeroframedAry);
		outfile2.write("2nd-pass of skeleton expansion");
		outfile2.write("\n");
		printPretty(zeroframedAry, outfile2);

		return this.zeroframedAry;
	}

	private void seconddPass_Expension(int[][] zeroframedAry) {
		for (int i = zeroframedAry.length - 2; i >= 1; i--) {
			for (int j = zeroframedAry[0].length - 2; j >= 1; j--)

			{
				int[] allNeighs = new int[fourConn];
				allNeighs[0] = this.zeroframedAry[i-1][j];
				allNeighs[1] = this.zeroframedAry[i+1][j];
				allNeighs[2] = this.zeroframedAry[i][j-1];
				allNeighs[3] = this.zeroframedAry[i][j+1];
				
				int maxN = Arrays.stream(allNeighs).max().getAsInt();
				if (maxN > this.zeroframedAry[i][j] && maxN > 0) {
					this.zeroframedAry[i][j] = (maxN-1);
					
					int max = maxN-1;
					if (max > this.newMax) {
								this.newMax = max;
							}
							if (max < this.newMin) {
								this.newMin = max;
							}
					
				}
				


					
				}
		}	
				
	}

	private void firstPass_Expension(int[][] zeroframedAry2) {
		for (int i = 1; i < zeroframedAry.length - 1; i++) {
			for (int j = 1; j < zeroframedAry[0].length - 1; j++)

				if (zeroframedAry[i][j] <= 0) {

					int[] allNeighs = new int[fourConn];
					allNeighs[0] = this.zeroframedAry[i-1][j];
					allNeighs[1] = this.zeroframedAry[i+1][j];
					allNeighs[2] = this.zeroframedAry[i][j-1];
					allNeighs[3] = this.zeroframedAry[i][j+1];
					
					int maxN = Arrays.stream(allNeighs).max().getAsInt();
					if (maxN > 0) {
						zeroframedAry[i][j] = maxN-1;
					}



					
				}
		}		
	}

	private void array2file(BufferedWriter outfile3, int[][] skeltonAry2) throws IOException {
		for (int row = 1; row < skeltonAry2.length - one; row++) {
			for (int col = 1; col < skeltonAry2[0].length - one; col++) {
				
				if (skeltonAry2[row][col] > 9) {
				outfile3.write(String.valueOf(skeltonAry2[row][col]) + " ");

			}
				else {
					outfile3.write(String.valueOf(skeltonAry2[row][col]) + " " + " ");

				}
			}
			outfile3.write("\n");

		}		
	}
		

	private int[][] compute_localMaxima(int[][] zeroframedAry, int[][] skeltonAry) {
		
		for (int i = 1; i < zeroframedAry.length-one; i++) {
			for (int j = 1; j < zeroframedAry[0].length-one; j++) {
				if (this.zeroframedAry[i][j] > 0 && is_maxima (this.zeroframedAry, i,j)) {
				
					this.skeltonAry[i][j] = this.zeroframedAry[i][j];
				}
				else {
					this.skeltonAry[i][j] = 0;
					
				}
				
			}
			
		}
		return this.skeltonAry;
	}

	private boolean is_maxima(int[][] zeroframedAry2, int i, int j) {
		int[] allNeighs = new int[fourConn];
		allNeighs[0] = this.zeroframedAry[i-1][j];
		allNeighs[1] = this.zeroframedAry[i+1][j];
		allNeighs[2] = this.zeroframedAry[i][j-1];
		allNeighs[3] = this.zeroframedAry[i][j+1];
		
		for(int k = 0; k < allNeighs.length; k++){
	        if (allNeighs[k] > this.zeroframedAry[i][j] )
	          return false;
	      }
		return true;
	}

	

	public int[][] compute8Distance(BufferedWriter outfile1, int[][] zeroframedAry) throws IOException {

		fistPass_8Distance(this.zeroframedAry);
		outfile1.write("1st-pass of the 8-connectness distance transform");
		outfile1.write("\n");
		printPretty(zeroframedAry, outfile1);
		secondPass_8Distance(this.zeroframedAry);
		outfile1.write("2nd-pass of the 8-connectness distance transform");
		outfile1.write("\n");
		printPretty(zeroframedAry, outfile1);

		
		return this.zeroframedAry;

	}

	private void secondPass_8Distance(int[][] zeroframedAry2) {
		for (int row = zeroframedAry.length - 2; row >= 1; row--) {
			for (int col = zeroframedAry[0].length - 2; col >= 1; col--)

				if (zeroframedAry[row][col] > 0) {

						int Nmin = loadNeighboursGetMin(2, row, col, this.zeroframedAry);
						int min = Math.min(zeroframedAry[row][col], Nmin+1);
						this.zeroframedAry[row][col] = min;
						if (min > this.newMax) {
							this.newMax = min;
						}
						if (min < this.newMin) {
							this.newMin = min;
						}


					
				}
		}	
		
		
	}

	public void fistPass_8Distance(int[][] zeroframedAry) {
		for (int row = 1; row < zeroframedAry.length - 1; row++) {
			for (int col = 1; col < zeroframedAry[0].length - 1; col++)

				if (zeroframedAry[row][col] > 0) {

					
						int min = loadNeighboursGetMin(1, row, col, this.zeroframedAry);
						this.zeroframedAry[row][col] = (min + 1);


					
				}
		}
	}

	public int loadNeighboursGetMin(int pass, int row, int col, int[][] zeroframedAry) {
		
		
		if (pass == 1) {
		int[] allNeighs = new int[EightConn];
		int index = 0;
		for (int x = row - size; x <= row + size; x++) {
			for (int y = col - size; y <= col + size; y++) {
				allNeighs[index] = this.zeroframedAry[x][y];
				index++;
			}
		}

		allNeighs = Arrays.copyOfRange(allNeighs, 0, numNb - one);
		return Arrays.stream(allNeighs).min().getAsInt();

		
		
	}
		

		if (pass == 2) {
		int[] allNeighs = new int[EightConn];
		int index = 0;
		for (int x = row - size; x <= row + size; x++) {
			for (int y = col - size; y <= col + size; y++) {
				allNeighs[index] = this.zeroframedAry[x][y];
				index++;
			}
		} 

		allNeighs = Arrays.copyOfRange(allNeighs, numNb+1, allNeighs.length);
		return Arrays.stream(allNeighs).min().getAsInt();

		
		
	}
		
		return 0;
		
	}

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

	public int[][] setZero(int[][] ary) {
		for (int[] row : ary)
			Arrays.fill(row, 0);
		return ary;

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
