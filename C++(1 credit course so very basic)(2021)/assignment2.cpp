#include <iostream>

using namespace std;
class Node
{
public:
    //initialize node constructors
    Node();
    Node(int data, Node *next, Node *prev);
    //initialize destructor
    ~Node();

    //public fields
    int data;
    Node *next;
    Node *previous;
};
//create constructors and assign everything with respect to its arguments
Node::Node()
{
    this->next = NULL;
    this->previous = NULL;
}
Node::Node(int data, Node *next, Node *previous)
{
    this->data = data;
    this->next = next;
    this->next = previous;
}
//create destructor(deletes Node)
Node::~Node()
{
    delete next;
    delete previous;
}
class DLLStructure
{
public:
    DLLStructure();
    ~DLLStructure();
    DLLStructure(int array[], int size);
    //instantiate methods
    void printDLL();
    void InsertAfter(int valueToInsertAfter, int valueToBeInserted);
    void InsertBefore(int valueToInsertBefore, int valueToBeInserted);
    void Sort();
    bool IsEmpty();
    void Delete(int value);
    int GetMax();
    int GetMin();
    int GetSize();
    int GetHead();
    int GetTail();
    DLLStructure(DLLStructure& dlls);
 

private://private fields
    Node *head;
    Node *last;
};
DLLStructure::DLLStructure()//set all prvate constructors to null
{
    this->head = NULL;
    this->last = NULL;
}
//fix
 DLLStructure:: ~DLLStructure(){//delete each node via iterating through heads by setting head to the next element and deleting head's previous element
    while (this->head != this->last) {
			this->head = this->head->next;	
            delete this->head->previous;
		}
		delete this->last;
}

//fix
DLLStructure ::DLLStructure(int array[], int size)
{
    if (size == 0)//if nothing is in array then NULL across the DLLStructure
    {
        this->head = NULL;
        this->last = NULL;
    }
    else
    { // iterrate through the arrray moving the last(tail) pointer to the newest element(element i)
       int i=0;
        this->head = new Node(array[i++], NULL, NULL);
        this->last = this->head;
        while (i<size)
        {
            this->last->next = new Node(array[i], NULL, this->last);
            this->last = this->last->next;
            i++;
        }
        this->last->next = NULL;
    }
}

void DLLStructure::printDLL()
{
    //iterates through list printing out the data of each element until it hits the tail and then prints out the tail(done so there wouldn't be a space after)
    Node *tmp = this->head;
    while (tmp != this->last)
    {

        cout << tmp->data << " ";

        tmp = tmp->next;
    }
    cout << tmp->data << endl;
}
void DLLStructure ::InsertAfter(int valueToInsertAfter, int valueToBeInserted)
{
    Node *ptmp = NULL;
    Node *tmp = this->head;
    Node *ntmp = tmp->next;
    while (tmp != NULL)//iterate through DLLSTructure until all elements are checked
    {
        if(tmp->data==valueToInsertAfter){ //if value is found then insert element by changing the pointers of the previous element(element with the data "valueToInsertAfter") and the next element(the element that previous element is point to before changes)
            ntmp=tmp->next;
            Node* insert=new Node(valueToBeInserted, tmp, ntmp);
            tmp->next=insert;
            ntmp->previous=insert;
            return;

        }
        tmp=tmp->next;
    }
    if (tmp == NULL)//if value to insert after is not found then add to end of the list by setting the new node to the tail and fixing pointers(like moving tail to new element and )
    {
        tmp = this->last;
        Node *insert = new Node(valueToBeInserted, NULL, tmp);
        tmp->next = insert;
        this->last = insert;
    }
}

