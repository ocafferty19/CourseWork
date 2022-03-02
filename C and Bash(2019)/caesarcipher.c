/*
 * Program to implement a caesar ciphe\r

 * Author       Dept        Date                Notes
 
 *Oliver C      ECSE    Feb 18, 2020        Initial Version
 *Oliver C      ECSE    Feb 20, 2020      Fininshing Touches
*/
#include <stdlib.h>
#include <stdio.h>
int main(int argc, char *argv[]){
	//variables
	int shift;
	char str[1000];
	//if command is ./caesarcipher # it has 2 arguments. Argc is the counter for arguments so it is equal 2. Also, sets shift to the second argument.
	if(argc==2){
		shift=atoi(argv[1]);
	}else{
		printf("Error: Usage is caesarcipher <shift #>");
		exit(1);
	}
	//stops scanning after finding a tab.
	scanf("%[^\t]", str);
//runs until it sees a blank char
	for(int i = 0; str[i] != '\0'; ++i){
		char c;
		c = str[i];
		//sets parameters to only effect lower case letters
		if(c >= 'a' && c <= 'z'){
			//checks if shift + c is greater than z before setting c to c+shift. If it is, c's ascii code is subtracted by 25 and then shifted.  If it is not greater, it sets c to c + shift.
			if(c+shift > 'z'){
				c = c - 'z'+ 'a' + shift-1;
			}else{
				 c = c + shift;
			}
			if(c < 'a'){
				c = c - 'a' +'z'+1;
			}
			//sets local variable back to global
			str[i] = c;
		}
	}

printf("%s", str);
}
