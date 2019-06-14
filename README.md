# Solitaire
@michaelbar98
Instructions for running the code : You can download the runnable jar file, then from a terminal, go to the directory that contains the jar ﬁle and run java -jar runnableFile.jar 
I implemented the card game Solitaire that is playable in a terminal/cmd/shell. In this readme I will refer to the variable that represents a card type as $ meaning that $ could be c, s, h, or d.
How To Play:
 • to start, either type "start hard" or "start easy"
 • to draw a card just type “d”
 • to move a card within the main table, use the command “m row,col col” where    row,col are the coordinates of the top of the sequence of cards that you want to    move, and col is the column of the deck where you want to put your cards. You    can treat row,col as where you would click the mouse and drag if you would play the actual game.
• to move a drawn card to the main table: “d col”
• to move a drawn card to the side table: “d $”
•        To move a card from the side table to the main table: “m $ col”
