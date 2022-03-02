/*
 * Program to implement a simple calculator

 * Author	Dept	    Date		Notes
 
 *Oliver C	ECSE	Feb 18, 2020	    Initial Version
 *Oliver C	ECSE	Feb 20, 2020	  Fininshing Touches
*/
#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main(int argc, char *argv[]){
	//global varibles
	int x, y;
	char op;
	//checks how many arguments were passed. if the amount of args is not 4 then error. If 4, sets each argument accordingly
	if(argc == 4){
	x=atoi(argv[1]);
	op = argv[2][0];//length is 0 because it is char. and 0 length is basically 1.
	y=atoi(argv[3]);
	}else{
	printf("Error: usage is simplecalc <Number1><Operator><Number2>\n");
	exit(1);
	}
	//checks operator. Then based on what the operator is the code switches the mathematical functions. If + addition, - subtraction, etc.
	switch(op){ 
	//%d points to the value of x op y
		case'*':
			printf("%d\n",x*y);
			break;
		case'/':
			if(y==0){
			printf("You can't divide by 0");
			}else{
			printf("%d\n",x/y);
                        break;
			}
		case'-':
                        printf("%d\n",x-y);
			break;
		case'+':
			printf("%d\n",x+y);
                        break;
		default:
			printf("%c is not a valid operato\nr", op);
			exit(2);//error 2

	}

}
