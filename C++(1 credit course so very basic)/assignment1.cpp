//import statements
#include <iostream>
#include <string>
#include <cstdlib>
using namespace std;

//global variables
const int rows=5;
const int cols=5;



//functions

void checkPassword(){
	bool l=false; // booleans that checks if length is less than 8 char(assumed false and will be set to true if <8 chars)
	bool lessThanTwo=true;// boolean for checking if password contains the same character more than 2 times(assume false and will be set to true when found) 
	bool num=false; // boolean for checking if the password contains number (assumes false and will be set to true when found)
	bool sC=false;//boolean for checking if the password contains special character (assumes false and will be set to true when found)
	int enc=0;
	string pW;
	cout << "Enter your password: "; //Prompt user for password
    cin >> pW; // Get user input(password)
	if(pW.length()<8){ //checks if length is not greater than or equal to 8 characters and if it isnt will return failure 
		l=true;
	}
	for(int i=0; i< pW.length(); i++){ // iterates through the string looking at characters
		if(pW[i]=='$'||pW[i]=='*'||pW[i]=='@') sC=true; // checks if character matches any of the special characters
		if(pW[i]=='0'||pW[i]=='1'||pW[i]=='2'||pW[i]=='3'||pW[i]=='4'||pW[i]=='5'||pW[i]=='6'||pW[i]=='7'||pW[i]=='8'||pW[i]=='9') num=true;// checks if character is a number 
		int tmpEnc=0; //temporary counter for number of encounters
		for(int j=0; j<pW.length(); j++){ // nested for loop iterates through the string looking at characters
			if(pW[i]==pW[j]) tmpEnc++;
		}	
		if(tmpEnc>enc) enc=tmpEnc; //sets global encounters to temporary encounters if temporary is greater than global encounters
		if(enc>2) lessThanTwo=false; //if more than two encounters are found sets lessThanTwo to false and will give an error in the else statement below(look at *)
	}
	
	if(!l && sC && num && lessThanTwo){//if accpeted is never turned to false 
		cout << "Password Accepted."; 
	} else{ //returns which reason(or reasons) the password was not accepted
		if(l) cout<< "Error: Your password must have at least 8 characters.\n";
		if(!sC) cout<<"Error: Your password must contain one of the characters '$', '#', '*'.\n";
		if(!num) cout<<"Error: Your password must contain a number.\n";
		if(!lessThanTwo) cout<<"Error: Your password must not have more than 2 of the same character.\n"; //*
	}
	
}


