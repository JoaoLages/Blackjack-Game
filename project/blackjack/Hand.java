package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	List<ArrayList<Card>> handsInHand = new ArrayList<ArrayList<Card>>();
	List<Integer> handValue = new ArrayList<Integer>();
	List<Integer> numberCards = new ArrayList<Integer>();
	List<Integer> timesReducedValue = new ArrayList<Integer>();
	
	public Hand(){
		handsInHand.add(new ArrayList<Card>());
		handValue.add(0);
		numberCards.add(0);
		timesReducedValue.add(0);
	}
	
	
//	public void newSplittedHand(Card newCard, int whichHand){
//		handsInHand.add(whichHand+1, new ArrayList<Card>());
//		handsInHand.get(whichHand+1).add(newCard);
//		handValue.add(whichHand+1, newCard.getValue());
//		numberCards.add(whichHand+1,1);
//		timesReducedValue.add(whichHand+1, 0);
//	}
	
	public Card removeLastCard(int whichHand){
		Card cardRemoved = handsInHand.get(whichHand).remove(1);
		if (cardRemoved.isAce() && handsInHand.get(whichHand).get(0).isAce()){
			handValue.set(whichHand, handValue.get(whichHand)-1);
			timesReducedValue.set(whichHand, timesReducedValue.get(whichHand)-1);
		}
		else{
			handValue.set(whichHand, handValue.get(whichHand)-cardRemoved.getValue());
		}
		numberCards.set(whichHand, numberCards.get(whichHand)-1);
		return cardRemoved;
	}
	
	/**
	 * @edited MB: is was a void now returns the taken card
	 * @param thisShoe
	 * @param whichHand
	 * @return
	 */
	public Card newCard(Shoe thisShoe, int whichHand){
		try{
			Card cardTaken = thisShoe.getCard();
			handsInHand.get(whichHand).add(cardTaken);
			handValue.set(whichHand, handValue.get(whichHand)+cardTaken.getValue()); 
			reduceHandValue(whichHand);
			numberCards.set(whichHand, numberCards.get(whichHand)+1);
			
			return cardTaken;
		}catch (Exception e){
			return null;
		}
	}
	
	private void reduceHandValue(int whichHand){
		int aces = 0;
		
		if (handValue.get(whichHand) > 21){
			for (int i = 0; i < handsInHand.get(whichHand).size(); i++){
				if (handsInHand.get(whichHand).get(i).isAce()){
					aces ++;
				}
			}
		}
		if (aces > timesReducedValue.get(whichHand)){
			handValue.set(whichHand, handValue.get(whichHand)-10);
			timesReducedValue.set(whichHand, timesReducedValue.get(whichHand)+1);
		}
	}
	
	public int getHandValue(int whichHand){
		return handValue.get(whichHand);
	}
	
	public String getInitialHand(){
		return ""+handsInHand.get(0).get(0)+" X";
	}
	
	public String getHand(int whichHand){
	    String result = "";
	    for (int i = 0; i < handsInHand.get(whichHand).size(); i++) {
	    	if (i == 0){
	    		result += handsInHand.get(whichHand).get(i);
	    	}
	    	else {
	    		result += " " + handsInHand.get(whichHand).get(i);
	    	}
	    }
	    return result;
	}
	
	
	/**
	 *  
	 * @param whichHand	
	 * @param whichCard
	 * @return the card selected from the whichHand Hand and the whichCard position.
	 * TODO: if there is an error avoid it! else statement 
	 */
	public Card getHandCard(int whichHand, int whichCard){
		if ( handsInHand.get(whichHand).size() > whichCard) return handsInHand.get(whichHand).get(whichCard);
		else return null;
		
	}
	
	public int getTimesReducedValue(int whichHand){
		return timesReducedValue.get(whichHand);
	}	
	
	public int getNumberCards(int whichHand){
		return numberCards.get(whichHand);
	}
	
	public void removeHands(){
		handsInHand = new ArrayList<ArrayList<Card>>();
		handsInHand.add(new ArrayList<Card>());
		handValue = new ArrayList<Integer>();
		handValue.add(0);
		numberCards = new ArrayList<Integer>();
		numberCards.add(0);
		timesReducedValue = new ArrayList<Integer>();
		timesReducedValue.add(0);
	}
	
	public boolean showsAce(){
		boolean result = false;
		if (handsInHand.get(0).get(0).isAce()){
			result = true;
		}
		return result;
	}
}
