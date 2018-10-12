# Solitaire
@michaelbar98
Instructions for running the code : You can download the runnable jar file, then from a terminal, go to the directory that contains the jar ﬁle and run java -jar runnableFile.jar <br/>
I implemented the card game Solitaire that is playable in a terminal/cmd/shell. In this readme I will refer to the variable that represents a card type as $ meaning that $ could be c, s, h, or d.<br/>
How To Play:<br/>
 • to start, either type "start hard" or "start easy"<br/>
 • to draw a card just type “d”<br/>
 • to move a card within the main table, use the command “m row,col col” where    row,col are the coordinates of the top of the sequence of cards that you want to    move, and col is the column of the deck where you want to put your cards. You    can treat row,col as where you would click the mouse and drag if you would play the actual game. <br/>
• to move a drawn card to the main table: “d col” <br/>
• to move a drawn card to the side table: “d $” <br/>
•        To move a card from the side table to the main table: “m $ col” <br/>
I made a Deck class that represents any combination of cards stuck together. For example, the main table has 7 decks, and 4 side decks (for each card type) and there is also a drawing deck where user draws cards from. In addition, the rules from adding a card to the deck is different for the main 7 decks (colors has to be different) and the 4 side ones (where color must match). I decided to create a subclass sideDeck that extends Deck. I overridden two functions there.
