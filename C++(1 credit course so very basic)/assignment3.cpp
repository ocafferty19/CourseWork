// include statements
#include <iostream>
#include <string>
#include <vector>
#include <time.h>    
#include <algorithm> 
using namespace std;
enum Rank//declare enumeators
{//rank = value of card
    ACE = 1,//ace value when 11 is calc in getTotal of Hand 
    TWO = 2,
    THREE = 3,
    FOUR = 4,
    FIVE = 5,
    SIX = 6,
    SEVEN = 7,
    EIGHT = 8,
    NINE = 9,
    TEN = 10,
    JACK,
    QUEEN,
    KING
};

enum Type
{
    CLUBS,
    DIAMONDS,
    HEARTS,
    SPADES
};


class Card
{
private:
    // private fields
    enum Rank rank;
    enum Type type;

   
public:
    //instatiate constructor
    Card(Rank rank, Type type);
    // Instatiate functions
    int getValue();//gets value of card
    void displayCard();//Prints card
};
Card::Card(Rank rank, Type type){//assign arguments to respective fields
    this->rank=rank;
    this->type=type;
}
int Card::getValue(){
    Rank r=this->rank;//temporary variable r used to compare the Cards rank to all potential option and outputting their respective values in blackjack
    if(r==ACE){
        return 1;
        //check for 11 in getTotal
    }
    else if(r==TWO){
        return 2;
    } 
     else if(r==THREE){
         return 3;
    }  else if(r==FOUR){
        return 4;
    }  else if(r==FIVE){
        return 5;
    }  else if(r==SIX){
        return 6;
    }  else if(r==SEVEN){
        return 7;
    }  else if(r==EIGHT){
        return 8;
    }  else if(r==NINE){
        return 9;
    }  else{
        return 10;
    }   
}
void Card::displayCard(){//print value of card and then the first letter of its type. Could have used getValue for the value but Im lazy and I dont want to change it bc its already functional and I spent too much time debugging and its beautiful outside
    Rank r=this->rank;
    if(r==ACE){
           std::cout<<"1";
    } else if(r==TWO){
         std::cout<<"2";
    } else if(r==THREE){
          std::cout<<"3";
    }  else if(r==FOUR){
       std::cout<<"4";
    }  else if(r==FIVE){
        std::cout<<"5";
    }  else if(r==SIX){
         std::cout<<"6";
    }  else if(r==SEVEN){
         std::cout<<"7";
    }  else if(r==EIGHT){
         std::cout<<"8";
    }  else if(r==NINE){
        std::cout<<"9";
    }  else if(r==JACK){
         std::cout<<"J";
    }  else if(r==QUEEN){
        std::cout<<"Q";
    } else{
        std::cout<<"K";
    }
    
    Type t=this->type;
    if(t==CLUBS){
        std::cout<<"C ";
    }
    else if(t==DIAMONDS){
        std::cout<<"D ";
    }
    else if(t==HEARTS){
        std::cout<<"H ";
    }
    else{
        std::cout<<"S ";
    }
        
    
}
class Hand{
    
    public:
      vector<Card> h;//private field representing all cards in hand
        void add(Card card);
        void clear();
        int getTotal();

};
void Hand::add(Card card){//add card to hand
    this->h.push_back(card);
}
void Hand::clear(){//have an empty hand like at the end of game or smth
    this->h.clear();
}
int Hand::getTotal(){//returns value of hand and fixes the value of ace if conditions are met
    int sum=0;

    int a=0;//ace counter
    for(int i=0; i< h.size(); i++){//iteraate through all cards and add their values to sum
     
        if(this->h[i].getValue()==1){
            a++;
        }
        sum+=this->h[i].getValue();
    } 
    while(sum<=11&&a>0){//if has ace and sum is less than bust before adding the value 10 (so 1-> 11) then ace =11
        sum+=10;
        a--;
    }


    return sum;
}
class Deck{
   
    public:
        vector<Card> d;//private field representing all cards in deck(52 cards)
        void populate();
        void shuffle();
        void deal(Hand &h);
}; 
void Deck::populate(){//uses enumerator to add cards to deck
    int r=ACE;
    while(r<=KING){//For all ranks Ace to King(using enum)
        int t=CLUBS;
        while(t<=SPADES){//create 4 cards of each rank (1 for each type)
             this->d.push_back(Card((Rank)r, (Type)t));//newly created card added to deck
             t++;
        }
        r++;
    }
}
void Deck::shuffle(){
    int i=0;
    int r;
    srand (time(NULL));
    Card tmp(ACE, SPADES);//just did this to initialize
    while(i<=51){//0-51=52 so all cards will be shuffled
        tmp=d[i];//swap value at index i with random index
        r=rand()%52;//mod 52 so index does not exceed deck size
        d[i]=d[r];
         d[r] =tmp;
        i++;
    }


}
void Deck :: deal(Hand &h){//take card from top of pile(top card is at end of vector(Deck))
     
    Card c=this->d.back();
    this->d.pop_back();
    h.add(c);
   
}


