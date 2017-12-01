
public class Main {
	

	static Player player0;
	static Player player1;
	//static Player player0, player1;
	static Player[] players = {player0, player1};
	
	
	static Board p0;
	static Board p1;
	static Board[] boards = {p0, p1};
	
	public static boolean peace = true;
	public static int roundCount = 0;

	public static void main(String[] args) {
		//pretty self-explanatory
		deal();
		playWar();
		
		

	}
	
	public static void playWar() {
		//keep on playing while players still have cards
		while(players[0].size()>0 && players[1].size()>0) {
			playRound();
		}
		if(players[0].size() > players[1].size()) {
			System.out.println("Player 0 wins in " + roundCount + " rounds!");
		}
		else {
			System.out.println("Player 1 wins in " + roundCount + " rounds!");
		}
	}

	public static void deal() {
		Deck deck = new Deck();
		players[0] = new Player();
		players[1] = new Player();
		boards[0] = new Board();
		boards[1] = new Board();
		deck.shuffle();
		while(!(deck.isEmpty())) {
			//deal them out one at a time alternating
			players[0].put(deck.getCard());
			players[1].put(deck.getCard());
		}
	}
	
	
	public static void playRound() {
		roundCount++; //keep track of the rounds
		//only play the round if the players have cards to play
		if(players[0].size()>0) {
			boards[0].put(players[0].getFirstCard());
		}
		if(players[1].size()>0) {
		 boards[1].put(players[1].getFirstCard());
		}
		
		System.out.print(boards[0].peekFirstCard().getSymbol() + " vs. " + boards[1].peekFirstCard().getSymbol());
		System.out.println("");
		if((boards[0].peekFirstCard().getRank() == 2 || boards[1].peekFirstCard().getRank() == 2) && !(peace)) { peace = false; } //this 2 means war
		
		if(peace && !(boards[0].peekFirstCard().equals(boards[1].peekFirstCard()))) {
			peace = false; // this makes 2 belligerent again
			//(check if player 0's card is higher than player 1's card and player 0 doesn't have an 8) or check if player 0 has an 8
			if((boards[0].peekFirstCard().compareTo(boards[1].peekFirstCard()) > 0 && boards[1].peekFirstCard().getRank() != 8) || boards[0].peekFirstCard().getRank() == 8) {
				//Give all the cards in the board to the player who wins the round
				players[0].addAll(boards[0]);
				players[0].addAll(boards[1]);
			}
			else {
				players[1].addAll(boards[0]);
				players[1].addAll(boards[1]);
			}
			//make sure the boards are empty after every round
			boards[0].clear();
			boards[1].clear();
			
			System.out.print("Player 0: " + players[0].size() + " cards, Player 1: " + players[1].size() + " cards");
			System.out.println("");
		}
		
		else {
			peace = true; //2 doesn't count as war now
			System.out.println("WAR! (Isn't that exciting?)");
			war();
		}
	}

	public static void war() {
		System.out.print("Down Cards: P0: ");
		//put additional three cards on the board 
		for(int i = 0; i<3 && i<players[0].size(); i++) {
			boards[0].put(players[0].getFirstCard());
			System.out.print(boards[0].peekFirstCard().getSymbol() + " ");
		}
		System.out.print("P1: ");
		for(int i = 0; i<3 && i<players[1].size(); i++) {
			boards[1].put(players[1].getFirstCard());
			System.out.print(boards[1].peekFirstCard().getSymbol() + " ");
		}
		System.out.println("");
		playRound();
	}

}
