/*
 * Program to implement polyapp.c the main in the polyapp.

 * Author       Dept          Date              Notes
 
 *Oliver C      ECSE      April 11, 2020    Initial Version
 *Oliver C      ECSE      April 14, 2020     Final Version
*/
#include<stdlib.h>
#include<stdio.h>
#include<string.h>
//header files
#include "poly.h"
#include "utils.h"

int *c;//global int pointers
int *e;

int main(int argc, char*argv[]){
	
	char arr[512];
	FILE*file=fopen(argv[1], "r");//opens file passed in second arg in read mode
	if(file ==NULL){//if can't find file(argv[1], terminate with error code 1
		printf("Error! Can't find %s", argv[1]);
		return 1;
	}
	fgets(arr,511,file); //gets line from file.

	while(!feof(file)){//while not end of file
      		int coe;//local var recreated at begining of loop
		int exp;
		c=&coe;//c value points at coe. So when c=x, coe=x. exact same for e and exp
		e=&exp;
	
		parse(arr,c,e);//parses data. sets args c and e.

		addPolyTerm(coe,exp);//creates and adds the polyterm with coe being the coeffficient of the term and exp being the exponential
		
       		fgets(arr,511,file);//get another line to be parsed
  	}
	printf("\n");
	displayPolynomial();//displays the sorted polynomial
	int n=-2;//sets the first value to be evaluated
	while(n<=2){// n from -2 to 2 will be evaluated, iterating in increasing order. Printing the x and the y value(sum). 
		evaluatePolynomial(n);
		n++;
	}
	return 0;//if ran properly returns 0
}
