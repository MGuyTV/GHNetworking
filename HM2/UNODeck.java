public class UNODeck{
	private UNOCard[] unoCardArray = new UNOCard[90];//A deck of UNO cards has 90 cards, this could change in the future depending on what I find online

	public UNODeck(){
		populateDeck(unoCardArray);

	}

	public UNOCard[] getDeck(){
		return this.unoCardArray;
	}

	public void setDeck(UNOCard[] array){//I doubt I'll even use this, but lets put it in here just for convention
		this.unoCardArray = array;
	}

	public void populateDeck(){
		//Populate the deck here with it's appropriate values
	}

	public void shuffleDeck(){
		//shuffle the values in the array here
	}

	@Override
	public String toString(){//We can modify this later to look nicer
		String string = "";
		for(UNOCard card : unoCardArray){
			string = card.toString() + "\n";
		}
		return string;
	}


}