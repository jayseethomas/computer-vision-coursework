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
    int * hist;
    // to do
    inputFile.open(argv[1]);
    outputFile.open(argv[2]);

    inputFile >> numrows;
    inputFile >> numCols;
    inputFile >> minVal;
    inputFile >> maxVal;
    hist = new int [maxVal+1];
    
    outputFile << numrows;
    outputFile << " ";
    outputFile << numCols;
    outputFile << " ";
    outputFile << minVal;
    outputFile << " ";
    outputFile << maxVal;
    outputFile << "\n";
    
    int num;
   
    for (int i = 0; i <=maxVal+1; ++i)
    {
        hist[i] = 0;
    }
    
    
    
    while (inputFile >> num) {
        
        for (int i = 0; i < (maxVal+1); ++i)
        {
            if (num == i)
                hist[i]++;
        }
        
    
    
    }
    
    for (int i = 0; i < maxVal+1; i++){
        if (hist[i] == 0 )
            outputFile << i << "(" << hist[i] << ") : " << endl;
        else if (hist[i] >= 60){
            outputFile << i << "(" << hist[i] << ") : ";
            for (int j = 0; j < 60; j++) {
                outputFile << "+";}
            outputFile << endl;
        }
        else {
            outputFile << i << "(" << hist[i] << ") : ";
            for (int j = 0; j < hist[i]; j++) {
                outputFile << "+";}
            outputFile << endl;
    
            }
            
    }

 

    inputFile.close();
    outputFile.close();
    return 0;
}
