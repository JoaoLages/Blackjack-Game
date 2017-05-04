package main;

import gamemodes.Debug;
import gamemodes.Interactive;
import gamemodes.Simulation;

/**
 * 
 * @author João Lages 75286, Miguel Bordalo 76330, Tiago Cerveira 76932
 *
 * This main permits to choose which mode to play through the input parameters 
 */
public class Main2 {


	public static void main(String[] args) {
		int minBet, maxBet;
		int balance;
		int numberDeck;
		int shuffle;
		int s_number;
		boolean swing = false;
		String strategy, cmd_file, shoe_file;
		
		
//		takes input parameters into the appropriate variables
		//TODO be stronger agaist input errors pode estar a aceder a valors do args qu enao existem 
		if(args.length>0){
			
			if (args[0].equals("-i")){//interactive mode
				try{
					minBet = Integer.parseInt(args[1]);
					maxBet = Integer.parseInt(args[2]);
					balance = Integer.parseInt(args[3]);
					numberDeck = Integer.parseInt(args[4]);
					shuffle = Integer.parseInt(args[5]);
					if(args.length>=7){
						if(args[6].equals("-gui")) swing = true;
					}
					if(minBet>=1 && minBet*10<=maxBet && maxBet<=20*minBet && balance >=50*minBet && 4<=numberDeck && numberDeck<=8 && 10<=shuffle && shuffle <=100){
						Interactive Intmode= new Interactive(minBet, maxBet, numberDeck, shuffle, swing);
						Intmode.play(balance);
					}else System.out.println("input parameter out of bound");
				}catch (Exception e){
					System.out.println("Error in the input parameters");
				}
				
			} else if (args[0].equals("-s")){//interactive mode
				try{
					minBet = Integer.parseInt(args[1]);
					maxBet = Integer.parseInt(args[2]);
					balance = Integer.parseInt(args[3]);
					numberDeck = Integer.parseInt(args[4]);
					shuffle = Integer.parseInt(args[5]);
					s_number=Integer.parseInt(args[6]);
					strategy=args[7];
					if(minBet>=1 && minBet*10<=maxBet && maxBet<=20*minBet && balance >=50*minBet && 4<=numberDeck && numberDeck<=8 && 10<=shuffle && shuffle <=100){
						Simulation Simmode= new Simulation(minBet, maxBet, numberDeck, shuffle, s_number, strategy);
						Simmode.play(balance);
					}else System.out.println("input parameter out of bound");
				}catch (Exception e){
					System.out.println("Error in the input parameters");
				}
			} else if (args[0].equals("-d")){//interactive mode
				try{
					minBet = Integer.parseInt(args[1]);
					maxBet = Integer.parseInt(args[2]);
					balance = Integer.parseInt(args[3]);
					shoe_file = args[4];
					cmd_file =args[5];
					if(minBet>=1 && minBet*10<=maxBet && maxBet<=20*minBet && balance >=50*minBet){
						Debug Debugmode= new Debug(minBet, maxBet,  shoe_file, cmd_file);
						Debugmode.play(balance);
					}else System.out.println("input parameter out of bound");
				}catch (Exception e){
					System.out.println("Error in the input parameters");
				}
			}else if (args[0].equals("-g")){
				//TODO add SWING HERE!
			}else System.out.println("You did not introduce a valid mode");
			
		}else{
			System.out.println("You introduced nothing");
			System.out.println("Dear to try one of this modes to play BlackJack");
			System.out.println("-i min-bet max-bet balance shoe shuffle");
			System.out.println("-d min-bet max-bet balance shoe-file cmf-file");
			System.out.println("-s min-bet max-bet balance shoe shuffle s-number strategy");
			System.out.println("-g");
		}
	}

}
