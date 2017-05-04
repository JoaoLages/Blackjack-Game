package blackjack;

import java.util.ArrayList;
import java.util.List;

import cardcounting.AcefiveStrategy;
import cardcounting.BasicStrategy;
import cardcounting.HiloStratedy;
import statistics.Statistics;

/**
 * 
 * @author Tiago
 * 
 * This Class implements the blackJack Game for any of the 3 modes of play
 * this mode should then extend this abstract class and implement the respective
 * abstract methods
 */
public abstract class Blackjack {
	/* Init variables similar to all game modes */
	protected int maxBet, minBet;
	
	
	protected static Shoe shoe;
	List<Integer> blackjackBet = new ArrayList<Integer>();
	protected int insuranceBet = 0;
	protected int prevBet=0;
	protected int Nshuffles=0;
	
	/*Flags*/
	protected static boolean over = false, betted = false, ended = true, dealt = false, hitted = false,
			splitted = false, insured = false, splittedAces = false, playerWins = false, playerLoses=false, 
			dealersCardIsHidden=false, InvalidCommand=true;
	protected static int numberHands = 0, handInPlay = 0, previousHandInPlay=-1;
	

	protected final static int DealerShownCard =0;
	protected final static int DealerHidenCard =1;
	
	/* Statistics*/
	protected Statistics statistics;
	
	/*CountingCards*/
	public  AcefiveStrategy acefivestrategy;
	public HiloStratedy hilostratedy;
	public BasicStrategy basicstrategy = new BasicStrategy();
	
	/* Players in the Game dealer + player1*/
	protected Player player1 ;
	protected Hand dealer;


	protected static String previousDealer;
	protected static String previousPlayer1;
	protected static int previousDealerValue, previousPlayer1Value;
	

	/**
	 *  BlackJack constructor
	 * @param _minBet
	 * @param _maxBet
	 */
	public Blackjack(int _minBet, int _maxBet){
		minBet = _minBet;
		maxBet = _maxBet;	
	}
	
	/**
	 * 
	 * 
	 * Blackjack Abstract Methods
	 * 
	 * 
	 * 
	 * */
	
	/**
	 * Creates the shoe accordingly to the mode
	 */
	protected abstract void  newShoe();
	
	/**
	 * Check ifthe shoe should be reshuffled accordingly to the mode
	 */
	protected abstract void reshuffleIfNeeded();
	/**
	 * Decides what should the player decide to play next moveaccordingly to the mode
	 */
	public abstract String WhatToPlay();
	
	
	/**
	 * 
	 * 
	 * Blackjack Methods
	 * 
	 * 
	 * 
	 * */
	
	/**
	 * Tests if it is possible for the player to start the play accordingly 
	 * to the betted money
	 * @param player
	 * @param bet
	 * @return
	 */
	public boolean startPlay(Player player, int bet){
		boolean result = false;
		if (bet >= minBet && bet <= maxBet){
			blackjackBet = new ArrayList<Integer>();
			blackjackBet.add(bet);
			insuranceBet = 0;
			if (player.updateBalance(-blackjackBet.get(0))){
				result = true;
			}
			else{
				System.out.println("invalid bet: player is out of cash");
				InvalidCommand=true;
			}
		}
		else{
			System.out.println("invalid bet: bet is out of bounds");
			InvalidCommand=true;
		}
		return result;
	}
	
	/**
	 *  Resets the current play 
	 * @param dealer
	 * @param player
	 */
	public static void endPlay(Hand dealer, Player player){

		previousDealer= dealer.getHand(0);
		previousPlayer1= player.getHand(handInPlay);
		previousDealerValue = dealer.getHandValue(0);
		previousPlayer1Value = player.getHandValue(handInPlay);
		
		previousHandInPlay=handInPlay;
    	dealer.removeHands();
    	player.removeHands();
    	ended = true;
    	betted = false;
    	dealt = false;
    	hitted = false;
    	splitted = false;
    	insured = false;
    	splittedAces = false;
    	playerLoses = false;
    	playerWins  = false;
    	numberHands = 0;
    	handInPlay = 0;
	}
	
