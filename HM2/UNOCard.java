public class UNOCard{
	private String cardColor = "";
	private int cardNumber = 0;


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
	

	@Override
	public String toString(){
		//return String.valueOf("Color: %s, Number: %d", cardColor, cardNumber);
		return "Color: " + cardColor + ", Number: " + cardNumber;
	}













}
