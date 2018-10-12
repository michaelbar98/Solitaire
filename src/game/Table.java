package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.Card.Card_Type;

/**
 * this class represents the whole table where the user plays
 * @author michaelbardakji
 *
 */
public class Table {
	String message;
	private Deck drawDeck;
	private Deck mainDeck;
	private Deck[] mainTable;
	private int numberOfMoves;
	public Table(String difficulty) {
		numberOfMoves = 0;
		mainDeck = new Deck(difficulty); //deck containing 52 cards
		drawDeck = new Deck(Collections.emptyList()); //when the user draws a card, the card goes in this deck
		mainTable = this.makeInitialTable();
		this.drawTable();
	}


	private void makeInitialSideTable(Deck[] table) {
		table[7] = new SideDeck(Card_Type.Clubs);
		table[8] = new SideDeck(Card_Type.Spades);
		table[9] = new SideDeck(Card_Type.Hearts);
		table[10] = new SideDeck(Card_Type.Diamonds);


	}

	private Deck[] makeInitialTable() {
		Deck[] table = new Deck[11];
		for(int column = 0; column < 7; column ++){
			List<Card> cards = new ArrayList<Card>();
			for(int row = 0; row < column + 1; row++) {
				Card curr = mainDeck.removeCard();
				if(row == column) {
					curr.changeVisible(true);
				}
				cards.add(curr);
			}
			table[column] = new Deck(cards);

		}
		this.makeInitialSideTable(table);
		return table;	
	}


	/*
	 * this method only prints Integers (columns) 1 through 7 at the very top
	 */
	private void printFirstLine() {
		System.out.print("    ");
		for(int col = 1; col < 7 + 1; col++) {
			System.out.print(col + "     ");
		}
		System.out.println("");
	}


	
	/**
	 * gets the cards underneath the given card (including the given card)
	 * @param coord: coord of given card or null to get from draw deck
	 * @return a deck of cards that are underneath the given card
	 */
	public Deck getDeck(int[] coord) {
		if(coord == null) { //if null then we are moving the card from the drawDeck
			if(this.drawDeck.getNumOfCards() == 0) {
				message = "You need to draw a card first";
				return null;
			}
			return this.drawDeck.getLastCard().toDeck();
		}else if(coord[0] == -1) {
			return mainTable[coord[1]].getLastCard().toDeck();
		}
		Card card = mainTable[coord[1]].getCardByIndex(coord[0]);
		if(card != null) {
			return mainTable[coord[1]].getCardsBelow(coord[0]);

		}
		message = "Invalid move";
		return null;
	}

	private Deck getColDeck(int col) {
		return this.mainTable[col];
	}
	
	
	/**
	 * method that is used to move cards in the game
	 * @param from coordinates of the card we want to move, or null to move the drawn card
	 * @param to: the column where the card needs to move to, 0-6 for first 7 main columns, 7-10 for the sideDecks
	 * @return an empty string meaning success or an error message
	 */
	public String move(int[] from, int to) {
		message = "";
		Deck movingDeck = this.getDeck(from);
		if(movingDeck == null) {
			this.drawTable();
			return message;
		}
		Deck toDeck = this.getColDeck(to);
		Deck fromDeck;
		if(from == null) {
			fromDeck = movingDeck;
		}else {
			fromDeck = this.getColDeck(from[1]);
		}
		if(toDeck.canInsertToDeck(movingDeck)) {
			toDeck.insertBelow(movingDeck);
			if(from != null) {
				if(from[0] == -1) {
					fromDeck.removeCard();
				}else {
					fromDeck.removeCardsBelow(from[0]);
				}
			}else {
				this.drawDeck.removeCard();
			}
			
		}else {
			message = "Invalid move";
		}

		this.drawTable();
		if(message.equals("")) {
			this.numberOfMoves++;
		}
		return message;


	}

	
	/**
	 * this method is called when user needs to draw a card
	 */
	public void drawCard() {
		if(this.mainDeck.getNumOfCards() != 0) { //if there are cards in main deck
			Card card = this.mainDeck.removeCard(); //removes one card
			card.changeVisible(true); //sets the drawn card to be visible
			this.drawDeck.addToList(card);

		}else { //the main deck is out of cards, I reinsert drawn cards back into it
			this.mainDeck.insertBelow(this.drawDeck); //inserts drawn cards back into main deck but in reversed order
			this.mainDeck.reverseOrder(); //reverse the order again to go back to normal
			drawDeck = new Deck(Collections.emptyList());
		}
		this.drawTable();


	}

	private void printDrawDeck() {
		if(this.drawDeck.getNumOfCards() != 0) {
			int iter = 0;
			System.out.print("\n  Last Drawn Cards:");
			for(int i = this.drawDeck.getNumOfCards() -1; i >=0 ; i--) {
				if(iter ==3) {
					break;
				}
				System.out.print(drawDeck.getCardByIndex(i).toString());
				iter++;
			}	
			System.out.println("");
		}

	}

	private void printSideDecks() {
		for(int i = 7; i < 11; i++) {
			System.out.print(this.mainTable[i].toString());
		}
		System.out.println("");
	}

	private int getMaxRow() {
		int max = -1;
		for(int i = 0; i < 7; i++) {
			max = Math.max(this.mainTable[i].getNumOfCards(), max);
		}
		return max;
	}

	private void checkGameOver() {
		int cardSum = 0;
		for(int i = 7; i < 11; i++) {
			cardSum += this.mainTable[i].getNumOfCards();
		}
		if(cardSum == 52/2) {
			System.out.println("Congratulations! You won with " + this.numberOfMoves + " moves!");
			System.exit(0);
		}
	}
	
	/**
	 * method that is responsible of drawing the main table
	 */
	private void drawTable() {
		this.printSideDecks();
		this.printFirstLine();
		int maxRow = this.getMaxRow();
		for(int row = 0; row < maxRow; row++) {
			String line;
			if(row + 1 > 9) { 
				line = (row+1) + " ";//adjusts for the double digit 
			}else { 
				line = (row+1) + "  ";//print the row number at the very left
			}
			for(int i = 0; i < 7; i++) {
				Deck col = this.mainTable[i];
				if(col.getNumOfCards() > row) {
					Card card = col.getCardByIndex(row);
					line += card.toString();
				}else {
					line += "      ";
				}
			}
			System.out.println(line);
		}
		this.printDrawDeck();
		this.checkGameOver();
	}

}
