
public class Player {

	public CityAdjacent position;
	public String name;
	private final int id;
	
	static int nbPlayers = 0;

	public Player(String n){
		nbPlayers++;
		this.name = n;
		this.id = nbPlayers;
	}
	
	public boolean move(CityAdjacent dest){

		if(this.position == dest){
			System.out.println("Player already in the city");
			return false;
		}else if(this.position.neighbours.contains(dest)){
			System.out.println(this.name+"(player "+this.getID()+") moved to "+dest.name);
			this.position = dest;
			return true;
		}else{
			System.out.println("The city is too far to move to it in one turn");
			return false;
		}
	}
	
	public int getID(){
		return this.id;
	}

	
	
	
	
}
