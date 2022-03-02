/*
 * Program to implement Utils

 * Author       Dept          Date              Notes
 
 *Oliver C      ECSE      April 11, 2020    Initial Version
*/
//include statemnts used for strtok & atoi
#include <stdio.h>//not used
#include <stdlib.h>
#include <string.h> 

#include "utils.h" //for powi

void parse(char* str, int* val, int* exp){
	int a,b;//local var for function pase. Used to set arg1(val) and arg2(exp)
	char* c= strtok(str, " ");//c is the first int value in str form.
	if(c) a=atoi(c); //turn token c to int a. 
	c=strtok(NULL, " \n ");// token c is the second value from str
	if(c) b=atoi(c); // turns token c into int b
	*val=a; //sets pointers to ints setting arg1 and arg2
	*exp=b;
	return;	
}
int powi(int x, int exp){
	int n=1; //sum of the powi term
	//if(exp==0) return n;
	for(int i=1; i <= exp; i++){// if exp=0, this for loop doesnt run so n=1. But other wise it iteratively raises the x to the power of the exp. The loop runs until i is greater than exp.
		n=n*x;//n=sum on left side and n on ther side = previous sum, x is the value being raised. So everytime x is multiplied by it's self the counter i increases. i increases until it 1 greater than exp
	}	
	return n; //returns sum
}

