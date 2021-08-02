/* implement the 8-connected component algorithms*/

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;


public class ConnComponents {
	
	public static int two = 2;
	public static int one = 1;
	public int minLabel = 100;
	public int diffLabel = 100;
	public static int numNb = 5;
	public int size = 1;
	public int EightConn = 9;
	public int[]  NonZeroNeighbor = new int[numNb];
	public int[] EQArray; 
	public int newLabel = 1;
	public int newMin = 100;
	public int newMax = 0;


	
	
	public class Property {
		public int label;
		public int numPixels;
		public int minrow;
		public int maxrow;
		public int minCol;
		public int maxCol;
		
		public Property(int l, int np, int mr, int mar, int mc, int mac) {
			this.label = l;
			this.numPixels = np;
			this.minrow = mr;
			this.maxrow = mar;
			this.minCol = mc;
			this.maxCol = mac;
			
			
		}
	}
	
	
	

	public static void main(String[] args) throws IOException {
		
		int numrows;
		int numCols;
		int minVal;
		int maxVal;
		
		
		
		
		
        Scanner infile1 = new Scanner(new FileReader(args[0]));
		BufferedWriter bw = new BufferedWriter(new FileWriter( args[1]));
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(args[2]));
		BufferedWriter bw3 = new BufferedWriter(new FileWriter(args[3]));

		int intItem;
		intItem = infile1.nextInt();
		bw.write(String.valueOf(intItem)+ " ");
		numrows = intItem;

		intItem = infile1.nextInt();
		bw.write(String.valueOf(intItem)+ " ");
		

		numCols = intItem;

		intItem = infile1.nextInt();
		bw.write(String.valueOf(intItem)+ " ");
		

		maxVal = intItem;
	
		intItem = infile1.nextInt();
		bw.write(String.valueOf(intItem)+ " ");
		
		
		minVal = intItem;
	
		
		bw.write("\n");
		



	
		int[][] zeroframedAry = new int[numrows+two][numCols+two];
		
		
		Proj3_CV p3 = new Proj3_CV();
		p3.EQArray = new int[(numrows * numCols)/4];
		for (int i = 0; i < p3.EQArray.length; i++) {
			p3.EQArray[i] = i;
			
		}

		p3.initalize(zeroframedAry);	
		p3.loadImage(infile1,zeroframedAry);
		
		
		
		p3.Connect8CCPass1(zeroframedAry,p3.newLabel, p3.EQArray);
		p3.printPretty(zeroframedAry, bw);
		p3.printEQ(p3.EQArray, bw);
		bw.write("\n");
		
		
		p3.Connect8CCPass2(zeroframedAry,p3.newLabel, p3.EQArray);
		p3.printPretty(zeroframedAry, bw);
		p3.printEQ(p3.EQArray, bw);
		bw.write("\n");

		
		p3.manageEQArray(p3.EQArray);
		p3.printEQ(p3.EQArray, bw);
		bw.write("\n");


		
		Property[] CCProperty = new Property[p3.newLabel-1];
		for (int c = 0; c < CCProperty.length; c++) {
			CCProperty[c]= p3.new Property(c+1, 0, 1000, 0, 1000, 0);
				
		}
		
		
		p3.ConnectCCPass3(zeroframedAry, p3.EQArray, CCProperty); 
		p3.printPretty(zeroframedAry, bw);
		p3.printEQ(p3.EQArray, bw);
		bw.write("\n");

		

		
		bw2.write(String.valueOf(numrows)+ " ");
		bw2.write(String.valueOf(numCols)+ " ");
		bw2.write(String.valueOf(p3.newMin)+ " ");
		bw2.write(String.valueOf(p3.newMax)+ " ");
		bw2.write("\n");
		
		
		for(int row = 1; row < zeroframedAry.length-one; row++) {
			for(int col = 1; col < zeroframedAry[0].length-one; col++) {

				bw2.write(zeroframedAry[row][col] + " ");

		}
			bw2.write("\n");

		}
		
		
		bw3.write(String.valueOf(numrows)+ " ");
		bw3.write(String.valueOf(numCols)+ " ");
		bw3.write(String.valueOf(p3.newMin)+ " ");
		bw3.write(String.valueOf(p3.newMax)+ " ");
		bw3.write("\n");
		bw3.write(String.valueOf(p3.newLabel-1)+ "\n");
		for(int i = 0; i < p3.newLabel-1; i++) {
			bw3.write(String.valueOf(CCProperty[i].label)+ "\n");
			bw3.write(String.valueOf(CCProperty[i].numPixels)+ "\n");
			bw3.write(String.valueOf(CCProperty[i].minrow)+ " ");
			bw3.write(String.valueOf(CCProperty[i].minCol)+ "\n");
			bw3.write(String.valueOf(CCProperty[i].maxrow)+ " ");
			bw3.write(String.valueOf(CCProperty[i].maxCol)+ "\n");




			

			
			
		}

		
		bw.flush();
		bw2.flush();
		bw3.flush();


