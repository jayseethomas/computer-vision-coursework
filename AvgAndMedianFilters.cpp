
/* two noise filter methods: 3X3 averaging and 3x3 median filters.
*/

#include <iostream>
#include <fstream>
using namespace std;

ifstream inputFile;
ofstream outputFile1;
ofstream outputFile2;
static int numrows;
static  int numCols;
static    int minVal;
static    int maxVal;
static    int newMax;
static    int newMin;
int two = 2;
int one = 1;
static int neighborAry[9];
int n = 3;
int size = 1;
int sizeOfNArray = 9;


void loadImg(int** mirrorFramedAry){

    
    // putting in the array
    for (int row = 1; row < numrows+1; row++)
    {
        for (int col = 1; col < numCols+1; col++)
        {
            inputFile >> mirrorFramedAry[row][col];
            
        }
    }
    
    
}

// to accomodate a 3x3 median filtering you'll need to make a copy of the array and add extra rows and cols
void mirrorFraming(int** mirrorFramedAry){
    
    
    // putting in the frames -- cols
    for (int col = 1; col < numCols+2 ; col++)
    {
        mirrorFramedAry[0][col] = mirrorFramedAry[1][col];
        mirrorFramedAry[numrows+1][col] = mirrorFramedAry[numrows][col];
        
    }
    
    
    // putting in the frames -- rows
    for (int row = 0; row < numrows+2 ; row++)
    {
        mirrorFramedAry[row][0] = mirrorFramedAry[row][1];
        mirrorFramedAry[row][numCols+1] = mirrorFramedAry[row][numCols];
        
    }
    
 
}


// load the 3 x 3 neighbor of a given pixel at position [i][j] and given the array
void loadNeighbors (int i, int j, int** mirrorFramedAry) {
    
    int index = 0;
    for (int x = i-size; x <= i+size; x++){
        for(int y = j-size; y<= j+size; y++)
        {
            neighborAry[index] = mirrorFramedAry[x][y];
            index++;
        }
    }
    
}

// given the 3x3 neighbours window, find the average value
int AVG(int neighborAry[9]){
    int sum = 0;
    for (int i=0; i<sizeOfNArray; i++)
        sum += neighborAry[i];
    
    return sum/sizeOfNArray;
}


// replace the pixel value at [i][j] with the average of all pixel values in the 3x3 window for an entire image 
void computeAVG(int** mirrorFramedAry, int** avgAry){
    
    for (int i = 1; i < numrows+1; ++i)
    {
        for (int j = 1; j < numCols+1; ++j)
        {
            loadNeighbors(i, j, mirrorFramedAry);
            int sum = AVG(neighborAry);
            avgAry[i][j] = sum;
            if (sum < newMin)
            {
                newMin = sum;
            }
            else if (sum > newMax)
            {
                newMax = sum;
            }
        }
    }
   
    
}


void copyArray(int** mirrorFramedAry,int** avgAry){
    
    for (int i = 0; i < numrows+2; ++i)
    {
        for (int j = 0; j < numCols+2; ++j)
        {
            avgAry[i][j] = mirrorFramedAry[i][j];
        }
        
    }
}

// replace the pixel value at [i][j] with the median of all pixel values in the 3x3 window for an entire image 
void computeMedian(int** mirrorFramedAry, int** avgAry){
    
    for (int i = 1; i < numrows+1; ++i)
    {
        for (int j = 1; j < numCols+1; ++j)
        {
            loadNeighbors(i, j, mirrorFramedAry);
            sort(neighborAry, neighborAry+9);
            int sum = neighborAry[4];
            avgAry[i][j] = sum;
            if (sum < newMin)
            {
                newMin = sum;
            }
            else if (sum > newMax)
            {
                newMax = sum;
            }
        }
    }
    
    
}


void printAry2File(int** ary, ofstream& file1)
{
    
    file1 << numrows;
    file1 << " ";
    file1 << numCols;
    file1 << " ";
    file1 << newMax;
    file1 << " ";
    file1 << newMin;
    file1 << "\n";
    

    
    for (int i = 1; i < numrows+1; ++i)
    {
        for (int j = 1; j < numCols+1; ++j)
        {
           file1 << ary[i][j] << " ";
        }
        file1 << "\n";
    }
    
}




int main(int argc, const char * argv[]) {
    
    inputFile.open(argv[1]);
    outputFile1.open(argv[2]);
    outputFile2.open(argv[3]);

    
    inputFile >> numrows;
    inputFile >> numCols;
    inputFile >> minVal;
    inputFile >> maxVal;
    
   
    int** mirrorFramedAry = new int*[numrows+2];
    for(int i = 0; i < numrows+2; ++i)
        mirrorFramedAry[i] = new int[numCols+2];
    
    for (int i = 0; i < numrows+2; i++)
        for (int j = 0; j < numCols+2; j++)
            mirrorFramedAry[i][j] = 0;
    
    int** avgAry = new int*[numrows+2];
    for(int i = 0; i < numrows+2; ++i)
        avgAry[i] = new int[numCols+2];

    int** medianAry = new int*[numrows+2];
    for(int i = 0; i < numrows+2; ++i)
        medianAry[i] = new int[numCols+2];
    
    
    
    newMax = minVal;
    newMin = maxVal;
    
    
    
    loadImg(mirrorFramedAry);
    mirrorFraming(mirrorFramedAry);
   
    computeAVG(mirrorFramedAry,avgAry );
    printAry2File(avgAry, outputFile1);

    newMax = minVal;
    newMin = maxVal;

    computeMedian (mirrorFramedAry, medianAry);
    printAry2File(medianAry, outputFile2);

    
  
    
}



