package gamemodes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import blackjack.Blackjack;
import blackjack.Shoe;
import cardcounting.AcefiveStrategy;
import cardcounting.HiloStratedy;

/**
 * @author João Lages 75286, Miguel Bordalo 76330, Tiago Cerveira 76932
 * Debug mode is one way to play BlackJack. It imports information of theshoe and the plays from
 * files  
 */
public class Debug extends Blackjack{
	private final static int decksize = 52;
	private static int numberDecks;
	private String shoe_file;
	private String cmd_file;
	private String[] Commands;
	private int commandN=0;


	public Debug(int _minBet, int _maxBet, String shoe_file_, String cmd_file_) {
		super(_minBet, _maxBet);
		shoe_file = shoe_file_;
		cmd_file = cmd_file_;
		
		newShoe();
		ReadComandsFromFile();
	}



	@Override
	protected void newShoe(){
		try{
			Scanner input1 = new Scanner(new File("src/"+shoe_file));
			String cardlist= input1.nextLine();
			input1.close();

		shoe = new Shoe(cardlist);
		
		acefivestrategy = new AcefiveStrategy(minBet, maxBet);
		
		/*Test if the round rounds for a positive number if it rounds to 0 force this value to 1*/
		numberDecks = (Math.round(shoe.size()/decksize) >0) ? Math.round(shoe.size()/decksize) : 1;
		hilostratedy = new HiloStratedy(shoe.size(),numberDecks);
		acefivestrategy.resetCount();
		hilostratedy.resetCount(shoe.size());
		
		}catch (FileNotFoundException e) {
			System.out.println("File .txt not found");
		}catch (Exception e){
			System.out.println("Error in the file lecture");
		}
	}

	private void ReadComandsFromFile(){
		try{
			Scanner input2 = new Scanner(new File("src/"+cmd_file));
			String aux2= input2.nextLine();
			Commands = aux2.split(" ");
			input2.close();
		
		}catch (FileNotFoundException e) {
			System.out.println("File .txt not found");
		}catch (Exception e){
			System.out.println("Error in the file lecture");
		}
	}
	
	
	@Override
	public String WhatToPlay() {
		if(commandN!=Commands.length && !(Commands[commandN].equals("b") && isInteger(Commands[commandN+1])) ){
			System.out.println("-cmd "+Commands[commandN]);
			return Commands[commandN++]; 
		}else if(commandN!=Commands.length && Commands[commandN].equals("b")){
			System.out.println("-cmd "+Commands[commandN]+" "+Commands[commandN+1]);
			commandN+=2;
			return Commands[commandN-2]+" "+Commands[commandN+1-2]; 
		}
		else return "q";
	}
	
	@Override
	protected void reshuffleIfNeeded(){
		if (shoe.size() == 0){
			newShoe();
		}
	}
}	
