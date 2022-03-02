/*
 * Program to implement Poly(linked lists). Display, Add, & Evaluate polynomial with linked list with struct PolyTerm.

 * Author       Dept          Date              Notes
 
 *Oliver C      ECSE      April 12, 2020    Initial Version
 *Oliver C      ECSE      April 13, 2020     Lots of work
 *Oliver C      ECSE      April 14, 2020    Final  Version
*/
#include <stdio.h>
#include<stdlib.h>
//include header files to access functions in other files
#include "utils.h"
#include "poly.h"

struct PolyTerm {
	int coeff;
	int expo;
	struct PolyTerm *next;
};
struct PolyTerm *head, *last;//declaring head and tail of the polynomial


int addPolyTerm(int coe, int exp){//adds arguments to linked list(list of polynomials)
	struct PolyTerm *temp= (struct PolyTerm *)malloc(sizeof(struct PolyTerm));//see if there is available memory and allocates it to temp
	if(!temp) return -1;//if not enough memory, error. Exit is in polyapp

	temp->coeff=coe;//setting the struct temp's data
	temp->expo=exp;
	temp->next=NULL;
	
	if(head != NULL) last->next=temp;//if linked arr doesnt have head new term is head
	if(head == NULL) head=temp;//if head exist term is added to end of linked list
	last=temp;//last always = temp. Useful because temp is a global var although not used in ass6
	return 0;
}

void swap(struct PolyTerm *a, struct PolyTerm *b){
    int tempc = a->coeff;//setting temp's data to term a's(arg[0]) data
    int tempe = a->expo;
   // struct PolyTerm tempn=a->next
    a->coeff = b->coeff; //swapping a and b's data
    a->expo = b->expo;
   // a->next=b->next;
    b->coeff=tempc;//set's arg 2s(b)  data after moving its prev data to arg 1
    b->expo=tempe;
   // b->next=tempn;//setting nexts was not nessesary
}
void displayPolynomial(){
	struct PolyTerm *tmp, *sta;//declaring vars
        sta=head;//used for iteration through linked list
	
	//sorts list in ascending order. ie: 2x0 1x1 3x5
	while(sta){ //while term being pointed at exists. set second point to same term. While term has a linked next term, check if current exponential is greater than next. If it is swap values. Outside of if statement, set tmp to next val. Loop restarts.  
		tmp=sta;
		while(tmp->next){
			if(tmp->expo > tmp->next->expo) swap(tmp, tmp->next);
			tmp=tmp->next;
		}
		sta=sta->next;
	}

	//display list
	struct PolyTerm *temp=NULL;//create term temp
	if(head->next){//if linked array length is greater than 1(starting count from 1). set temp to the term after the head(second term)
        	temp=head->next;
	}
	struct PolyTerm *before=head;//term before is equal to the head. it will be used for comparisions later
	struct PolyTerm *nxt = NULL; 
	if(temp!=NULL && temp->next!=NULL){//if both temp and the next linked node exist, next equals said linked node
		nxt = temp->next;	
	}
	int sumc=before->coeff;//sum is the sum of coeeficient for terms of with same exponential(later)
	
	//for first term
	if(temp!=NULL) {//if temp exists 
		if(before->expo==temp -> expo){//if expos are equal set sum to sum of coeff before and temp. Sets before, nxt, temp pointers all to their next spots in the linked list if they exist. before=old temp so you dont need to check if it exists. 
			sumc=before->coeff + temp->coeff;
			if(temp->next) temp=temp->next;
				before= before->next;
		 	if(nxt->next){
                       		nxt=nxt->next;
                 	}	

		}
	}
		printf("%dx%d", sumc, head->expo);//prints the first term
	while(temp){//runs loop until temp is null
		sumc=temp->coeff;//sumc resets every time it loops through
		if(nxt!=NULL){ //if the next term exists a
			if(temp->expo == nxt->expo){
				before=before->next;
		
				 if(nxt->next){
                         	       nxt=nxt->next;
                       		 }// else{ used for testing
                              		//printf("+%dx%d", temp->coeff+nxt->coeff, temp->expo);
				//	nxt=NULL;
                		//}        
                		temp=temp->next;		
			continue;//skips the rest of loop and goes back to while conditional
			}
		}
			
		if(before->expo == temp->expo){	//if expo of before and current(temp) are equal set sumc to the sum of their coeeficients. 
			sumc = before->coeff + temp->coeff;
		}
		if(sumc>=0){ //checks to see if sum of coeeficients is positive of negative. Then prints the polyterm
			printf("+%dx%d", sumc, temp->expo);//when positive
		} else{
			printf("%dx%d", sumc, temp->expo);//when negative
		}
			
		before=before->next; //sets pointers to next positions
		temp=temp->next;
		if(nxt) nxt=nxt->next;
	}
	printf("\n");	

}

int evaluatePolynomial(int x){//evaluates Polynomial(linked list) at a specific x value and returns the y value
	int sum=0;//declaring y value
	struct PolyTerm *tmp=head;//easy to set tmp to head for going through a linked list. You can just used ->next
	while(tmp){//runs until temp=NULL(the end of the linked list)
		sum=sum + tmp->coeff * powi(x, tmp->expo);//sum= previous sum + the coeff of the current term multiplied by the answer of powi(x^expo) 
		tmp=tmp->next;//sets tmp to the next value in the list. So when loop restarts, it has a new tmp value
	}
	printf("For X: %d   Y: %d \n", x, sum);//prints x and y value respectively
	return sum;//returns the y value
}


