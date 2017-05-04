package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe {
	List<Card> shoeList = new ArrayList<Card>();
	Deck deck;
	/**
	 * CONSTRUCTOR
	 * @param shoeNumber
	 */
	public Shoe(int shoeNumber){
		int deckSize;
		for (int i = 0; i < shoeNumber; i++){
			deck = new Deck();
			deckSize = deck.size();
			for (int j = 0; j < deckSize; j++){
				Card aux = deck.getCard();
				shoeList.add(aux);
			}			
		}
	}
	
	/**
	 * CONSTRUCTOR
	 * @param cardlist String read from the file to build a shoe from
	 */
		public Shoe(String cardlist){
			String[] singleCards = cardlist.split(" ");
			String value = "";
			String suit = "";
			
			for (int j = 0; j < singleCards.length; j++){
				if(singleCards[j].length()==2){
					 value = singleCards[j].substring(0, 1);
					 suit = singleCards[j].substring(1, 2);
				}else  if(singleCards[j].length()==3){
					 value = singleCards[j].substring(0, 2);
					 suit = singleCards[j].substring(2, 3);			
				}
				Card aux = new Card( value, suit);
				shoeList.add(aux);
			}
		}
		
	public int size(){
		return shoeList.size();
	}
	
	public Card getCard(){
		return shoeList.remove(0);
	}

	public void shuffle(){
		Collections.shuffle(shoeList);
	}
	
	public String toString(){
	    String result = "[";
	    for (int i = 0; i < shoeList.size(); i++) {
	    	if (i == 0){
	    		result += shoeList.get(i);
	    	}
	    	else{
	    		result += ", " + shoeList.get(i);
	    	}
	        
	    }
	    result += "] Size->"+shoeList.size();
	    return result;
	}


}

