package game;

import java.util.Collections;
import game.Card.Card_Type;

/**
 * this class is a subclass of Deck, I chose this design because deck and sideDeck are the same except for the
 * requirement of inserting a card in the deck.
 * @author michaelbardakji
 *
 */
public class SideDeck extends Deck {
	private Card_Type type;
	
	public SideDeck(Card_Type type) {
		super(Collections.emptyList()); //constructs an empty deck
		this.type = type;
	}
	
			
	@Override
	public boolean canInsertToDeck(Deck deck) {
		if(deck.getNumOfCards() != 1) { //Makes sure we are only inserting one card at a time
			return false;
		}
		Card firstCard = deck.getFirstCard();
		if(this.getNumOfCards() == 0) { //if no cards in sideDeck, then the inserted Card must be a king
			return firstCard.getType() == this.type && firstCard.getValue() == 1;
		}
		return firstCard.isCompatible(this.getLastCard(), true); //if there are cards in the deck, we check if the two cards are compatible
	}
	
	@Override
	public String toString() {
		if(super.getLastCard() != null) {
			return this.type.toString() + ": " + super.getLastCard().toString();
		}
		return this.type.toString() + ": XXX ";
	}
	
	
}
