
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Prob3_1_CV {

	public static int two = 2;
	public static int one = 1;

	public int numrowsImg;
	public int numColsImg;
	public int minValImg;

	public int maxValImg;
	public int numRowsProp;
	public int numColsProp;
	public int minValProp;
	public int maxValProp;

	public int[][] labelledAry;

	public static void main(String[] args) throws IOException {
		Prob3_1_CV p3 = new Prob3_1_CV();

		int numCC;
		int label;
		int numpixels;
		int minrow = 100;
		int mincol = 100;
		int maxrow = 0;
		int maxcol = 0;
		int thrSize = 0;

		Scanner infile1 = new Scanner(new FileReader(args[0]));
		Scanner infile2 = new Scanner(new FileReader(args[1]));
		BufferedWriter outfile1 = new BufferedWriter(new FileWriter(args[3]));
		BufferedWriter outfile2 = new BufferedWriter(new FileWriter(args[4]));

		int intItem;
		intItem = infile1.nextInt();
		p3.numrowsImg = intItem;

		intItem = infile1.nextInt();
		p3.numColsImg = intItem;

		intItem = infile1.nextInt();
		p3.minValImg = intItem;

		intItem = infile1.nextInt();
		p3.maxValImg = intItem;

		int intItem2;
		intItem2 = infile2.nextInt();
		p3.numRowsProp = intItem2;

		intItem2 = infile2.nextInt();
		p3.numColsProp = intItem2;

		intItem2 = infile2.nextInt();
		p3.minValProp = intItem2;

		intItem2 = infile2.nextInt();
		p3.maxValProp = intItem2;

		if ((p3.numrowsImg != p3.numRowsProp) || (p3.numColsImg != p3.numColsProp) || (p3.minValImg != p3.minValProp)
				|| (p3.maxValImg != p3.maxValProp)) {
			System.exit(0);

		}

		thrSize = Integer.parseInt(args[2]);

		outfile1.write(String.valueOf(p3.numrowsImg) + " ");
		outfile1.write(String.valueOf(p3.numColsImg) + " ");
		outfile1.write(String.valueOf(p3.minValImg) + " ");
		outfile1.write(String.valueOf(p3.maxValImg) + " ");
		outfile1.write("\n");

		outfile2.write(String.valueOf(p3.numRowsProp) + " ");
		outfile2.write(String.valueOf(p3.numColsProp) + " ");
		outfile2.write(String.valueOf(p3.minValProp) + " ");
		outfile2.write(String.valueOf(p3.maxValProp) + " ");
		outfile2.write("\n");

		p3.labelledAry = new int[p3.numrowsImg][p3.numColsImg];

		p3.loadImage(infile1, p3.labelledAry);
		intItem2 = infile2.nextInt();
		numCC = intItem2;

		for (int k = 0; k < numCC; k++) {
			intItem2 = infile2.nextInt();
			label = intItem2;
			// System.out.println(intItem2);

			intItem2 = infile2.nextInt();
			numpixels = intItem2;
			// System.out.println(intItem2);

			intItem2 = infile2.nextInt();
			minrow = intItem2;
			// System.out.println(intItem2);

			intItem2 = infile2.nextInt();
			mincol = intItem2;
			// System.out.println(intItem2);

			intItem2 = infile2.nextInt();
			maxrow = intItem2;
			// System.out.println(intItem2);

			intItem2 = infile2.nextInt();
			maxcol = intItem2;
			// System.out.println(intItem2);

			if (numpixels < thrSize) {
				p3.eraseCC(label, minrow, mincol, maxrow, maxcol);

			}

			else {

				p3.drawCCBox(label, minrow, mincol, maxrow, maxcol);

			}
		}

		p3.printfile(outfile1);

		p3.printPretty(outfile2);

		outfile1.close();
		outfile2.close();
		infile1.close();
		infile2.close();

	}

	private void printPretty(BufferedWriter outfile2) throws IOException {
		for (int row = 0; row < this.labelledAry.length; row++) {
			for (int col = 1; col < this.labelledAry[0].length - 1; col++) {
				int x = this.labelledAry[row][col];
				if (x == 0) {
					outfile2.write(" " + " ");
				} else {
					outfile2.write(String.valueOf(x) + " ");

				}

			}
			outfile2.write("\n");
		}

	}

	private void printfile(BufferedWriter outfile1) throws IOException {
		for (int i = 0; i < this.labelledAry.length; i++) {
			for (int j = 0; j < this.labelledAry[0].length; j++) {
				outfile1.write(String.valueOf(this.labelledAry[i][j]) + " ");

			}
			outfile1.write("\n");
		}

	}

	private void drawCCBox(int label, int minrow, int mincol, int maxrow, int maxcol) {
		for (int i = mincol - one; i < maxcol; i++) {
			this.labelledAry[minrow - one][i] = label;
			this.labelledAry[maxrow - one][i] = label;

		}

		for (int j = minrow - one; j < maxrow; j++) {
			this.labelledAry[j][mincol - one] = label;
			this.labelledAry[j][maxcol - one] = label;
		}

	}

	private void eraseCC(int label, int minrow, int mincol, int maxrow, int maxcol) {
		for (int i = minrow - one; i < maxrow; i++) {
			for (int j = mincol - one; j < maxcol; j++) {
				if (this.labelledAry[i][j] == label)
					this.labelledAry[i][j] = 0;

			}

		}
	}

	public void loadImage(Scanner infile1, int[][] Ary) {

		int intItem;
		for (int row = 0; row < Ary.length; row++) {
			for (int col = 0; col < Ary[0].length; col++) {
				intItem = infile1.nextInt();
				Ary[row][col] = intItem;

			}
		}

	}

}