	/**
	 * Takes a card from the shoe and gives to the player/Dealer
	 * It checks:
	 * 		If the shoe has to be reshuffled according to the abstract reshuffleIfNeeded function
	 * 		If there are no more cards in the deck, in that case creates a new deck has it created
	 *  at the begging 
	 * @param player
	 * @param whichHand
	 * @return The Taken card so that other methods can see which one it was
	 */
	protected Card TakeCardFromShoe(Hand player, int whichHand){
		Card cardTaken;
		reshuffleIfNeeded();
		cardTaken = player.newCard(shoe, whichHand);
		if( cardTaken == null){
			newShoe();
			
			cardTaken = player.newCard(shoe, whichHand);
		}
		return cardTaken;
	}
	
	/**
	 * Gives to the player and dealer the initial card to start the play It also makes shure the
	 * counting of this cards are updated
	 * @param dealer
	 * @param player
	 */
	public void dealInitialCards(Hand dealer, Player player){
		Card cardTaken;

		cardTaken = TakeCardFromShoe(dealer,0);
		acefivestrategy.updateCount(cardTaken);
		hilostratedy.updateCount(cardTaken);
		/*do not update this card (it's hidden)*/
		cardTaken = TakeCardFromShoe(dealer,0);
	
		cardTaken = TakeCardFromShoe(player,0);
		acefivestrategy.updateCount(cardTaken);
		hilostratedy.updateCount(cardTaken);

		cardTaken = TakeCardFromShoe(player,0);
		acefivestrategy.updateCount(cardTaken);
		hilostratedy.updateCount(cardTaken);
	}

	/**
	 * gives a card to the respective player/dealer
	 * @param player
	 * @param whichHand		Hand in cause
	 */
	protected void hit(Hand player, int whichHand){
		Card cardTaken;
		cardTaken = TakeCardFromShoe(player, whichHand);
		acefivestrategy.updateCount(cardTaken);
		hilostratedy.updateCount(cardTaken);
	}
	

	/**
	 * Check who wins the play
	 * @param dealer
	 * @param player
	 * @param whichHand
	 * @param splitted
	 * @return	a string with the result
	 */
	public String winner(Hand dealer, Player player, int whichHand, boolean splitted){
		String result;
		if (dealer.getHandValue(0) == 21 && dealer.getNumberCards(0) == 2){
			player.updateBalance(2*insuranceBet);
		}
		if (player.getHandValue(whichHand) < dealer.getHandValue(0) && dealer.getHandValue(0) <= 21){
			if (dealer.getHandValue(0) == 21 && dealer.getNumberCards(0) == 2){
				System.out.println("blackjack!");
				statistics.incrementDealerBlackJack();
			}
			result = "player loses";
			statistics.incrementPlayerLoses();
		}
		else if ((player.getHandValue(whichHand) == dealer.getHandValue(0)) && dealer.getHandValue(0) < 21){
			result = "player pushes";
			statistics.incrementPlayerPushes();
			player.updateBalance(blackjackBet.get(whichHand));
		}
		else if (player.getHandValue(whichHand) == 21 && dealer.getHandValue(0) == 21){
			if (dealer.getNumberCards(0) == 2 || player.getNumberCards(whichHand) == 2 && !splitted){
				System.out.println("blackjack!");
				statistics.incrementPlayerBlackJack();
			}
			if(player.getNumberCards(whichHand) > dealer.getNumberCards(0) && dealer.getNumberCards(0) == 2){
				result = "player loses";
				statistics.incrementPlayerLoses();
	    	}
			else if (player.getNumberCards(whichHand) < dealer.getNumberCards(0) && player.getNumberCards(whichHand) == 2){
				result = "player wins";
				statistics.incrementPlayerWins();
				if (splitted){
					player.updateBalance((float) (2*blackjackBet.get(whichHand)));
				}
				else{
					player.updateBalance((float) (2*blackjackBet.get(whichHand)+blackjackBet.get(whichHand)*1.0/2));
				}
			}
	    	else{
	    		result = "player pushes";
	    		statistics.incrementPlayerPushes();
	    		player.updateBalance(blackjackBet.get(whichHand));
	    	}
		}
		else if (player.getHandValue(whichHand) <= 21){
			if (player.getHandValue(whichHand) == 21 && player.getNumberCards(whichHand) == 2){
				
				if (splitted){
					statistics.incrementPlayerWins();
					player.updateBalance((float) (2*blackjackBet.get(whichHand)));
				}
				else{
					System.out.println("blackjack!");
					statistics.incrementPlayerBlackJack();
					player.updateBalance((float) (2*blackjackBet.get(whichHand)+blackjackBet.get(whichHand)*1.0/2));
				}
			}
			else{
				player.updateBalance(2*blackjackBet.get(whichHand));
			}
			result = "player wins";
			statistics.incrementPlayerWins();
		}
		else{
			result = "player loses";
			statistics.incrementPlayerLoses();
		}
		return result;
	}
	
	
	public static boolean bust(Hand player, int whichHand){
		boolean result = false;
		if (player.getHandValue(whichHand) > 21){
			result = true;
		}
		return result;
	}
	
