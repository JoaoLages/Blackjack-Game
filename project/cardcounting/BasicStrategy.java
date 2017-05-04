package cardcounting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import blackjack.Card;
import blackjack.Player;

/**
 * 
 * @author João
 *
 *
 *	edited MB:
 *	.adicionado fclose
 *	.aidicionado private nas var
 *
 *	TODO:  mudar os codigos
 *	TODO: acho que se devia de mudar os inputs doWhatToDO para hands(e as devidas alterações) como fiz nos outros
 */

public class BasicStrategy {
	
	public String[][] Hard = new String[17][10];
	public String[][] Soft = new String[9][10];
	public String[][] Pair = new String[10][10];
	
	public BasicStrategy(){
		try{
			Scanner input1 = new Scanner(new File("src/basicstrategy_hard.txt"));
			for(int i=0; i<17; i++){
				String aux= input1.nextLine();
				String[] aux_words = aux.split("	");
				for(int j=0; j<10; j++){
					Hard[i][j]=aux_words[j];
				}
			}
			input1.close();
			
			Scanner input2 = new Scanner(new File("src/basicstrategy_soft.txt"));
			for(int i=0; i<9; i++){
				String aux= input2.nextLine();
				String[] aux_words = aux.split("	");
				for(int j=0; j<10; j++){
					Soft[i][j]=aux_words[j];
				}
			}
			input2.close();
			
			Scanner input3 = new Scanner(new File("src/basicstrategy_pairs.txt"));
			for(int i=0; i<10; i++){
				String aux= input3.nextLine();
				String[] aux_words = aux.split("	");
				for(int j=0; j<10; j++){
					Pair[i][j]=aux_words[j];
				}
			}
			input3.close();
			
		}catch (FileNotFoundException e) {
			System.out.println("File .txt not found");
		}catch (Exception e){
			System.out.println("Error in the file lecture");
		}
		
	}

	
	
	public String WhatToDo(Player p, int whichHand, Card Dealer, boolean splitted, boolean splittedAces, int numberHands, int bet){
//			int valueplayer ,boolean ispair, int valuedealer, boolean hasAces){
		
		int valueplayer = p.getHandValue(whichHand);
		int valuedealer = Dealer.getValue();
		int numCards = p.getNumberCards(whichHand);
		boolean hasAces = false, ispair=false;
		int numAces=0;
		
		//get all the cards in the hand
		for(int i=0; i<numCards; i++){
			if(p.getHandCard(whichHand, i).isAce()){
				hasAces=true;
				numAces++;
			}
		}
		
		if(p.getHandCard(whichHand, 0).getValue() == p.getHandCard(whichHand, 1).getValue() && numCards==2) ispair=true;
		
		if(ispair){
			switch(Pair[(valueplayer/2)-2][valuedealer-2]){
				case "H": if(!splittedAces){
					return "basic     hit";
				}else{
					return "basic     stand";
				}
				case "Dh": if(p.getNumberCards(whichHand)==2 && p.getHandValue(whichHand)>=9 && p.getHandValue(whichHand)<=11 && p.getBalance()>=bet){
					return "basic     double";
				}else if(!splittedAces){
					return "basic     hit";
				}else{
					return "basic     stand";
				}
				case "P": if(numberHands < 3 && p.getBalance()>=bet){
					return "basic     split";
				}else{
					return "basic     stand";
				}
				
				case "S": return("basic     stand");
				
				default: return("");
			}
		}else if(hasAces && p.getHandValue(whichHand)>12 && numAces>p.getTimesReducedValue(whichHand) ){
			switch(Soft[valueplayer-13][valuedealer-2]){
				case "H": if(!splittedAces){
					return "basic     hit";
				}else{
					return "basic     stand";
				}
				case "Dh": if(p.getNumberCards(whichHand)==2 && p.getHandValue(whichHand)>=9 && p.getHandValue(whichHand)<=11 && p.getBalance()>=bet){
					return "basic     double";
				}else if(!splittedAces){
					return "basic     hit";
				}else{
					return "basic     stand";
				}
					
				case "Ds": if(p.getNumberCards(whichHand)==2 && p.getHandValue(whichHand)>=9 && p.getHandValue(whichHand)<=11 && p.getBalance()>=bet){
					return "basic     double";
				}else{
					return "basic     stand";
				}
					
				case "S": return("basic     stand");
					
				default: return("");
			}
		}else{
			switch(Hard[valueplayer-5][valuedealer-2]){
				case "H": if(!splittedAces){
					return "basic     hit";
				}else{
					return "basic     stand";
				}
					
				case "Dh": if(p.getNumberCards(whichHand)==2 && p.getHandValue(whichHand)>=9 && p.getHandValue(whichHand)<=11 && p.getBalance()>=bet){
					return "basic     double";
				}else{
					return "basic     hit";
				}
					
				case "Rh": if(p.getNumberCards(whichHand)==2 && !splitted){
					return "basic     surrender";
				}else{
					return "basic     hit";
				}
					
				case "S": return("basic     stand");
				
				default: return("");
			}
		}
	}

	
}
