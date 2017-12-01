
public class Player extends PlayerHand {
	
	public Player() {
		
	}
	
	//Override required in order to return type Card rather than type Object (I named it differently so I wouldn't get confused)
	public Card getFirstCard() {
		return (Card) super.get();
	}
	
	//Override required in order to return type Card rather than type Object (I named it differently so I wouldn't get confused)
	public Card peekFirstCard() {
		return (Card) super.peek();
	}
}
