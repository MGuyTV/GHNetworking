public class Player{

private int score;
private String name;
	public Player(String name){
		this.score = 0;
		this.name = name;
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

	@Override
	public String toString(){
		return "Current score for player " + name + "is :" + score;
	}









}