		infile1.close();
		bw.close();
		bw2.close();
		bw3.close();



		


	
	}
	
// use the EQAry to relabel the components
// keep track the newMin newMax for the label image header as well as compute the property of each c.c.
private void ConnectCCPass3(int[][] zeroframedAry, int[] eQArray, Property[] CCProperty) {
	for (int row = 1; row < zeroframedAry.length-1; row++)   {
        for (int col = 1; col < zeroframedAry[0].length-1; col++){
        	
        	if (zeroframedAry[row][col] == this.EQArray[zeroframedAry[row][col]])
        	{
        		
        	}
        	else {
            	zeroframedAry[row][col] = this.EQArray[zeroframedAry[row][col]];

      
        	}
        	
        }
	}
	
	for (int k = 0; k < this.EQArray.length; k++) {
		this.EQArray[k] = k;
	}
	
	for (int row = 1; row < zeroframedAry.length-1; row++)   {
        for (int col = 1; col < zeroframedAry[0].length-1; col++){
        
        	int x = zeroframedAry[row][col];
        	if (x>0) {
        	int y = x-1;
        	CCProperty[y].numPixels++;
        	if(col > (CCProperty[y].maxCol))
        	{
        		CCProperty[y].maxCol = col;
        	}
        	
        	if( (CCProperty[y].minCol) > col)
        	{
        		CCProperty[y].minCol = col;
        	}
        	
        	if(row > (CCProperty[y].maxrow))
        	{
        		CCProperty[y].maxrow = row;
        	}
        	if( (CCProperty[y].minrow) > row)
        	{
        		CCProperty[y].minrow = row;
        	}
        	if(x > newMax) {
        		newMax = x;
        	}
        	if(x < newMin) {
        		newMin = x;
        	}
        		
        	
        	
        	
        	}
        	
        }
	}
	
	


	
	
	}


// Update EQAry of all non-zero neighbors to minLabel
private void manageEQArray(int[] eQArray2) {
		int temp = 1;
		for (int i = 1; i < this.newLabel; i++) {
			if (i == this.EQArray[i]) {
				this.EQArray[i] = temp;
				temp++;
			}
			else {
				this.EQArray[i] = this.EQArray[ this.EQArray[i] ];
			}
		}
		// System.out.println(temp);
		this.newLabel = temp;
		
	}


//  scan zeroFramedAry R to L & B to T (inside the frame)
// if zeroFramedAry (i, j) <= 0
// repeat step 1
// step 2 numNz -> loadNonZero(i, j, zeroFramedAry (i, j), minLabel, diffLabel)
// if numNz > 0 && diffLabel > 1
// zeroFramedAry (i, j) ß minLabel
// updateEQ (minLabel)

step 3: repeat step 1 to step 2 until all pixels are processed
private void Connect8CCPass2(int[][] zeroframedAry, int newLabel2, int[] eQArray2) {
	// System.out.println(this.minLabel);
			for (int row = zeroframedAry.length-2; row>=0; row--)   {
		        for (int col = zeroframedAry[0].length-2; col>=0; col--){
		        	int x = zeroframedAry[row][col];
		        	if (x > 0) {
		        		int numNz = loadNonZero(2, row, col, 1, minLabel, diffLabel, zeroframedAry);
		        		if(numNz > 0 && this.diffLabel > 1) {
		        			zeroframedAry[row][col] = this.minLabel;
		        			updateEQ(this.minLabel, this.EQArray);

		        		
		            
		        }
		    }
		
	}
			}
}


private void printEQ(int[] eQArray, BufferedWriter bw) throws IOException {
	bw.write("EQ Array" + "\n");
	bw.write("Index : ");

	for (int i = 0; i <= this.newLabel ; i++) {
			bw.write(i + " ");
		}
	bw.write("\n");
	bw.write("Value : ");

	for (int i = 0; i <=this.newLabel; i++) {
			bw.write(eQArray[i] + " ");
		}
		
	}


private void printPretty(int[][] zeroframedAry, BufferedWriter bw) throws IOException {
	for (int row = 1; row < zeroframedAry.length-1; row++)   {
        for (int col = 1; col < zeroframedAry[0].length-1; col++){
        	int x = zeroframedAry[row][col];
        	if (x == 0 )  {
        		bw.write(" " + " ");
        	}
        	else {
        		bw.write(String.valueOf(x)+ " ");

			}
			
		}
		bw.write("\n");
	}
		
	}




