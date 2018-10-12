package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.Card.Card_Type;

/**
 * this class represents a deck in the game, a deck is the deck where user draws from, the 7 main columns in the table, and the 4 side decks
 * @author michaelbardakji
 *
 */
public class Deck {
	private List<Card> deck;
	
	/**
	 * a deck constructor that is used to construct the deck where user draws cards from
	 * @param difficulty whether its easy or hard
	 */
	public Deck(String difficulty) {
		//make a normal 52 cards deck
		deck = this.makeDeck(difficulty);	



	}

	/**
	 * constructor for deck that adds the cards to the add
	 * @param list: list of cards that are in the newly created deck
	 */
	public Deck(List<Card> list) {
		deck = new ArrayList<Card>();
		for (Card card : list) {
			deck.add(card);
		}

	}


	/**
	 * This method adds the 4 types of a card to the deck
	 * @param list a list of cards that needs to be populated
	 * @param value the value of the card (1 - 13)
	 */
	private void addCardsToList(List<Card> list, int value) {
		Card curr;
		for (Card_Type type : Card_Type.values()) {
			curr = new Card(type, value);
			list.add(curr);	


		}

	}

	/**
	 * This method creates the deck
	 * @param difficulty 
	 * 
	 * @return a list of 13*4 Cards
	 */
	private List<Card> makeDeck(String difficulty){
		List<Card> deck = new ArrayList<Card>();
		for(int i = 1; i < 14; i++) { //iterates through all 13 cards
			this.addCardsToList(deck, i);
			//instantiate the card for each type

		}
		if(difficulty.equals("hard")) {
			Collections.shuffle(deck); //shuffle the deck
		}

		return deck;

	}

	public void reverseOrder() {
		Collections.reverse(this.deck);

	}

	private List<Card> getList(){
		return this.deck;
	}

	public Deck getCardsBelow(int belowindex) {
		List<Card> output = this.deck.subList(belowindex, this.getNumOfCards()); //gets all cards below the index		
		return new Deck(output);

	}

	public void removeCardsBelow(int belowindex) {
		this.deck = this.deck.subList(0, belowindex); //removes the cards from current deck
		if(this.getNumOfCards() != 0) {
			this.deck.get(this.getNumOfCards() -1).changeVisible(true);
		}

	}

	public void insertBelow(Deck deck) {
		this.deck.addAll(deck.getList());

	}

	public Card getCardByIndex(int index){
		if(index<this.getNumOfCards()) {
			return deck.get(index);
		}
		return null;
	}

	public void addToList(Card card) {
		this.insertBelow(card.toDeck());
	}

	public Card getLastCard() {
		if(!deck.isEmpty()) {
			return deck.get(deck.size()-1);
		}
		return null;
	}

	public Card getFirstCard() {
		if(!deck.isEmpty()) {
			return deck.get(0);
		}
		return null;
	}

	public Card removeCard() {
		if(!deck.isEmpty()) {
			return deck.remove(this.getNumOfCards()-1);
		}
		return null;
	}

	public boolean canInsertToDeck(Deck deck) {
		if(this.getNumOfCards() == 0) {
			return deck.getFirstCard().getValue()==13;
		}
		return this.getLastCard().isCompatible(deck.getFirstCard(), false);
	}

	public int getNumOfCards(){
		return deck.size();
	}


}
