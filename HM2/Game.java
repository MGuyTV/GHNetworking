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
	public Game(/*Player player1, Player player2*/){
		deck = new UNODeck();
		//playTheGame()
	}

	public UNODeck getDeck(){
		return this.deck;
	}

	//@Override






}