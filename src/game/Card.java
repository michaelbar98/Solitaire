package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class represents each card in the game
 * @author michaelbardakji
 *
 */
public class Card {
	private Card_Type type;
	private int value;
	private Boolean isVisible;
	private Color color;
	private Map<Integer, String> numbertojqk;



	enum Card_Type{
		Spades,Clubs, Hearts, Diamonds;
	}

	public Card(Card_Type type, int value) {
		this.type = type;
		this.value = value;
		this.isVisible = false; //all cards are not visible when starting
		numbertojqk = new HashMap<Integer, String>();
		numbertojqk.put(1, "A");
		numbertojqk.put(11, "J");
		numbertojqk.put(12, "Q");
		numbertojqk.put(13, "K");

		if(type == Card_Type.Hearts || type == Card_Type.Diamonds) {
			this.color = Color.RED;
		}else {
			this.color = Color.BLACK;
		}
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public Card_Type getType() {
		return this.type;
	}

	public int getValue() {
		return this.value;
	}

	public Color getColor() {
		return this.color;
	}

	public void changeVisible(Boolean bool) {
		this.isVisible = bool;
	}

	/**
	 * This method returns whether card can be placed underneath this card
	 * @param card card to be placed
	 * @param side whether this card exists in a side deck or the first 7 main table decks
	 * @return whether user can place the card or not
	 */
	public boolean isCompatible(Card card, boolean side) {
		if((side && card.getColor() == this.getColor()) || card.getColor() != this.getColor()) {
			return card.getValue() == this.getValue() - 1;
		}

		return false;
	}

	/**
	 * creates a deck containing this card
	 * @return a deck with only one value
	 */
	public Deck toDeck() {
		List<Card> list = new ArrayList<Card>();
		list.add(this);
		return new Deck(list);
	}


	@Override
	public String toString() {
		String printingValue =  Integer.toString(this.value);
		String prefix = " ";
		if (this.isVisible) {
			if(numbertojqk.containsKey(this.value)) {
				printingValue = this.numbertojqk.get(this.value); //to print K in stead of 13, Q in stead of 12 ..

			}else if(this.value == 10) { //adjusts printing for the only double digit number
				prefix = "";

			}
			return prefix + printingValue + ":" + this.type.toString().charAt(0) + "  ";

		}	//if not visible, print Xs
		return "XXXXX ";
	}

}
