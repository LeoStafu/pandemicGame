import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class GraphAdjacent implements Graph {
	
	private Set<CityAdjacent> allCities = new HashSet<CityAdjacent>();
	private Set<Disease> curedDiseases = new HashSet<Disease>();
	/**
	 * This function will parse the file containing all the cities names, color, position and links
	 * @param a filename 
	 * @return nothing, it only modify the allCities Set attribute of the object
	 */
	public GraphAdjacent(String filename) {
		int nbCities = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			if((line = br.readLine()) != null) {
				nbCities = Integer.parseInt(line);
			}
			line = br.readLine(); //read line with window size
			for(int i=0; i < nbCities; i++){
				line = br.readLine();
				String[] tokens = line.split("\\s*[#,}{]\\s*[{]?\\s?");
				CityAdjacent c = new CityAdjacent(tokens[0].toLowerCase(), CityAdjacent.nbCity, Disease.valueOf(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
				allCities.add(c);
			}
			//loop till the end : add neighbours
			while((line = br.readLine()) != null){
				String[] tokens = line.split("\\s*##\\s*");
				CityAdjacent c1 = null, c2 = null;
			    for(CityAdjacent c : allCities){
			    	if(c.name.equals(tokens[0].toLowerCase()))
			    		c1 = c;
			    	else if(c.name.equals(tokens[1].toLowerCase()))
			    		c2 = c;
			    	}
			    	if(c1 != null && c2 != null){
			    		c1.neighbours.add(c2);
			    		c2.neighbours.add(c1);
			    	}
			 	} 
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Set<CityAdjacent> get_allCities(){
		return allCities;
	}
	
	public Set<Disease> get_curedDiseases(){
		return curedDiseases;
	}

}
