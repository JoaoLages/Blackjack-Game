package blackjack;

/**
 * 
 * @author Tiago
 *
 * Creates a card
 * @edited MB visibility to protected
 */
public class Card {
	protected String value, suit;
	protected int pointValue;
	
//	constructor
	public Card(String _value, String _suit){
		value = _value;
		suit = _suit;
		pointValue = tryParse(value);
		if (value.equals("A")){
			pointValue = 11;
		}
	}
	
	private static Integer tryParse(String text) {
		try{
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return 10;
		}
	}

	public String toString(){
		return ""+value+suit;
	}

	public int getValue(){
		return pointValue;
	}
	
	public boolean isAce(){
		boolean result = false;
		
		if (value.equals("A")){
			return true;
		}
		return result;
	}
	
}
