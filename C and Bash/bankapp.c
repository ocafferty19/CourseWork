/*
 * Program to implement a bankapp

 * Author       Dept        Date                Notes
 
 *Oliver C      ECSE    Mar 6, 2020        Initial Version
 *Oliver C      ECSE    Mar 13, 2020      Fininshing Touches
*/
#include  <stdio.h>
#include <stdlib.h>
#include <string.h>

void displayFile(FILE *);
int withdrawl(FILE *file, char *aNum, char *date, char *withdrawl){
	char new[50];
//creates new line that withdrawls from account
//adds line new to CSV file
	strcpy(new, "TX,");
	strcat(new, aNum);
	strcat(new, ",");
	strcat(new, date);
	strcat(new, ",");
	strcat(new, "-");
	strcat(new, withdrawl);
	strcat(new, "\n");
	fputs(new, file);

}
int deposit(FILE *file, char *aNum, char *date, char * deposit){
	char new[50];
//creates new line that deposits from account
//adds line new to CSV file
	strcpy(new, "TX,");
	strcat(new, aNum);
	strcat(new, ",");
	strcat(new, date);
	strcat(new, ",");
	strcat(new, deposit);
	strcat(new, "\n");
	fputs(new, file);
}
int add(FILE *file, char *aNum, char *name){
	char new[50];
	//creates new line that adds a new account
//adds line new to CSV file
	strcpy(new, "AC,");
	strcat(new, aNum);
	strcat(new, ",");
	strcat(new, name);
	strcat(new, "\n");
	fputs(new, file);

}
int main(int argc, char *argv[]){
        if(argc==1){
		//if one given one string as input erros with  usage messor
                fprintf(stderr, "%s", "Error, incorrect usage!\n -a ACCTNUM NAME\n -d ACCTNUM DATE AMOUNT\n -w ACCTNUM DATE AMOUNT\n");
                exit(1);
        }

       	if(fopen("bankdata.csv", "r")==NULL){
         //if csv file does not exist. Error 100
	 	fprintf(stderr, "%s", "Error, unable to locate bankdata.csv\n");
                exit(100);
        }
	FILE *file= fopen("bankdata.csv","at");
	char op=argv[1][1];//used for if statements below. for type of bank interacton
	char buff[1024];

	//fprintf(stdout,"%d\n" ,argc);
        

        if(op == 'a'){
                if(argc==4){
			//if right of # arguents are passed adds new account
			add(file,argv[2], argv[3]);	
                }else{
                        fprintf(stderr, "%s", "Error, incorrect usage!\n -a ACCTNUM NAME\n");
      	                exit(1);
			//error if not right #
		} 

        } else if(op == 'w'){
                if(argc==5){
			 //if right of # arguents are passed withdrawls from  account
			withdrawl(file, argv[2],argv[3],argv[4]);
        	  }else{
                        fprintf(stderr, "%s", "Error, incorrect usage!\n -w ACCTNUM DATE AMOUNT\n");
                        exit(1);
			 //error if not right #
                }
        } else if(op == 'd'){
                if(argc==5){
			//if right of # arguents are passed deposits in account
			deposit(file,argv[2], argv[3], argv[4]);
                }else{
                        fprintf(stderr, "%s", "Error, incorrect usage!\n -d ACCTNUM DATE AMOUNT\n");
                        exit(1);
			 //error if not right #
               }
        }

}
//copied from class slides used for early testing
void displayFile (FILE *p) {
       	char c;
	while(!feof(p)) {
       		c = fgetc(p);
		putc(c, stdout);
	}
}
