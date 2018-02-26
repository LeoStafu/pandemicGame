import java.util.HashMap;


public class CityMatrix extends City {

	static int nbCity = 0;
	
	public CityMatrix(String n, int id, Disease d, int x, int y ){
		this.name = n;
		this.id = id;
		this.regionalDisease = d;
		this.posX = x;
		this.posY = y;
		this.condition = new HashMap <Disease, Integer>();
		nbCity += 1;
	}
	
	public boolean equals(Object o){
		if(o instanceof CityMatrix){
			CityMatrix c = (CityMatrix)o;
			return(c.id == this.id);
		}else{
			return false;
		}
	}
}