	public boolean blackj(Hand player, int whichHand){
		boolean result = false;
		if (player.getHandValue(whichHand) == 21){
			result = true;
		}
		return result;
	}
	
	public void updateBet(int bet, int whichHand){
		blackjackBet.set(whichHand, blackjackBet.get(whichHand)+bet);
	}
	
	public void insuranceBet(int bet){
		insuranceBet = bet;
	}
	
	public void split(Player player, int whichHand, int bet){
		Card splitCard = player.removeLastCard(whichHand);
		player.newSplittedHand(splitCard, whichHand);
		blackjackBet.add(bet);
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10); //10 indexes are enough
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

/**
 * Reveals for the first time the dealer second  card
 * once newcard is reveled counting cards should be updated 
 * @param dealer
 */
	public void RevealDealer2Card(Hand dealer){		
		Card dealerHidenCard = dealer.getHandCard(0, DealerHidenCard);
		/*update counting cards*/
		acefivestrategy.updateCount(dealerHidenCard);
		hilostratedy.updateCount(dealerHidenCard);
		dealersCardIsHidden = false;
	}
	
	
		
	/**
	 *  Plays for the dealer after all the players have decided to stand
	 * @param game
	 * @param dealer
	 * editedMB
	 */
	public boolean dealerPlays(Hand dealer){

    	while(dealer.getHandValue(0) < 17){
    		System.out.println("dealer hits");
    		hit(dealer, 0);
    		System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
    	}
    	if (bust(dealer, 0)){
    		System.out.println("dealer busts");
    	}
    	else{
    		System.out.println("dealer stands");
    	}
    	return true;
	}
	
	
	
