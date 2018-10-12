package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * this is the class that starts the REPL and the game
 * It processes the user's input
 * @author michaelbardakji
 *
 */
public class Game {
	private String difficulty;
	public Game() {
		this.startHelp();
		this.startGame(difficulty);

	}


	private int[] getRowColFromInput(String input) {
		String[] arr = input.split(",");
		int[] output = {Integer.parseInt(arr[0])-1, Integer.parseInt(arr[1])-1};
		return output;
	}

	private int[][] getFromAndTo(String s1, String s2, Map<String, Integer> types){
		int[][] out = new int[2][2];
		if(types.containsKey(s1)) {
			out[0] = new int[] {-1,types.get(s1)};
			out[1] = new int[] {-1,Integer.parseInt(s2)-1};
		}else if(types.containsKey(s2)){
			out[1] = new int[] {-1,types.get(s2)};
			out[0] = this.getRowColFromInput(s1);
		}else {
			return null;
		}
		return out;
	}

	private boolean isValidColumn(String strNum) {
		try {
			int i = Integer.parseInt(strNum);
			return (i>=1 && i<=7);
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
	}

	private String helpCase(String[] strarr, boolean started) {
		switch(strarr.length) {
		default:
			return "invalid number of arguments";
		case 1:
			if(!started) {
				return "possible commands are: \"exit\", \"start easy\", \"start hard\", but when playing Possible commands are: \"m\" or \"d\" Type \"help m\" or \"help d\" for more";
			}
			return "possible commands are: \"m\" , \"d\", \"exit\". Type \"help m\" or \"help d\" for more";
		case 2:
			switch(strarr[1]) {
			default:
				return "possible help commands are: \"help m\" or \"help d\"";
			case "m":
				return "m is for move, general form is \"m from to\" where from is \"row,col\" or either c s h d and to is \"colnumber\" or either c s h d";
			case "d":
				return "d is for draw, \"d\" by itself draws a card, \"d to\" moves the most recently drawn card (to is colnumber or either c s h d)";

			}
		}
	}
	
	
	private void startHelp() {
		System.out.println("Type \"help\" to see possible commands, or \"start easy\" to start the game with low difficulty or \"start hard\" for high difficulty");
		System.out.println("");
		String input;
		String message = "";
		Scanner readUserInput = new Scanner(System.in);
		help: while (readUserInput.hasNextLine()) {
			input = readUserInput.nextLine().toLowerCase();
			String[] strarr = input.split(" ");
			switch(strarr[0]) {
			default:
				message = "current possible commands are \"help\" or \"start easy\" for low difficulty or \"start hard\" for high difficulty";
				break;
			case "help":
				message = this.helpCase(strarr, false);
				break;
			case "start":
				if(strarr.length == 2 && strarr[1].equals("easy") || strarr[1].equals("hard")) {
					this.difficulty = strarr[1];
					break help;
				}
				break;
			case "exit":
				System.exit(0);
				break;
			}
			System.out.println(message);

		}


	}

	public void startGame(String difficulty) {

		Map<String, Integer> possibleTypes = new HashMap<String, Integer>();
		possibleTypes.put("c", 7);
		possibleTypes.put("s", 8);
		possibleTypes.put("h", 9);
		possibleTypes.put("d", 10);

		Table table = new Table(difficulty);
		String error;
		String input;
		Scanner readUserInput = new Scanner(System.in);

		while (readUserInput.hasNextLine()) {
			error = "";
			input = readUserInput.nextLine().toLowerCase();
			String[] strarr = input.split(" ");
			try {
				switch(strarr[0]) {
				default:
					error = "Invalid command";
					break;
				case "m":
					if(strarr.length != 3) {
						error = "Invalid command, type help for more";
					}else if(strarr[1].contains(",") && this.isValidColumn(strarr[2])){
						int[] from = this.getRowColFromInput(strarr[1]);
						int to = Integer.parseInt(strarr[2]) -1;
						error = table.move(from, to);
					}else if(possibleTypes.containsKey(strarr[2]) || possibleTypes.containsKey(strarr[1])) {
						int[][] fromAndTo = this.getFromAndTo(strarr[1], strarr[2], possibleTypes);
						error = table.move(fromAndTo[0], fromAndTo[1][1]);
					}else {
						error = "Invalid command, type help for more";
					}
					break;

				case "d":
					if(strarr.length == 1) {
						table.drawCard();
					}else if(strarr.length == 2){
						if(this.isValidColumn(strarr[1])) {
							int to = Integer.parseInt(strarr[1])-1;
							error = table.move(null, to);

						}else if(possibleTypes.containsKey(strarr[1])){

							error = table.move(null, possibleTypes.get(strarr[1]));
						}else {
							error = "Invalid command, type help for more";
						}

					}else {
						error = "Invalid command, to draw a card type \"d\", for more type help ";
					}
					break;
					
				case "help":
					error = this.helpCase(strarr,true);
					break;
					
				case "exit":
					System.exit(0);
					break;
				}

			}catch(Exception e) {
				error = "Invalid command, type help for more";
			}
			System.out.println(error);
		}
		readUserInput.close();
	}
}