string Nato(char input){ //helper method that matches each char to its respective NATO word
	switch(input){ //matches capital and lower case letter to same Nato because a return is empty so automatically goes to next line without checking cases because no break statement was found and has already entered a case
		case 'A':
		case 'a': 
			return "Alfa";
			break; 	// after returning breaks(excessive but follows standard of breaking in switch statements)
		case 'B':
		case 'b': 
			return "Bravo";
			break;
		case 'C':
		case 'c': 
			return "Charlie";
			break;
		case 'D':
		case 'd': 
			return "Delta";
			break;
		case 'E':
		case 'e': 
			return "Echo";
			break;
		case 'F':
		case 'f': 
			return "Foxtrot";
			break;
		case 'G':
		case 'g': 
			return "Golf";
			break;
		case 'H':
		case 'h': 
			return "Hotel";
			break;
		case 'I':
		case 'i': 
			return "India";
			break;
		case 'J':
		case 'j': 
			return "Juliett";
			break;
		case 'K':
		case 'k': 
			return "Kilo";
			break;
		case 'L':
		case 'l': 
			return "Lima";
			break;
		case 'M':
		case 'm': 
			return "Mike";
			break;
		case 'N':
		case 'n': 
			return "November";
			break;
		case 'O':
		case 'o': 
			return "Oscar";
			break;
		case 'P':
		case 'p': 
			return "Papa";
			break;
		case 'Q':
		case 'q': 
			return "Quebec";
			break;
		case 'R':
		case 'r': 
			return "Romeo";
			break;
		case 'S':
		case 's': 
			return "Sierra";
			break;
		case 'T':
		case 't': 
			return "Tango";
			break;
		case 'U':
		case 'u': 
			return "Uniform";
			break;
		case 'V':
		case 'v': 
			return "Victor";
			break;
		case 'W':
		case 'w': 
			return "Whiskey";
			break;
		case 'X':
		case 'x': 
			return "X-ray";
			break;	
		case 'Y':
		case 'y': 
			return "Yankee";
			break;
		case 'Z':
		case 'z': 
			return "Zulu";
			break;
		default: 
			return "";
			break;
	}
}
void convertPhonetic(){
	string word; //initilize input word
	cout << "Please enter a word: "; //Prompt user for password
    cin >> word; //sets word to user input
    string output;
    for(int i=0; i< word.length(); i++){ //iterates through the char of the input
    	if(output.length()==0){ 
            output=Nato(word[i]);
        } else if(Nato(word[i])!="") { //char is not the default then concatenate the nato string to the output(I did this so there would not be an extra space if someone input something with a number or special character. (Example without doing this: a2s = Alfa  Sierra))
    		output = output + " " +Nato(word[i]);
        }
    }
    cout<< output;
}
void fillMatrix(int matrix[rows][cols]){ //fills all columns of a row and then moves on to next row(goes into row and then iterates through all of its elements via columns and then moves to next row)
	for(int i=0; i< rows; i++){ //i represents the current row
		for(int j=0; j<cols; j++){ //j represents the current column
			matrix[i][j]=rand()%26; //matrix element will range from 0-25 because of mod 26. (Each element is randomized because rand() returns a random number)
		}
	}
}
void printMatrix(int matrix[rows][cols]){
	cout<< "-----------------------\n";
	for(int i=0; i< rows; i++){ //iterator for rows
		string oRow; //String used for outputing each row
		for(int j=0; j<cols; j++){ //iterator for columns
			if(j==0){ //if first column of row i then oRow is the first element of the row plus a space
				oRow = to_string(matrix[i][j]) + " ";
			} else if(j==4){//if last column of row i then oRow is the prev value of row (the previous 4 elements in the row) concatenated with a divider and the last element in the row and then a space
				oRow = oRow + "| " + to_string(matrix[i][j]) + " ";
			} else { //if middle columns of row i then oRow is the prev value of row concatenated with a divider and then the new element
				oRow= oRow +"| " + to_string(matrix[i][j])+ " ";
			}
		}
		cout<< oRow + "\n";//prints out the row on a single line 
		cout<< "-----------------------\n";//on a new line(because \n on line above) a divider is created for reader 
	}
	cout<<  "\n"; //after matrix is printed two lines of empty space are created
	cout<<  "\n";
}	



void multiplyMatrices(int matrix_left[rows][cols], int matrix_right[rows][cols], int matrix_result[rows][cols]){ //this method was a ***** because I was using an online compiler and it was giving me the wrong output but then I put it in vim and it worked. I looked it up and it is due to how the code is processed in terminal. Do you reccomend strictly using vim or an ide from now on?
	//matrix multiplication is row by column which what this function does
	//initialize variables
	if(x < rows) {//after all rows have been traversed the function terminates(because nothing runs if this conditional isnt entered) so x<row
        if(y< cols) { //runs until all columns of matrix_right are created
            if(z < cols) {//runs until all columns of matrix_left are checked(z is current column index)
                matrix_result[x][y] += matrix_left[x][z]*matrix_right[z][y]; 
                z++;
                multiplyMatrices(matrix_left,matrix_right,matrix_result);//recursively call upon method(static variables will retain their value)
            }
            z = 0; //column value=0 (left)so it is ready for new row
            y++; 
            multiplyMatrices(matrix_left,matrix_right,matrix_result);//recursively call upon method(static variables will retain their value)
        }
        y= 0;///column value=0 (right)so it is ready for new row
        x++;//new row of matrix_result
        multiplyMatrices(matrix_left,matrix_right,matrix_result);//recursively call upon method(static variables will retain their value
    }
}

int main() {
	checkPassword();
	convertPhonetic();
	int matrix[rows][cols];
	int matrix2[rows][cols];
	int matrix_result[rows][cols];
	fillMatrix(matrix);
	fillMatrix(matrix2);
	printMatrix(matrix);
	multiplyMatrices(matrix, matrix2, matrix_result);
	printMatrix(matrix_result);
	return 0;
}