void DLLStructure::InsertBefore(int valueToInsertBefore, int valueToBeInserted)
{
    Node *tmp = this->head;
    Node *ptmp = NULL;
    //set tmp to head and iterate through array until tail(NULL wasnt working for some reason) or valueToInsertBefore is found
    while (tmp != this->last)
    {
        if(tmp->data == valueToInsertBefore){
            //if valueToInsertBefore is found use the data of the node before as input for InsertAfter
             InsertAfter(ptmp->data, valueToBeInserted);
             return;
        }
        ptmp = tmp;
        tmp = tmp->next;
    }
    if(tmp==this->last){//check tail to see if it matches
        if(tmp->data==valueToInsertBefore){
            InsertAfter(ptmp->data, valueToBeInserted);
        } else{
            tmp=this->head;
            Node* insert= new Node(valueToBeInserted, NULL, NULL);
            tmp->previous=insert;
            tmp->next=tmp->next;
            this->head=insert;
            this->head->next=tmp;
        }
    }
   
   
   
}
void DLLStructure::Delete(int value)
{
    //removes a Node from the DLLStructure depending on it's data. Does this by iterating through DLLStructutre until proper element is found
    Node *tmp = this->head;
    
    while (tmp != NULL)
    {
        if(tmp->data == value){//if value is found at tmp assign pointers for tmp next and tmp prev to skip over of tmp
            tmp->previous->next=tmp->next;
            tmp->next->previous=tmp->previous;
            return;
        }
        tmp=tmp->next;
    }
   
    
    return;
}
void DLLStructure::Sort()
{
    
    int i = this->GetSize();
    Node *tmp = this->head;

    int j = 0;
    int k=0;
    int tmpI;//used for swap
    tmp=this->head;
    Node* tmp2=NULL;
    while (j < i)
    { //simple selection sort
        k = j + 1;
        tmp2=tmp->next;
        while (k < i)
        {
            if (tmp2->data < tmp->data)//swap the data  if the tmp element's data is larger than any other element after it
            {
                tmpI = tmp->data;
                tmp->data = tmp2->data;
                tmp2->data = tmpI;
            }
            tmp2=tmp2->next;
            k++;
        }
        tmp=tmp->next;
        j++;
    }
}
bool DLLStructure::IsEmpty()
{    //checks if it is empty by checking if the head is NULL(which implies that the array is empty as there is not initial value)
    if (this->head == NULL)
        return true;
    return false;
}
int DLLStructure::GetHead()
{
    //returns the data of the first element of the DLLStructure
    return head->data;
}
int DLLStructure::GetTail()
{
    //returns the data of the last element of the DLLStructure
    return last->data;
}
int DLLStructure::GetSize()
{
    //checks if the array contains any elements and if it does then GetSize returns # of elements and if not it returns 0
    if(this->head!=NULL){
    //i is set to 1 because the while loop stops when temporary element is equal to the tail element of the DLLStructure(so the index starting at 1 accounts for the tail )
    int i = 1;
    Node *tmp = this->head;
    while (tmp != this->last)
    {
        tmp = tmp->next;
        i++;
    }
        return i;
    } else{
    return 0;
    }
}
int DLLStructure::GetMax()
{
    //iterates through DLLStructure comparing each element to the int max(a temporary variable). After iterating through every element it will output the max. Which is now the relative max to all elements
    int max = -1;
    Node *tmp = this->head;
    while (tmp != this->last)
    { //if data at current tmp is greater than max so far set max to data at current tmp
        if (max < tmp->data)
            max = tmp->data;
        tmp = tmp->next;
    }
    if (max < tmp->data) max = tmp->data;//check tail
    return max;
}
int DLLStructure::GetMin()
{   // iterates through DLL structure comparing each elements data to the relative min(with respect to previous elements)
    int min = 999;
    Node *tmp = this->head;
    while (tmp != this->last)
    {//if data at current temp is less than min so far set min to data at current tmp
        if (min > tmp->data)
            min = tmp->data;
        tmp = tmp->next;
    }
    if(min>tmp->data){//check tail
        min=tmp->data;
    }
    return min;
}
DLLStructure::DLLStructure(DLLStructure& dlls){//create deep copy of DLLStructure
    int z=dlls.GetSize();

    if(z==0){//if no elements exist then initialize fields of DLLStructure to null
        this->head=NULL;
        this->last=NULL;
    }
    else{
        this->head = new Node(dlls.head->data, NULL, NULL);
        this->last = this->head;
        Node* tmp = dlls.head->next;
        int i=1;
        while(tmp!=dlls.last) {//iterate until tail
            //assign current last node pointer's next member to new Node
            this->last->next = new Node(tmp->data,NULL,this->last);
            this->last = this->last->next;
            tmp = tmp->next;
        }
        //set tail and all properties(next and last) to tail
        this->last->next = new Node(tmp->data,NULL,this->last);
        this->last = this->last->next;
        this->last->next = NULL;
       

    }




}

int main()
{
    int array[5] = {11, 2, 7, 22, 4};
    DLLStructure dll(array, 5); // note that 5 is the size of the array
    dll.printDLL();

    dll.InsertAfter(7,13);
    dll.printDLL();
    dll.InsertAfter(25, 7);
    dll.printDLL();
    dll.InsertBefore(7,26);
    dll.printDLL();
    dll.InsertBefore(19,12);
    dll.printDLL();
    dll.Delete(22);
    dll.printDLL();
    dll.Sort();
    dll.printDLL();
    if(dll.IsEmpty()){
        cout<< "The list is empty"<<endl;
    }
    cout<<"Head element is: "<< dll.GetHead()<<endl;
    cout<<"Tail element is: "<<dll.GetTail()<<endl;
    cout<<"The number of elements in the list is: "<<dll.GetSize()<<endl;
    cout<<"Max element is: "<<dll.GetMax()<<endl;
    cout<<"Min element is: "<<dll.GetMin()<<endl;
    cout<<"Q11. The simplest and most efficient way to use .GetMax() or .GetMin() would be to have a sorted list and simply call on .GetHead() and .GetTail(). We could also keep a the element as a private field. So,  it could be accessed without having to iterate through the DLLStructure"<<endl;
    cout<<"Q12. This function creates a deep copy of the DLLStructure, instead of copying the pointers like the default constructor(shallow copy). Hence, any changes with the default constructor would also change the elements of the input or copied DLLStructure. So, to avoid making changes to the copied DLLStructure one must perform a deep copy(like DLLStructure::DLLStructure(DLLStructure& dlls) does)."<<endl;


 
   
    //     //Q12
    DLLStructure dll2(dll);

     dll2.printDLL();
 

    return 0;
}