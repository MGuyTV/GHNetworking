public class UNOCard{
	private String cardColor = "";
	private int cardNumber = 0;
	private String cardName = "";


	public UNOCard(String cardColor, int cardNumber){
		this.cardColor = cardColor;
		this.cardNumber = cardNumber;
	}

	public String getColor(){
		return this.cardColor;	
	}

	public void setColor(String color){
		this.cardColor = color;
	}

	public int getNumber(){
		return this.cardNumber;
	}

	public void setNumber(int number){
		this.cardNumber = number;
	}
	
	public String getName(){
		return this.cardName;
	}

	public void setName(String name){
		this.cardName = name;
	}

	@Override
	public String toString(){
		return String.valueOf("Color: %S, Number: %d", cardColor, cardNumber);
	}













}
