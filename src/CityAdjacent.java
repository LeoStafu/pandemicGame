import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class CityAdjacent extends City{

	public Set <CityAdjacent> neighbours;
	
	static int nbCity = 0;
	
	public CityAdjacent(String n, int id, Disease d, int x, int y ){
		this.name = n;
		this.id = id;
		this.regionalDisease = d;
		this.posX = x;
		this.posY = y;
		this.neighbours = new HashSet<CityAdjacent>();
		this.condition = new HashMap <Disease, Integer>();
		nbCity += 1;
	}
	
	
	public boolean equals(Object o){
		if(o instanceof CityAdjacent){
			CityAdjacent c = (CityAdjacent)o;
			return(c.id == this.id);
		}else{
			return false;
		}
	}
	
}
