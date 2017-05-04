package Swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import gamemodes.Interactive;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;



public class Graphical_Interface extends Thread {

	public static volatile JFrame frame, frame2;

	private JTextField textField;
	public static volatile List<JLabel> dealer = new ArrayList<JLabel>();
	public static volatile List<JLabel> player = new ArrayList<JLabel>();
	public static volatile boolean buttonPressed=false;
	public static volatile JLabel Invalidwindow = new JLabel(" ");
	public static volatile JLabel textWindow = new JLabel("  ");
	public static volatile JLabel Invalid = new JLabel("");
	public static volatile JLabel chips = new JLabel("  ");
	public static volatile String button="null", bet;
	//player's points
	public static volatile JLabel player_points = new JLabel(" ");
	public static volatile JLabel dealer_points = new JLabel(" ");
	//public static volatile String[] Cards;
	//public static volatile int HandValue;
	//public static volatile boolean isplayer, changeInterface=false;
	public Graphical_Interface a;
	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public void run() {
	   try {
		   	initialize();
			//Graphical_Interface.frame.setVisible(true);
		
	  } catch (Exception e) {
	  // TODO Auto-generated catch block
	       e.printStackTrace();
	       System.out.println("Exception in Thread!");
	  }
	}
		 

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 586, 308);
		frame.setDefaultCloseOperation(0);
		frame.getContentPane().setLayout(null);
				
		JButton btnNewButton = new JButton("$");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="$";
				buttonPressed=true;
			
				buttonPressed=false;
			}
		});
		btnNewButton.setBounds(0, 246, 45, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("st");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="st";
				buttonPressed=true;
				
				buttonPressed=false;
			}
		});
		btnNewButton_1.setBounds(42, 246, 45, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnAd = new JButton("ad");
		btnAd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="ad";
				buttonPressed=true;
				
				buttonPressed=false;
			}
		});
		btnAd.setBounds(85, 246, 51, 23);
		frame.getContentPane().add(btnAd);
		
		JButton btnP = new JButton("p");
		btnP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="p";
				buttonPressed=true;
				
				buttonPressed=false;
			}
		});
		btnP.setBounds(263, 246, 45, 23);
		frame.getContentPane().add(btnP);
		
		JButton btnI = new JButton("i");
		btnI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="i";
				buttonPressed=true;
				
				buttonPressed=false;
			}
		});
		btnI.setBounds(134, 246, 45, 23);
		frame.getContentPane().add(btnI);
		
		JButton btnU = new JButton("u");
		btnU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="u";
				buttonPressed=true;
			
				buttonPressed=false;
			}
		});
		btnU.setBounds(177, 246, 45, 23);
		frame.getContentPane().add(btnU);
		
		JButton button_4 = new JButton("2");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="2";
				buttonPressed=true;
				
				buttonPressed=false;
			}
		});
		button_4.setBounds(220, 246, 45, 23);
		frame.getContentPane().add(button_4);
		
		JButton btnS = new JButton("s");
		btnS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="s";
				buttonPressed=true;
			
				buttonPressed=false;
				
			}
		});
		btnS.setBounds(306, 246, 45, 23);
		frame.getContentPane().add(btnS);
		
		JButton btnH = new JButton("h");
		btnH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="h";
				buttonPressed=true;
			
				buttonPressed=false;
			}
		});
		btnH.setBounds(349, 246, 45, 23);
		frame.getContentPane().add(btnH);
		
		JButton btnD = new JButton("d");
		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="d";
				buttonPressed=true;
			
				buttonPressed=false;
			}
		});
		btnD.setBounds(392, 246, 45, 23);
		frame.getContentPane().add(btnD);
		
		JButton btnD_1 = new JButton("b");
		btnD_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="b";
				bet=textField.getText();
				buttonPressed=true;
			
				buttonPressed=false;
			}
		});
		btnD_1.setBounds(435, 246, 45, 23);
		frame.getContentPane().add(btnD_1);
		
		textField = new JTextField();
		textField.setText("");
		textField.setBounds(502, 247, 59, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblYourBet = new JLabel("Your bet:");
		lblYourBet.setBounds(502, 231, 59, 14);
		frame.getContentPane().add(lblYourBet);
		
		JButton quit = new JButton();
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button="q";
				buttonPressed=true;
				
				buttonPressed=false;
				closeScreen();
			}
		});
		
		textWindow.setBounds(10, 108, 147, 129);
		frame.getContentPane().add(textWindow);
		
		
		chips.setBounds(0, 0, 136, 111);
		chips.setIcon(new ImageIcon("src/Icons/chips-3.png"));  
		frame.getContentPane().add(chips);
		
		
		
		frame.setDefaultCloseOperation(0);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(frame, 
		            "Are you sure to close this window?", "End Game?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	button="q";
					buttonPressed=true;
					
					buttonPressed=false;
					closeScreen();
					
		        }
		    }
		});
		for(int i=0; i<5; i++){
			player.add(i, new JLabel(" "));
			player.get(i).setBounds(195+i*80, 123, 51, 70);
			frame.getContentPane().add(player.get(i));
			
			dealer.add(i, new JLabel(" "));
			dealer.get(i).setBounds(195+i*80, 21, 51, 70);
			frame.getContentPane().add(dealer.get(i));
		}
	
		player_points.setBounds(140, 151, 26, 14);
		frame.getContentPane().add(player_points);

		dealer_points.setBounds(140, 49, 26, 14);
		frame.getContentPane().add(dealer_points);
		frame.getContentPane().setBackground(new Color(25,132,0));
		
		
		Invalid.setBounds(228, 221, 209, 14);
		frame.getContentPane().add(Invalid);
		frame.setVisible(true);	
		
		
	}
	public static void setText(String text, JLabel Label){
		Label.setText(text);	
	}
	public static void setCards(String Hand, int _HandValue, boolean _isplayer){
		String[] Cards= Hand.split(" ");
		if(_isplayer){
			if(_HandValue==-1){
				player_points.setText(" ");
			}else{
				player_points.setText("("+_HandValue+")");
			}
			for(int i=0; i<Cards.length && i<5; i++){
				player.get(i).setIcon(new ImageIcon("src/Icons/"+Cards[i]+".png"));  
			}
		}else{
			if(_HandValue==-1){
				dealer_points.setText(" ");
			}else if(_HandValue!=0){
				dealer_points.setText("("+_HandValue+")");
			}
			for(int i=0; i<Cards.length && i<5; i++){
				dealer.get(i).setIcon(new ImageIcon("src/Icons/"+Cards[i]+".png"));  
			}
		}
	
	}
	public static void player_Won_Lost_Pushed(int result){ //0-push, 1-won, -1-lost -2-surrender
		frame.setEnabled(false);
		
		JLabel Textresult = new JLabel(" ");
		frame2 = new JFrame();
		frame2.setBounds(250, 150, 216, 184);
		frame2.setDefaultCloseOperation(0);
		frame2.getContentPane().setLayout(null);
		frame2.setVisible(true);
		switch(result){
			case -2: Textresult.setText("Surrender!");;
			break;
			case -1: Textresult.setText("You lost!");
			break;
			case 0: Textresult.setText("You pushed!");
			break;
			case 1: Textresult.setText("You won!");;
			break; 
		}
		Textresult.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Textresult.setBounds(52, 28, 114, 32);
		frame2.getContentPane().add(Textresult);
		JButton OK = new JButton("OK");
		OK.setBounds(73, 75, 56, 41);
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setEnabled(true);
				frame2.setVisible(false);
				frame2.dispose();
				String resetCards="background background background background background ";
				setCards(resetCards, -1, true);
				setCards(resetCards, -1, false);

			}
		});
		frame2.getContentPane().add(OK);
	}
	
	public void closeScreen(){
		Graphical_Interface.frame.setVisible(false);
		Graphical_Interface.frame.dispose();
	}
}
