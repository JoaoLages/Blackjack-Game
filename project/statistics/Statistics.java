package statistics;

/**
 * 
 * @author Miguel
 * 
 * TODO: how should implemented in blackJackgame:
 * 		all the time one of this actions happens increment with the correct function player_blackjacks, dealer_blackjacks, player_wins, player_loses, player_pushes;
 *		print the satistics when st command is called
 */
public class Statistics {
	private int player_blackjacks, dealer_blackjacks, player_wins, player_loses, player_pushes;
	private float  init_balance, gain;
	
	/**
	 * Constructor
	 * @param init_balance_
	 */
	public Statistics(float init_balance_){
		player_blackjacks=0;
		dealer_blackjacks=0;
		player_wins=0;
		player_loses=0;
		player_pushes=0;
		init_balance=init_balance_;
		gain=1;
	}
	
	/** 
	 * Reset statistics values
	 * @param init_balance_
	 */
	public void resetStatistics(){
		player_blackjacks=0;
		dealer_blackjacks=0;
		player_wins=0;
		player_loses=0;
		player_pushes=0;

		gain=1;
	}
	
	/**
	 * Call this set of functions always that one of this actions happens in the game
	 */
	public void incrementPlayerBlackJack(){
		player_blackjacks+=1;
	}
	public void incrementDealerBlackJack(){
		dealer_blackjacks+=1;
	}
	public void incrementPlayerWins(){
		player_wins+=1;
	}
	public void incrementPlayerLoses(){
		player_loses+=1;
	}
	public void incrementPlayerPushes(){
		player_pushes+=1;
	}
	
	/**
	 * local function to compute the Statistics (gain)
	 */
	private void computeStatistics(float final_balance){
		gain = (float) final_balance/init_balance*100;
	}
	
	
	public static String stripHtmlTags(StringBuilder sb) {
	    int open;
	    while ((open = sb.indexOf("<")) != -1) {
	        int close = sb.indexOf(">", open + 1);
	        if(sb.toString().substring(open, close+1).equals("<br>")){
		        sb.replace(open, close+1, "\n");
	        }else{
	        	sb.delete(open, close + 1);
	        }
	    }
	    while ((open = sb.indexOf("&")) != -1) {
	        int close = sb.indexOf(";", open + 1);
	        //sb.delete(open, close + 1);
	        sb.replace(open, close+1, " ");
	    }
	    return sb.toString();
	}
	
	/** 
	 * Print to the terminal the statistics of the game
	 */
	public StringBuilder printStatistics(float final_balance){
		StringBuilder output = new StringBuilder();
		computeStatistics(final_balance);
		//String output;                  
		output.append("<html>BJ&nbsp;P/D&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+player_blackjacks+"/"+dealer_blackjacks+"<br>");
		output.append("Win&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+player_wins+"<br>");
		output.append("Lose&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+player_loses+"<br>");
		output.append("Push&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+player_pushes+"<br>");
		output.append("Balance&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+final_balance + "(%"+gain+")"+"</html>");
		
		return output;
	}
/**
 * Local function to test this Class
 * @param args
 */
public static void main(String[] args) {
	int init_balance=500;
	
	Statistics st = new Statistics(init_balance);
	
	st.incrementDealerBlackJack();
	st.incrementDealerBlackJack();
	st.incrementPlayerLoses();
	st.incrementPlayerLoses();
	st.incrementPlayerLoses();
	st.incrementPlayerLoses();
	st.incrementPlayerWins();
	st.incrementPlayerWins();
	st.incrementPlayerWins();
	
//	st.printStatistics();
//	st.resetStatistics();
//	st.printStatistics();
}

}
