
public class Board extends PlayerHand {

	public Board() {
		
	}
	
	//Read comments in player class because this is basically the same thing
	public Card getFirstCard() {
		return (Card) super.get();
	}
	
	public Card peekFirstCard() {
		return (Card) super.peek();
	}
	
}