// scan left to right, top to bottom. if <=0 do nothing, if >=1 load the number of non-zero neighbours
// case 1: if numNonZNeighs == 0
// zeroFramedAry (i, j) -> newLabel
// newLabel ++
// case 2: if numNonZNeighs > 0 && diffLabel == 1
// zeroFramedAry (i, j) -> minLabel
// case 3: if numNonZNeighs > 0 && diffLabel > 1
// zeroFramedAry (i, j) -> minLabel
// updateEQ (minLabel)

public void Connect8CCPass1(int[][] zeroframedAry, int newLabel, int[] EQArray) {
		
		// System.out.println(this.minLabel);
		for (int row = 1; row < zeroframedAry.length-1; row++)   {
	        for (int col = 1; col < zeroframedAry[0].length-1; col++){
	        	int x = zeroframedAry[row][col];
	        	if (x > 0) {
	        		int numNz = loadNonZero(1, row, col, 0, minLabel, diffLabel, zeroframedAry);
	        		if(numNz == 0) {
	        			// System.out.println("case 1");
	        			zeroframedAry[row][col] = this.newLabel;
	        			this.newLabel++;
	        		}
	        		else if(numNz > 0 && this.diffLabel == 1) {
	        			//System.out.println("case 2");
	        			zeroframedAry[row][col] = this.minLabel;

	        		}
	        		else if(numNz > 0 && this.diffLabel > 1) {
	        			// System.out.println("case 3");
	        			zeroframedAry[row][col] = this.minLabel;
	        			updateEQ(this.minLabel, this.EQArray);
	        			// System.out.println("updated");


	        		}
	        	
	        	}
	            
	        }
	    }

		
	}

	
	// Update EQAry of all non-zero neighbors to minLabel

	private void updateEQ(int minLabel, int[] EQArray) {
	for(int x = 0; x < NonZeroNeighbor.length; x++) {
		this.EQArray[NonZeroNeighbor[x]]= this.minLabel; 
	}
	
}

	// load non-zero neighbors of given pixel value [i][j] w/rt the pass
	// returns the number of non-zero neighbours 

	public int loadNonZero(int whichPass, int row, int col, int extra, int minLabel2, int diffLabel2, int[][] zeroframedAry) {
		int num = 0;

		if (whichPass == 1) {
		int[] array = new int[EightConn];
		array = loadNeighbours(row, col, zeroframedAry);
		
		
		NonZeroNeighbor = Arrays.copyOfRange(array,0,numNb-one);
		
		NonZeroNeighbor = Arrays.stream(NonZeroNeighbor).filter(x -> x != 0).toArray();
		num = NonZeroNeighbor.length;

		

		NonZeroNeighbor = IntStream.of(NonZeroNeighbor).distinct().toArray();
		this.diffLabel = NonZeroNeighbor.length;

		if (num == 0) {

		}
		else {
			this.minLabel =  Arrays.stream(NonZeroNeighbor).min().getAsInt();
		}

		}
		else if (whichPass == 2) {

			int[] array = new int[EightConn];
			array = loadNeighbours(row, col, zeroframedAry);
			
			
			NonZeroNeighbor = Arrays.copyOfRange(array,numNb-1,array.length);
			
			NonZeroNeighbor = Arrays.stream(NonZeroNeighbor).filter(x -> x != 0).toArray();
			num = NonZeroNeighbor.length;
			NonZeroNeighbor = IntStream.of(NonZeroNeighbor).distinct().toArray();
			this.diffLabel = NonZeroNeighbor.length;

			if (num == 0) {

			}
			else {
				this.minLabel =  Arrays.stream(NonZeroNeighbor).min().getAsInt();
			}

			}
			
		

		return num;
}


	public int[] loadNeighbours(int a, int b, int[][] zeroframedAry) {
		int[] allNeighs = new int[EightConn];
		int index = 0;
	    for (int x = a-size; x <= a+size; x++){
	        for(int y = b-size; y<= b+size; y++) {
	           allNeighs[index] = zeroframedAry[x][y];
	           index++;
	        }
	    }
		
	    return allNeighs;


	}


	public void loadImage(Scanner infile1, int[][] zeroframedAry) {
		
		int intItem;
		 for (int row = 1; row < zeroframedAry.length-1; row++)
		    {
		        for (int col = 1; col < zeroframedAry[0].length-1; col++)
		        {
		        	intItem = infile1.nextInt();
		        	zeroframedAry[row][col] = intItem;
		            
		        }
		    }
		    
		
	}

	public void initalize (int[][] zeroframedAry) {
		for (int i = 0; i < zeroframedAry.length; i++)
			  for (int j = 0; j < zeroframedAry[0].length; j++)
			    zeroframedAry[i][j] = 0;

	}

}
