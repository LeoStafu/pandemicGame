import java.util.Random;
import java.util.Set;

enum Disease {
	Blue, Black, Yellow, Red;
		
	public static Disease getRandomDisease() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
	
	//Manage expansion of each disease
	public static void DiseaseTurn(GraphAdjacent board){
		
		for(Disease d : Disease.values()){
			for(int i = 0; i <3; i++){
				int action = new Random().nextInt(3);
				if(action == 0){
					Disease.expansion(d, board, false);
				}else{
					Disease.expansion(d, board, true);
				}
			}
		}
    }
	
	public static boolean expansion(Disease d, GraphAdjacent board, boolean regional){
		Set<CityAdjacent> cities = board.get_allCities();
		
		int size = cities.size();
		int idCity = new Random().nextInt(size);
		
		for(CityAdjacent c : cities){
		    if(c.getID()==idCity){
		    	if(regional){
		    		d = c.getRegionalDisease();
		    	}
		    	if(board.get_curedDiseases().contains(d)){
		    		//disease cured, pass turn
		    		return true;
		    	}
		    	if(c.condition.containsKey(d)){
					Integer cursor = c.condition.get(d);
					if(cursor >=3){ 
						int choice = new Random().nextInt(2);
						if(choice == 0){
							System.out.println("Disease" +d+" exploded in "+c.name);
							Disease.explosion(c, d);
							return true;
						}
						System.out.println("Disease" +d+" grew in "+c.name);
						c.condition.put(d, cursor+1);
					}else{
						System.out.println("Disease" +d+" grew in "+c.name);
						c.condition.put(d, cursor+1);
					}
		    	}else{
		    		System.out.println("Disease" +d+" started in "+c.name);
		    		c.condition.put(d, 1);
		    	}
		    	return true;
		    }
		}
		return false;
	}

	public static void explosion(CityAdjacent c, Disease d) {
		for(CityAdjacent n : c.neighbours){
			if(n.condition.containsKey(d)){
				Integer cursor = n.condition.get(d);
				if(cursor != 9){
					n.condition.put(d, cursor+1);
					System.out.println("Disease" +d+" grew in "+c.name);
				}
			}else{
	    		n.condition.put(d, 1);
	    		System.out.println("Disease" +d+" started in "+c.name);
	    	}
		}
	}
	
	
	public static void initExpansion(GraphAdjacent board){
		Set<CityAdjacent> cities = board.get_allCities();
		
		int size = cities.size();
		int idCity = 0;
		Disease d = Disease.getRandomDisease() ;
		for(int level =1; level < 4; level ++){
			for(int nb =0; nb <3; nb++){
				idCity = new Random().nextInt(size);
				for(CityAdjacent c : cities){
				    if(c.getID()==idCity){
				    	System.out.println("Disease" +d+" started in "+c.name);
				    	c.condition.put(d, level);
				    }
				}
				d = Disease.getRandomDisease() ;
			}
		}
	}

	
}