class AbstractPlayer{//create abstract class
public:
    Hand h;
    virtual bool isDrawing() const = 0;
    bool isBusted(Hand h);
};
bool AbstractPlayer::isBusted(Hand h){//if total>21 bust

    return (h.getTotal()>21);

}

class HumanPlayer: public AbstractPlayer{//create HumanPlayer class that extends abstract Player(done to access isBusted)
   public:
    Hand h;
    bool isDrawing() const;
    void announce(Hand cpu);
};
bool HumanPlayer::isDrawing() const{//if user enters 'y' will draw a card(so total of hand increases by the value of the card) else total remains same(same number of cards[no drawing])
    char yN;
    cout << "Draw a card? (y/n) ";
    cin >> yN;
    return (yN=='y');
}
void HumanPlayer::announce(Hand cpu){//announce respective winning screen
    if(cpu.getTotal()>21 && !this->isBusted(this->h)){//if player busts print respective results
        cout << "Casino busts." << endl;
        cout<< "Player wins."<<endl;
    } else if(cpu.getTotal()<=21 && this->isBusted(this->h)){//if dealer(cpu) busts print respective results
        cout << "Player busts." << endl;
        cout<< "Casino wins."<<endl;
    } else if(cpu.getTotal() > this->h.getTotal()){//if sum of cards of dealer > players sum then casino wins and vice versa
         cout<< "Casino wins."<<endl;
    } else if(cpu.getTotal()<this->h.getTotal()){//vice versa as mentioned above
        cout<< "Player wins."<<endl;
    } else{//if total of player is same as dealer then push
        cout<< "Push: No one wins."<<endl;
    }
}

class ComputerPlayer: public AbstractPlayer{//create ComputerPlayer class that extends abstract Player(done to access isBusted)
    public:
    Hand h;
    bool isDrawing() const;
};
bool ComputerPlayer::isDrawing() const{//if dealer(cpu) current hand is >16 then the dealer wont draw
    Hand a=this->h;
    return (a.getTotal()<=16);
}

class BlackJackGame{//create BlackJack class with respective fields and funcs
    public:
    Deck m_deck;
    ComputerPlayer m_casino;
    void play();
};
void BlackJackGame:: play(){
    HumanPlayer hP;
    m_deck.d.clear();//clear everything at beginning of game
    m_casino.h.clear();
   
    m_deck.populate();//create and shuffle decl
    m_deck.shuffle();

    m_deck.deal(m_casino.h);//deal one card to dealer
    int i=0;
    cout << "Casino: ";//print hand of casino with sum of all values
   

    m_casino.h.h.at(0).displayCard();
    cout<<" ";
    
    cout << "[" << to_string(m_casino.h.getTotal()) << "]" << endl;

    
    m_deck.deal(hP.h);//deal two cards to player
    m_deck.deal(hP.h);

    i=0;
    cout << "Player: ";//print hand of player with sum of all values
    hP.h.h.at(0).displayCard();
    cout<<" ";
    hP.h.h.at(1).displayCard();
    cout<<" ";
    
    cout << "[" << to_string(hP.h.getTotal()) << "]" << endl;


    while(hP.isDrawing()){//while user inputs 'y' unless the player busts
        m_deck.deal(hP.h);

        cout << "Player: ";//print hand of player with sum of all values
        int i=0;
        while(i<hP.h.h.size()){
            hP.h.h.at(i).displayCard();
            cout<<" ";
            i++;
        }
      
        cout << "[" << to_string(hP.h.getTotal()) << "]" << endl;
        
        if(hP.isBusted(hP.h)){
           break;//if busted then go to return statement
        }

    }

    if(!hP.isBusted(hP.h)){//if busted then go to return statement
        while(m_casino.isDrawing()){//dealer draws
            m_deck.deal(m_casino.h);
            cout << "Casino: ";//print hand of casino with sum of all values
            int i=0;
        
            while(i<m_casino.h.h.size()){
                 m_casino.h.h.at(i).displayCard();
                cout<<" ";
                i++;
            }
           
            cout << "[" << to_string(m_casino.h.getTotal()) << "]" << endl;
            if(m_casino.isBusted(m_casino.h)){//if busted then go to return statement
               break;
            }

        }
    }

    hP.announce(m_casino.h);//announce results
    return;

}
  

int main()
{
  
    cout << "\tWelcome to the Comp322 Blackjack game!" << endl;
    BlackJackGame game;
 // The main loop of the game
    bool playAgain = true; 
    char answer = 'y'; 
    while (playAgain){
        game.play();
         // Check whether the player would like to play another round
        cout << "Would you like another round? (y/n): "; cin >> answer;
        cout << endl ;
        playAgain = (answer == 'y' ? true : false);
    }
    cout <<"Gave over! Thank you for a great semester and have a wonderful summer!";
    return 0;
}