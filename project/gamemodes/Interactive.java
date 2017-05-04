package gamemodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;

import Swing.Graphical_Interface;
import blackjack.Blackjack;
import blackjack.Card;
import blackjack.Shoe;
import cardcounting.AcefiveStrategy;
import cardcounting.HiloStratedy;

/**
 * 
* @author João Lages 75286, Miguel Bordalo 76330, Tiago Cerveira 76932
 *
 * Interactive mode is one way to play BlackJack. The play decides through the cmd line
 * what he what's the next move to be
 */
public class Interactive extends Blackjack{
	private final static int decksize = 52;
	private static int numberDecks;
	private static float shuffle;
	private float InitialBalance = 0;
	private boolean swing;
	
	public Interactive(int _minBet, int _maxBet, int _numberDecks, int _shuffle, boolean _swing) {
		super(_minBet, _maxBet);
		numberDecks = _numberDecks;
		shuffle = (float) (_shuffle/100.0);//para ter logo em dizima
		
		newShoe();
		
		swing=_swing;
		if(swing){
			Thread Swing = new Graphical_Interface();
		    Swing.start();
		}

	}

	@Override
public String WhatToPlay() {
				
		if(!swing){
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		    String line = null;
		    try {
		        line = reader.readLine();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return line;	
		}else{
			//UPDATE INTERFACE
			if(Graphical_Interface.button.equals("null")){
				InitialBalance=player1.getBalance();
			}
				//chips Image
			if(player1.getBalance()<(InitialBalance/2)){
				Graphical_Interface.chips.setIcon(new ImageIcon("src/Icons/chips-1.png")); 
			}else if(player1.getBalance()<(InitialBalance)){
				Graphical_Interface.chips.setIcon(new ImageIcon("src/Icons/chips-2.png")); 
			}else if(player1.getBalance()>(InitialBalance*2)){
				Graphical_Interface.chips.setIcon(new ImageIcon("src/Icons/chips-4.png")); 
			}else{
				Graphical_Interface.chips.setIcon(new ImageIcon("src/Icons/chips-3.png")); 
			}
				//invalid
			if(InvalidCommand && !Graphical_Interface.button.equals("null")){
				Graphical_Interface.setText("Invalid command, try again!", Graphical_Interface.Invalid);
			}else{
				Graphical_Interface.setText(" ", Graphical_Interface.Invalid);
			}
				//ad, st and $
			if(!Graphical_Interface.button.equals("ad") && !Graphical_Interface.button.equals("st") && !Graphical_Interface.button.equals("$")) Graphical_Interface.setText(" ", Graphical_Interface.textWindow);
				//d
			if(Graphical_Interface.button.equals("d") && !InvalidCommand){
				Graphical_Interface.setCards(player1.getHand(handInPlay), player1.getHandValue(handInPlay), true);
				Graphical_Interface.setCards(dealer.getInitialHand(), 0, false);
			}
				//s
			if(Graphical_Interface.button.equals("s") && !InvalidCommand){
				Graphical_Interface.setCards(previousPlayer1, previousPlayer1Value, true);
				Graphical_Interface.setCards(previousDealer, previousDealerValue, false);
				if( (previousDealerValue<previousPlayer1Value && previousPlayer1Value<22) || (previousPlayer1Value<22 && previousDealerValue>21) ){
					Graphical_Interface.player_Won_Lost_Pushed(1);//won
				}else if(previousDealerValue==previousPlayer1Value && previousPlayer1Value<22){
					Graphical_Interface.player_Won_Lost_Pushed(0);//pushed
				}else{
					Graphical_Interface.player_Won_Lost_Pushed(-1);//lost
				}
			}
				//p
			if(splitted && !InvalidCommand){
				Graphical_Interface.setCards(player1.getHand(handInPlay), player1.getHandValue(handInPlay), true);
				
			}
				//h
			if(Graphical_Interface.button.equals("h") && !InvalidCommand){
				
				if(ended){
					//game ended
					Graphical_Interface.setCards(previousPlayer1, previousPlayer1Value, true);
					Graphical_Interface.setCards(previousDealer, previousDealerValue, false);
					if(previousDealerValue<previousPlayer1Value && previousPlayer1Value<22){
						Graphical_Interface.player_Won_Lost_Pushed(1);//won
					}else if(previousDealerValue==previousPlayer1Value && previousPlayer1Value<22){
						Graphical_Interface.player_Won_Lost_Pushed(0);//pushed
					}else{
						Graphical_Interface.player_Won_Lost_Pushed(-1);//lost
					}
				}else{
					Graphical_Interface.setCards(player1.getHand(handInPlay), player1.getHandValue(handInPlay), true);
				}
			}
				//2
			if(Graphical_Interface.button.equals("2") && !InvalidCommand){

				if(ended){
					//game ended
					Graphical_Interface.setCards(previousPlayer1, previousPlayer1Value, true);
					Graphical_Interface.setCards(previousDealer, previousDealerValue, false);
					if(previousDealerValue<previousPlayer1Value && previousPlayer1Value<22){
						Graphical_Interface.player_Won_Lost_Pushed(1);//won
					}else if(previousDealerValue==previousPlayer1Value && previousPlayer1Value<22){
						Graphical_Interface.player_Won_Lost_Pushed(0);//pushed
					}else{
						Graphical_Interface.player_Won_Lost_Pushed(-1);//lost
					}
				}else{
					Graphical_Interface.setCards(player1.getHand(handInPlay), player1.getHandValue(handInPlay), true);
				}
			}
				//u
			if(Graphical_Interface.button.equals("u") && !InvalidCommand){
				Graphical_Interface.player_Won_Lost_Pushed(-2);
			}
			//wait to receive new command
			while(!Graphical_Interface.buttonPressed ){
				
			}
			switch(Graphical_Interface.button){
				case "b": return Graphical_Interface.button+" "+Graphical_Interface.bet;
				case "$": Graphical_Interface.setText("<html>Your&nbsp;balance&nbsp;is:<br>"+player1.getBalance()+"</html>", Graphical_Interface.textWindow);
				return Graphical_Interface.button;
				case "st": Graphical_Interface.setText(statistics.printStatistics(player1.getBalance()).toString(), Graphical_Interface.textWindow);
				return Graphical_Interface.button;
				case "ad": if(ended && !betted){
					Graphical_Interface.setText("     ace-five bet "+acefivestrategy.howmuchToBet(prevBet), Graphical_Interface.textWindow);
            	}else if(betted && dealt){
            		Card card = dealer.getHandCard(0, DealerShownCard);
					Graphical_Interface.setText("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+basicstrategy.WhatToDo(player1, handInPlay,card, splitted, splittedAces, numberHands, prevBet)+"<br>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+hilostratedy.whatToDo(player1,handInPlay , dealer.getHandCard(0, DealerShownCard), splitted, splittedAces, numberHands, insured, prevBet)+"</html>", Graphical_Interface.textWindow);
            	}else{
					Graphical_Interface.setText("     NO ADVICE   ", Graphical_Interface.textWindow);
            	}
				return Graphical_Interface.button;
				default: return Graphical_Interface.button;
			}
		}
		
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
			hilostratedy.resetCount(shoe.size());
	}

	@Override
	protected void reshuffleIfNeeded(){
		if (shoe.size() < (1-shuffle)*decksize*numberDecks){
			newShoe();
		}
	}

}
