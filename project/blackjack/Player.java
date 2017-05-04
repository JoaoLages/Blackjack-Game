package blackjack;

import java.util.ArrayList;

public class Player extends Hand{
	float balance;
	
	public Player(int _balance){
		balance = _balance;
	}
	
	
	public boolean updateBalance(float money){
		boolean result = false;
		balance += money;
		if (balance >= 0){
			result = true;
		}
		else{
			balance -= money;
		}
		return result;
	}
	
	public float getBalance(){
		return balance;
	}

	
	public boolean splitPossible(int whichHand){
		boolean result = false;
		if (handsInHand.get(whichHand).get(0).getValue() == handsInHand.get(whichHand).get(1).getValue()){
			result = true;
		}
		return result;
	}
	
	public void newSplittedHand(Card newCard, int whichHand){
		handsInHand.add(whichHand+1, new ArrayList<Card>());
		handsInHand.get(whichHand+1).add(newCard);
		handValue.add(whichHand+1, newCard.getValue());
		numberCards.add(whichHand+1,1);
		timesReducedValue.add(whichHand+1, 0);
	}
	
	public boolean splittingAces(int whichHand){
		boolean result = false;
		if(handsInHand.get(whichHand).get(0).isAce() && handsInHand.get(whichHand).get(1).isAce()){
			result = true;
		}
		
		return result;
	}
}
