package cardcounting;

import blackjack.Card;
import blackjack.Player;
//import blackjack.Shoe;

/**
 * HiloStratedy
 * @author Miguel
 * This Class implements one type of counting cards
 */
public class HiloStratedy {
	 private int running_count;
	 private float true_count;
	 
	 private  int InShoeNcards;
	 private static int NumberDecks;
	 private int roundedNdecks;

	/** 
	 * Constructor
	 */
	public HiloStratedy(int InitShoeNcards_, int NumberDecks_){
		 running_count=0;
		 true_count=0;
		 InShoeNcards = InitShoeNcards_;
		 NumberDecks = NumberDecks_;
		 roundedNdecks =  Math.round(InitShoeNcards_/NumberDecks_);
	 }
	
	
	/**
	 * Every time there  is a new visible card on the table this should be updated
	 * 	Visible cards appear:
	 * 		-Always that there is an hit
	 * 		-always there is the deal of the cards (except for the hidden card)
	 * 		-right before the dealerPlays
	 * @param c receives a card to update the values of the counting
	 */
	public void updateCount(Card c){
		InShoeNcards--;
		if (c.getValue()>=2 && c.getValue()<=6)  running_count +=1;
		if (c.getValue()>=7 && c.getValue()<=9)  running_count +=0; //Redundant 
		if (c.getValue()==10)  running_count -=1;
		if (c.isAce()) running_count -=1;
		roundedNdecks =  Math.round(InShoeNcards/NumberDecks);
		true_count = (float) running_count/roundedNdecks;
	}
	
	/** 
	 * The counting of card should be reset every time there is a new shuffle
	 */
	public void resetCount(int InitShoeNcards_){
		running_count = 0;
		true_count = 0;
		InShoeNcards=InitShoeNcards_;
		roundedNdecks =  Math.round(InShoeNcards/NumberDecks);
	}
	
	/**
	 * Advises what the user should do according to HiloStratedy counting Strategy 
	 * @param p	PlayerHand to access his cards and his value of its sum
	 * @param whichHand	
	 * @param Dealer
	 * @return
	 * 
	 */
	public String whatToDo(Player p, int whichHand, Card Dealer, boolean splitted, boolean splittedAces, int numberHands, boolean insured, int bet){
		
		int p_sum = p.getHandValue(whichHand);
		Card p1 = p.getHandCard(whichHand, 0);
		Card p2 = p.getHandCard(whichHand, 1);
		
		
		//Illustrious 18 rules

		if(true_count>=3 && Dealer.isAce() && p.getNumberCards(whichHand)==2 && !splitted && !insured) return "hi-lo     insure";
		if(p_sum==16 && Dealer.getValue()==10){
			if(true_count>=0){
				return "hi-lo     stand";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}
			return "hi-lo     stand"; //será??
		}
		/*if(p_sum==15 && Dealer.getValue()==10) return (true_count>=5)?"p":"s"; OverlappedRule*/
		if(p.getNumberCards(whichHand)==2 && p1.getValue()==10 && p2.getValue()==10 && Dealer.getValue()==5){
			if(true_count>=5 && numberHands <3 && p.getBalance()>=bet){
				return "hi-lo     split";
			}else{
				return "hi-lo     stand";
			}
		}
		if(p.getNumberCards(whichHand)==2 && p1.getValue()==10 && p2.getValue()==10 && Dealer.getValue()==6){
			if(true_count>=4 && numberHands <3 && p.getBalance()>=bet){
				return "hi-lo     split";
			}else{
				return "hi-lo     stand";
			}
		}
		if(p_sum==10 && Dealer.getValue()==10 ){
			if(true_count>=4 && p.getNumberCards(whichHand)==2 && p.getHandValue(whichHand)>=9 && p.getHandValue(whichHand)<=11 && p.getBalance()>=bet){
				return "hi-lo     double";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==12 && Dealer.getValue()==3){
			if(true_count>=2){
				return "hi-lo     stand";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==12 && Dealer.getValue()==2){
			if(true_count>=3){
				return "hi-lo     stand";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==11 && Dealer.isAce()){
			if(true_count>=1 && p.getNumberCards(whichHand)==2 && p.getHandValue(whichHand)>=9 && p.getHandValue(whichHand)<=11 && p.getBalance()>=bet){
				return "hi-lo     double";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==9 && Dealer.getValue()==2){
			if(true_count>=1 && p.getNumberCards(whichHand)==2 && p.getHandValue(whichHand)>=9 && p.getHandValue(whichHand)<=11 && p.getBalance()>=bet){
				return "hi-lo     double";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==10 && Dealer.isAce()){
			if(true_count>=4 && p.getNumberCards(whichHand)==2 && p.getHandValue(whichHand)>=9 && p.getHandValue(whichHand)<=11 && p.getBalance()>=bet){
				return "hi-lo     double";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==9 && Dealer.getValue()==7){
			if(true_count>=3 && p.getNumberCards(whichHand)==2 && p.getHandValue(whichHand)>=9 && p.getHandValue(whichHand)<=11 && p.getBalance()>=bet){
				return "hi-lo     double";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==16 && Dealer.getValue()==9){
			if(true_count>=5){
				return "hi-lo     stand";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==13 && Dealer.getValue()==2){
			if(true_count>=-1){
				return "hi-lo     stand";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==12 && Dealer.getValue()==4){
			if(true_count>=0){
				return "hi-lo     stand";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==12 && Dealer.getValue()==5){
			if(true_count>=-2){
				return "hi-lo     stand";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==12 && Dealer.getValue()==6){
			if(true_count>=-1){
				return "hi-lo     stand";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		if(p_sum==13 && Dealer.getValue()==3){
			if(true_count>=-2){
				return "hi-lo     stand";
			}else if(!splittedAces){
				return "hi-lo     hit";
			}else{
				return "hi-lo     stand"; //será?				
			}
		}
		
		//Fab4 rules:
		//TODO: change basicStratedgy to another command
		if(p_sum==14 && Dealer.getValue()==10){
			if(true_count>=3){
				return "hi-lo     surrender";
			}else{
				return "hi-lo     see in BS";
			}
		}
		/* 15vT overlapped rule*/
		if(p_sum==15 && Dealer.getValue()==10){
			if(true_count>=0 && p.getNumberCards(whichHand)==2 && !splitted){
				return "hi-lo     surrender";
			}else{
				return "hi-lo     see in BS";
			}
		}
		if(p_sum==15 && Dealer.isAce()){
			if(true_count>=1 && p.getNumberCards(whichHand)==2 && !splitted){
				return "hi-lo     surrender";
			}else{
				return "hi-lo     see in BS";
			}
		}
			
		
		//overlappedRules
		if(p_sum==15 && Dealer.getValue()==10){
			if(true_count>=0 && true_count<=+3 && p.getNumberCards(whichHand)==2 && !splitted) return "hi-lo     surrender";
			if (true_count>=4) return "hi-lo     stand";
			if (!splittedAces) return "hi-lo     hit";
			return "hi-lo     stand"; //será??
		}
		
		//TODO: change this to do another thing
		return "hi-lo     see in BS";
	}




}
