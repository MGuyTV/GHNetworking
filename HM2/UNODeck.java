import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class UNODeck{
	private UNOCard[] unoCardArray = new UNOCard[108];//A deck of UNO cards has 90 cards, this could change in the future depending on what I find online
	private String[] colors = {"red", "yellow", "blue", "green"};
	//76 number cards
	//24 action cards
	//8 wild cards
	public UNODeck(){
		populateDeck(unoCardArray);

	}

	public UNOCard getValueAt(int num){
		return this.unoCardArray[num];
	}

	public void setDeck(UNOCard[] array){//I doubt I'll even use this, but lets put it in here just for convention
		this.unoCardArray = array;
	}

	public void populateDeck(UNOCard[] array){
		SecureRandom rand = new SecureRandom();
 
		//Populate the deck here with it's appropriate values
		//first number 
		for(int i = 0; i < array.length; i++){

			array[i] = new UNOCard(colors[rand.nextInt(4)], rand.nextInt(10));
		}
	}

	public void shuffleDeck(UNOCard[] array){//https://www.digitalocean.com/community/tutorials/shuffle-array-java
		//shuffle the values in the array here
		List<UNOCard> cardList = Arrays.asList(array);
		Collections.shuffle(cardList);
		cardList.toArray(array);
	}

	@Override
	public String toString(){//We can modify this later to look nicer
		String string = "";
		for(UNOCard card : this.unoCardArray){
			string += card.toString() + "\n";
		}
		return string;
	}


}