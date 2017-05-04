package blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Tiago
 *
 * Creates a deck
 * @edited MB visibility to protected
 */
public class Deck {
	protected List<Card> deckList = new ArrayList<Card>();
	
	public Deck(){

		deckList.add(new Card("2", "C"));
		deckList.add(new Card("3", "C"));
		deckList.add(new Card("4", "C"));
		deckList.add(new Card("5", "C"));
		deckList.add(new Card("6", "C"));
		deckList.add(new Card("7", "C"));
		deckList.add(new Card("8", "C"));
		deckList.add(new Card("9", "C"));
		deckList.add(new Card("10", "C"));
		deckList.add(new Card("J", "C"));
		deckList.add(new Card("Q", "C"));
		deckList.add(new Card("K", "C"));
		deckList.add(new Card("A", "C"));
		deckList.add(new Card("2", "H"));
		deckList.add(new Card("3", "H"));
		deckList.add(new Card("4", "H"));
		deckList.add(new Card("5", "H"));
		deckList.add(new Card("6", "H"));
		deckList.add(new Card("7", "H"));
		deckList.add(new Card("8", "H"));
		deckList.add(new Card("9", "H"));
		deckList.add(new Card("10", "H"));
		deckList.add(new Card("J", "H"));
		deckList.add(new Card("Q", "H"));
		deckList.add(new Card("K", "H"));
		deckList.add(new Card("A", "H"));
		deckList.add(new Card("2", "S"));
		deckList.add(new Card("3", "S"));
		deckList.add(new Card("4", "S"));
		deckList.add(new Card("5", "S"));
		deckList.add(new Card("6", "S"));
		deckList.add(new Card("7", "S"));
		deckList.add(new Card("8", "S"));
		deckList.add(new Card("9", "S"));
		deckList.add(new Card("10", "S"));
		deckList.add(new Card("J", "S"));
		deckList.add(new Card("Q", "S"));
		deckList.add(new Card("K", "S"));
		deckList.add(new Card("A", "S"));
		deckList.add(new Card("2", "D"));
		deckList.add(new Card("3", "D"));
		deckList.add(new Card("4", "D"));
		deckList.add(new Card("5", "D"));
		deckList.add(new Card("6", "D"));
		deckList.add(new Card("7", "D"));
		deckList.add(new Card("8", "D"));
		deckList.add(new Card("9", "D"));
		deckList.add(new Card("10", "D"));
		deckList.add(new Card("J", "D"));
		deckList.add(new Card("Q", "D"));
		deckList.add(new Card("K", "D"));
		deckList.add(new Card("A", "D"));		
	}

	public Card getCard(){
		return deckList.remove(0);
	}

	public int size(){
		return deckList.size();
	}
	
}
