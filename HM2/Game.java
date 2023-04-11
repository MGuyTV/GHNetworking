import java.util.Scanner;
public class Game{
	/*In this project I was originally going to implement, but I decided to implement a 
	  hybrid game between UNO and a style of the game of 21
	  Rules: Players will stay hit me to up their number
	  		-If they reach 21 (or any number we want it to be), they win
	  		-If the player tips over 21, they lose the game (or the round, whatever we want
	  		-The colors have special features:
	  			-Yellow is a +1 additive
	  			-Red is a +2 additive
	  			-Blue is a + 4 additive
	  			-Green is a +15 additive
	  		-So if you start with a card 10 with a green suit, then you are currently at 20 points total)*/



	private UNODeck deck;
	private Player player1;
	private Player player2;
	public Game(Player player1, Player player2){
		deck = new UNODeck();
		//System.out.println(deck.toString());
		//playTheGame(deck, player1, player2)
	}

	public String checkWinCondition(Player player1, Player player2){//Will return the outcome of the game in a string
			String winString = 	   "_____.___.               __      __            ._.\n";
			winString +=		   "\\__  |   | ____  __ __  /  \\    /  \\____   ____| |\n";
			winString +=		   " /   |   |/  _ \\|  |  \\ \\   \\/\\/   /  _ \\ /    \\ |\n";
			winString +=		   " \\____   (  <_> )  |  /  \\        (  <_> )   |  \\|\n";
			winString +=		   " / ______|\\____/|____/    \\__/\\  / \\____/|___|  /_\n";
			winString +=		   " \\/                            \\/             \\/\\/\n";

			if(player1.getScore() >= 21)
				return "Player 1 wins!\n" + winString;//Do it like this so the server can directly get the output of who wins.
			if(player2.getScore() >= 21)
				return "Player 2 wins!\n" + winString;
			return "No winner yet";

	

	}

	public UNODeck getDeck(){
		return this.deck;
	}

	public static void main(String[] args){
		Player player1 = new Player("Matt");
		Player player2 = new Player("Garret");
		//UNODeck deck = new UNODeck();//The game provides its own deck
		Scanner scnr = new Scanner(System.in);
		Game game = new Game(player1, player2);
		while(game.checkWinCondition(player1, player2) == "No winner yet"){
			String line = scnr.nextLine();
		}
	}

	//@Override






}