	/**
	 *  This function start a game and plays according to the interactive gamemode. 
	 *  It respects all the rules. Prints to the terminal what's happening and waits for the user to introduce
	 *  in the terminal decisions and play accordingly
	 *   
	 * @param balance
	 */
	public void play(int initBalance){
		int i=0;
		
		
//		create player, dealer and a new game 
		player1 = new Player(initBalance);
		dealer = new Hand();
		statistics = new Statistics(initBalance);

		
		
//		infinite game loop
		while(!over){
			//TODO put this in the interaction 
//			function to receive commands from command line
			if(ended && (int)player1.getBalance()<minBet){
	        	over=true;
	        }
			String PlayerChoice = WhatToPlay();
//			function to split the command received in an array of separate words
			String[] words = PlayerChoice.split(" ");
			//search for input bugs
			if(words[0].equals("b")){
				if(words.length>1){
					if(!isInteger(words[1]) || words.length>2) words[0]="INVALID"; //invalid command	
				}
			}else if(words.length>1){
				words[0]="INVALID";
			}
	        switch (words[0]){
	            case "b":// bet
	            	if (ended){
	            		InvalidCommand=false;
	            		ended = false;
//	            		case when a bet value was provided
		            	if (words.length > 1){
//		            		tests if is able to start game (bet is within bounds and the player has sufficient funds)
		            		if (startPlay(player1, Integer.parseInt(words[1]))){
		            			prevBet = Integer.parseInt(words[1]);
		            			System.out.println("player is betting "+prevBet);
		            			betted = true;
		            		}
		            		else{
		            			ended = true;
		            		}
		            	}
		            	else{
//		            		case where no bet value was provided but a previous bet was made
		            		if (prevBet != 0){
//			            		tests if is able to start game (bet is within bounds and the player has sufficient funds)
		            			if (startPlay(player1, prevBet)){
		            				System.out.println("player is betting "+prevBet);
		            				betted = true;
		            			}
		            			else{
		            				ended = true;
		            			}
		            		}
//		            		case where no bet value was provided and no previous bet was made
		            		else{
//			            		tests if is able to start game (bet is within bounds and the player has sufficient funds)
		            			if (startPlay(player1, minBet)){
		            				System.out.println("player is betting "+minBet);
		            				prevBet = minBet;
		            				betted = true;
		            			}
		            			else{
		            				ended = true;
		            			}
		            		}
		            	}
	            	}
	            	else{
	            		System.out.println("b: invalid command");
	            		InvalidCommand=true;
	            	}
	            	break;
	            	
	            case "$":// current balance
	            	InvalidCommand=false;
	            	System.out.println("player current balance is "+player1.getBalance());	            	
	            	break;
	            
	            case "d":// deal
	            	if (betted && !dealt){
	            		InvalidCommand=false;
//	            		deals 2 cards to the dealer and 2 cards to the player
	            		dealInitialCards(dealer, player1);
	            		dealersCardIsHidden =true;
	            		System.out.println("dealer's hand "+dealer.getInitialHand());
	            		System.out.println("player's hand "+player1.getHand(handInPlay)+" ("+player1.getHandValue(handInPlay)+")");
	            		dealt = true;
	            	}
	            	else{
	            		System.out.println("d: invalid command");
	            		InvalidCommand=true;
	            	}
	            	break;
	            	
	            case "h":// hit
	            	if (betted && dealt && !splittedAces){
	            		InvalidCommand=false;
		            	System.out.println("player hits");
//		            	deals a card to the hand in play of the player
		            	hit(player1, handInPlay);
		            	if (numberHands > 0){
			            	System.out.println("player's hand ["+(handInPlay+1)+"] "+player1.getHand(handInPlay)+" ("+player1.getHandValue(handInPlay)+")");
		            	}
		            	else{
		            		System.out.println("player's hand "+player1.getHand(handInPlay)+" ("+player1.getHandValue(handInPlay)+")");
		            	}
//		            	tests if the hand in play has a value over 21, in this case terminates the hand and plays the next
		            	if (bust(player1, handInPlay)){
//		            		case when split was used
		            		if (numberHands > 0){
		            			System.out.println("player busts ["+(handInPlay+1)+"]");
//		            			in this case all available hands have been played
		            			if (numberHands == handInPlay){
		            				if(dealersCardIsHidden){
		            					RevealDealer2Card(dealer);
		            				}
			            			System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
			            			if(!dealerPlays( dealer)) break;
			            			for (i = 0; i <= numberHands; i++){
		    		            		System.out.println(""+winner(dealer, player1, i, splitted)+" ["+(i+1)+"] and his current balance is "+player1.getBalance());
		            				}
		            				endPlay(dealer, player1);
		            			}
//		            			start playing next hand (put in function? playNextHand();)
		            			else{
		            				handInPlay++;
		            				hitted = false;
		            				System.out.println("playing hand #"+(handInPlay+1));
					            	System.out.println("player's hand ["+(handInPlay+1)+"] "+player1.getHand(handInPlay)+" ("+player1.getHandValue(handInPlay)+")");		
		            			}
		            		}
//		            		case when split was not used
		            		else{
			            		System.out.println("player busts");
			            		if(dealersCardIsHidden){
	            					RevealDealer2Card(dealer);
	            				}
			            		System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
			            		System.out.println(""+winner(dealer, player1, handInPlay, splitted)+" and his current balance is "+player1.getBalance());
				            	endPlay(dealer, player1);
		            		}
		            	}
		            	else{
		            		hitted = true;
		            	}
	            	}
	            	else{
	            		System.out.println("h: invalid command");
	            		InvalidCommand=true;
	            	}
	            	break;
	            	
	            case "s":// stand
	            	if (betted && dealt){
	            		InvalidCommand=false;
	            		System.out.println("player stands");
//	            		case when split was used
	            		if (numberHands > 0){
//		            		case when there are not more hands to play
		            		if (handInPlay == numberHands){
		            			if(dealersCardIsHidden){
	            					RevealDealer2Card(dealer);
	            				}
		            			System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
		            			if(!dealerPlays( dealer)) break;
	            				for (i = 0; i <= numberHands; i++){
	    		            		System.out.println(""+winner(dealer, player1, i, splitted)+" ["+(i+1)+"] and his current balance is "+player1.getBalance());
	            				}
	            				endPlay(dealer, player1);
		            		}
//		            		start playing next hand (put in function? playNextHand();)
		            		else{
		            			handInPlay++;
		            			System.out.println("playing hand #"+(handInPlay+1));
				            	System.out.println("player's hand ["+(handInPlay+1)+"] "+player1.getHand(handInPlay)+" ("+player1.getHandValue(handInPlay)+")");
		            			hitted = false;
		            		}
	            		}
//	            		no split was used
	            		else{
	            			if(dealersCardIsHidden){
            					RevealDealer2Card(dealer);
            				}
	            			System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
	            			if(!dealerPlays( dealer)) break;
			            	System.out.println(""+winner(dealer, player1, handInPlay, splitted)+" and his current balance is "+player1.getBalance());
			            	endPlay(dealer, player1);
	            		}
	            	}
	            	else{
	            		System.out.println("s: invalid command");
	            		InvalidCommand=true;
	            	}
	            	break;
	            	
	            case "i":// insurance
	            	if (betted && dealt && !hitted && !splitted && !insured){
	            		if (dealer.showsAce()){
	            			InvalidCommand=false;
//	            			if the player does not have a blackjack hand
	            			if (!blackj(player1, handInPlay)){
		            			System.out.println("player is insuring");
		            			player1.updateBalance(-prevBet);
		            			insuranceBet(prevBet);
		            			//TODO: ver insurence
		            			if (blackj(dealer, 0)){
		            				if(dealersCardIsHidden){
		            					RevealDealer2Card(dealer);
		            				}
					            	System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
					            	winner(dealer, player1, handInPlay, splitted);
					            	System.out.println("player's current balance is "+player1.getBalance());
					            	endPlay(dealer, player1);
		            			}
		            			else{
		            				insured = true;
		            			}
	            			}
//	            			if the player insured a blackjack hand
	            			else{
	            				System.out.println("player is insuring a blackjack hand");
	            				if(dealersCardIsHidden){
	            					RevealDealer2Card(dealer);
	            				}
	            				System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
	            				player1.updateBalance(2*prevBet);
	            				System.out.println("player's current balance is "+player1.getBalance());
				            	endPlay(dealer, player1);
	            			}
	            		}
	            		else{
	            			System.out.println("i: invalid command");
	            			InvalidCommand=true;
	            		}
	            	}
	            	else{
	            		System.out.println("i: invalid command");
	            		InvalidCommand=true;
	            	}
	            	break;
	            
	            case "u":// surrender
	            	if (betted && dealt /*&& !hitted*/ && !splitted){
	            		InvalidCommand=false;
	            		System.out.println("player is surrendering");
	            		statistics.incrementPlayerLoses();
	            		if(dealersCardIsHidden){
        					RevealDealer2Card(dealer);
        				}
		            	System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
		            	if (blackj(dealer, 0)){
		            		System.out.println("blackjack!");
		            		statistics.incrementDealerBlackJack();
		            	}
		            	player1.updateBalance((float) (prevBet*1.0/2));
		            	System.out.println("player's current balance is "+player1.getBalance());
		            	endPlay(dealer, player1);
	            	}
	            	else{
	            		System.out.println("u: invalid command");
	            		InvalidCommand=true;
	            	}
	            	break;
	            	
	            case "p":// splitting
	            	if (betted && dealt && !hitted){
	            		if (numberHands < 3){
	            			if (player1.updateBalance(-prevBet)){
			            		if (player1.splitPossible(handInPlay)){
			            			InvalidCommand=false;
			            			if (player1.splittingAces(handInPlay)){
			            				splittedAces = true;
			            			}
			            			split(player1, handInPlay, prevBet);
			            			numberHands++;
	//			            			hit each hand one card, then show first hand splitted
			            			hit(player1, handInPlay);
			            			hit(player1, handInPlay+1);
			            			System.out.println("playing hand #"+(handInPlay+1));
					            	System.out.println("player's hand ["+(handInPlay+1)+"] "+player1.getHand(handInPlay)+" ("+player1.getHandValue(handInPlay)+")");       			
			            			splitted = true;
			            		}
			            		else{
				            		System.out.println("p: invalid command");
				            		InvalidCommand=true;
			            		}
	            			}else{
			            		System.out.println("p: invalid command");
			            		InvalidCommand=true;
	            			}
	            		}else{
		            		System.out.println("p: invalid command");
		            		InvalidCommand=true;
	            		}
	            	}
	            	else{
	            		System.out.println("p: invalid command");
	            		InvalidCommand=true;
	            	}
	            	break;
	            	
	            case "2":// double
	            	if (betted && dealt && !hitted && !splittedAces){
	            		if (player1.getHandValue(handInPlay) >= 9 && player1.getHandValue(handInPlay) <= 11){
	            			if (player1.updateBalance(-prevBet)){
	            				InvalidCommand=false;
		            			updateBet(prevBet, handInPlay);
				            	hit(player1, handInPlay);
				            	System.out.println("player's hand "+player1.getHand(handInPlay)+" ("+player1.getHandValue(handInPlay)+")");
				            	if (numberHands > 0){
				            		if (numberHands == handInPlay){
				            			if(dealersCardIsHidden){
			            					RevealDealer2Card(dealer);
			            				}
				            			System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
				            			if(!dealerPlays( dealer)) break;
			            				for (i = 0; i <= numberHands; i++){
			    		            		System.out.println(""+winner(dealer, player1, i, splitted)+" ["+(i+1)+"] and his current balance is "+player1.getBalance());
			            				}
			            				endPlay(dealer, player1);
				            		}
				            		else{
				            			handInPlay++;
				            			System.out.println("playing hand #"+(handInPlay+1));
						            	System.out.println("player's hand ["+(handInPlay+1)+"] "+player1.getHand(handInPlay)+" ("+player1.getHandValue(handInPlay)+")");
				            			hitted = false;
				            		}
				            	}
				            	else{
				            		if(dealersCardIsHidden){
		            					RevealDealer2Card(dealer);
		            				}
					            	System.out.println("dealer's hand "+dealer.getHand(0)+" ("+dealer.getHandValue(0)+")");
			            			if(!dealerPlays( dealer)) break;
					            	System.out.println(""+winner(dealer, player1, handInPlay, splitted)+" and his current balance is "+player1.getBalance());
					            	endPlay(dealer, player1);
				            	}
	            			}else{
		            			System.out.println("2: invalid command");
		            			InvalidCommand=true;
	            			}
	            		}
	            		else{
	            			System.out.println("2: invalid command");
	            			InvalidCommand=true;
	            		}
	            	}
	            	else{
	            		System.out.println("2: invalid command");
	            		InvalidCommand=true;
	            	}
	            	break;
	            	
	            case "ad":// advice
	            	InvalidCommand=false;

	            	if(ended && !betted){
	            		System.out.println("ace-five	bet " + acefivestrategy.howmuchToBet(prevBet));
//	            		acefivestrategy.howmuchToBet();
	            	}else if(betted && dealt){
	            		
	            			//Basic Strategy
	            		Card card = dealer.getHandCard(0, DealerShownCard);
	            		System.out.println(basicstrategy.WhatToDo(player1, handInPlay,card, splitted, splittedAces, numberHands, prevBet));
	            		System.out.println(hilostratedy.whatToDo(player1,handInPlay , dealer.getHandCard(0, DealerShownCard), splitted, splittedAces, numberHands, insured, prevBet));
	            	}else{
	            		System.out.println("Sorry, but I can't give you an advice yet");
	            	}
	            	break;
	            
	            case "st":// statistics
	            	InvalidCommand=false;
	            	System.out.println(Statistics.stripHtmlTags(statistics.printStatistics(player1.getBalance())));
	            	break;
	            	
	            case "q":// quit
	            	System.out.println("Game over!");
	            	over = true;
	            	break;
	            
	            default:
	            	System.out.println(""+words[0]+": invalid command");
	            	InvalidCommand=true;
	            	break;
	        }
		}
	}

}
