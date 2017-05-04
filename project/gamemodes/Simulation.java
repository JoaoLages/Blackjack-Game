package gamemodes;

import blackjack.Blackjack;
import blackjack.Card;
import blackjack.Shoe;
import cardcounting.AcefiveStrategy;
import cardcounting.HiloStratedy;
import statistics.Statistics;
/**
 * 
 * @author João Lages 75286, Miguel Bordalo 76330, Tiago Cerveira 76932
 * Simulation mode is one way to play BlackJack. It plays by it self based on the counting cards 
 * Advises
 *
 */
public class Simulation extends Blackjack{
	private final static int decksize = 52;
	private static int numberDecks;
	private static float shuffle;
	int s_number;
	String strategy;
	
	
	public Simulation(int minBet, int maxBet, int numberDecks_, int _shuffle, int _s_number, String _strategy){
		super(minBet, maxBet);
		s_number=_s_number;
		strategy=_strategy;
		numberDecks = numberDecks_;
		shuffle = (float) (_shuffle/100.0);//para ter logo em dizima
		

		newShoe();
	}
	
	@Override
	public String WhatToPlay() {
		boolean useBS=false;
		
		if(Nshuffles!=s_number && !over){
			if(ended && player1.getBalance()>=minBet){
				//bet
				if(strategy.equals("HL-AF") || strategy.equals("BS-AF")){ //Ace-five
					if(acefivestrategy.howmuchToBet(prevBet) <= (int) player1.getBalance()){	
						return "b "+Integer.toString(acefivestrategy.howmuchToBet(prevBet)); 
					}else{
						return "b "+Integer.toString((int) player1.getBalance());
					}
				}
				else{
					if(playerLoses==true){ //player Lost previous
						if(prevBet>minBet && (prevBet-minBet)>=minBet){
							return "b "+Integer.toString(prevBet-minBet);
						}else{
							return "b "+Integer.toString(minBet);
						}
					}else if(playerWins==true){
						if(prevBet<maxBet && (prevBet+minBet)<=maxBet){
							return "b "+Integer.toString(prevBet+minBet);
						}else{
							return "b "+Integer.toString(maxBet);
						}
					}else{
							if(prevBet!=0){
								return "b "+Integer.toString(prevBet);
							}else{
								return "b "+Integer.toString(minBet);
							}
					}
				}
			}else if(betted && !dealt){
				return "d";
			}else{
				if(strategy.equals("HL") || strategy.equals("HL-AF")){
					switch(hilostratedy.whatToDo(player1,handInPlay , dealer.getHandCard(0, DealerShownCard),splitted, splittedAces, numberHands, insured, prevBet)){
						case "hi-lo     see in BS": useBS=true;
							break; //será??
						case "hi-lo     insure": return "i";
						case "hi-lo     hit": return "h"; 
						case "hi-lo     split": return "p";
						case "hi-lo     stand": return "s";
						case "hi-lo     double": return "2";
						case "hi-lo     surrender": return "u";
					
						default: return "INVALID";
						//default: return hilostratedy.whatToDo(player1,handInPlay , dealer.getHandCard(0, DealerShownCard), splitted, splittedAces, numberHands);
					}
				}
				if(strategy.equals("BS") || strategy.equals("BS-AF") || useBS ){
            		Card card = dealer.getHandCard(0, DealerShownCard);
						switch(basicstrategy.WhatToDo(player1, handInPlay, card, splitted, splittedAces, numberHands, prevBet)){
							case "basic     hit": return "h"; 
							case "basic     split": return "p";
							case "basic     stand": return "s";
							case "basic     double": return "2";
							case "basic     surrender": return "u";
							default: return "INVALID";
					}
				}
			}
		}else{
        	System.out.println(Statistics.stripHtmlTags(statistics.printStatistics(player1.getBalance())));
			return "q";
		}
		return "INVALID";
	}

	@Override
	protected void newShoe() {
		shoe = new Shoe(numberDecks);
		shoe.shuffle();
		
		System.out.println("shuffling the shoe...");
		Nshuffles++;
		
		acefivestrategy = new AcefiveStrategy(minBet, maxBet);
		hilostratedy = new HiloStratedy(numberDecks*decksize,numberDecks);
		acefivestrategy.resetCount();
		hilostratedy.resetCount(numberDecks*decksize);
			
	}

	@Override
	protected void reshuffleIfNeeded(){
		if (shoe.size() < (1-shuffle)*decksize*numberDecks){
			newShoe();
		}
	}
}
