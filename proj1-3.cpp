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
    
  
    
    int thres;
    cout << "Please enter an integer value for the threshold: ";
    cin >> thres;
    
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

