public class Player{

private int score;
private String name;
private String response;
	public Player(String name){
		this.score = 0;
		this.name = name;
		this.response = "No Response yet";
	}

	public int getScore(){
		return this.score;
	}

	public void setScore(int score){
		this.score = score;
	}

	public String getName(){
		return this.name;
	}

	public String getResponse(){
		return this.response;
	}

	public void setResponse(String response){
		//Should be either "raise" or "pass"
		this.response = response;
	}

	public void add(int num, String response){
		if(response == "yellow"){
			this.setScore(num + 1);
			return;
		}

		if(response == "red"){
			this.setScore(num + 2);
			return;
		}

		if(response == "blue"){
			this.setScore(num + 4);
			return;
		}

		if(response == "green"){
			this.setScore(num + 15);
			return;
		}
	}

	@Override
	public String toString(){
		return "Current score for player " + name + "is :" + score;
	}









}