import java.util.HashMap;


public class City {
	
	public String name;
	protected int id;
	//positions on board
	protected int posX;
	protected int posY;
	
	//depends on the geographic position, which disease could occur naturally
	Disease regionalDisease;
	//HashMap each Disease is associated with his level in this city
	HashMap <Disease, Integer> condition;

	public String toString(){
		return("City nb "+this.id+" : "+this.name+" ("+this.regionalDisease+")");
	}
	
	public int getID(){
		return this.id;
	}
	
	public Disease getRegionalDisease(){
		return this.regionalDisease;
	}
	
	
}
