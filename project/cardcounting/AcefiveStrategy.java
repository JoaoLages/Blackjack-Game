package cardcounting;

import blackjack.Card;

/**
 * AcefiveStrategy
 * @author Miguel
 * This Class implements one type of counting cards based on number 5's and Ace's shown 
 *
 */
public class AcefiveStrategy {
	private int minbet, maxbet,  count;

	/**
	 *  Constructor
	 * @param minbet_
	 * @param maxbet_
	 */
	public AcefiveStrategy(int minbet_,int maxbet_){
		minbet = minbet_;
		maxbet = maxbet_;
		count=0;
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
		if (c.getValue()==5 ){
			count +=1;
		}
		if (c.isAce()) {
			count -=1;
		}
	}
	
	
	/**
	 * 
	 * @return int how much money should be the bet in the next play
	 */
	public int howmuchToBet(int prevBet){
		if(count>=2 && (prevBet*2)<=maxbet) return prevBet*2;
		else if(count>=2) return maxbet;
		else  return minbet;
	}
	
	/**
	 * The counting of card should be reset everytime there is a new shuffle
	 */
	public void resetCount(){
		count = 0;
	}

}
