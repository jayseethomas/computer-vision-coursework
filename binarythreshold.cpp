/* The binary threshold operation is the simplest way to extract foreground objects in a given grey-scale image. 
Given an image and a threshold value, the binary threshold operation is to transform pixels in the input image from grey-scale to binary values, where
if imgIn(i, j) >= threshold value then
result(i, j) -> 1
else
result(i, j) -> 0 */

#include <iostream>
#include <fstream>
using namespace std;


int main(int argc, const char * argv[]) {
    
    ifstream inputFile;
    ofstream outputFile;
    int numrows;
    int numCols;
    int minVal;
    int maxVal;
    // to do
    inputFile.open(argv[1]);
    outputFile.open(argv[2]);
    
    inputFile >> numrows;
    inputFile >> numCols;
    inputFile >> minVal;
    inputFile >> maxVal;
    
    int** result = new int*[numrows];
    for(int i = 0; i < numrows; ++i)
        result[i] = new int[numCols];

    outputFile << numrows;
    outputFile << " ";
    outputFile << numCols;
    outputFile << " ";
    outputFile << "0";
    outputFile << " ";
    outputFile << "1";
    outputFile << "\n";
    
    // get threshold from user
    
    int thres;
    cout << "Please enter an integer value for the threshold: ";
    cin >> thres;
    
    // process the input file from left to right and top to bottom
    // if the pixel value at [i][j] is greater or equal to threshold, replace with a 1
    // if it is less than replace with a 0
    
        for (int row = 0; row < numrows; row++)
        {
            for (int col = 0; col < numCols; col++)
            {
                inputFile >> result[row][col];
                
                if(result[row][col] >= thres)
                {
                    outputFile << "1" << ' '; // space
                }
                else
                {
                    outputFile << "0" << ' '; // space
                }
            }
            outputFile << '\n'; // newline
        }
        
        
    
    

    inputFile.close();
    outputFile.close();
    return 0;
}

