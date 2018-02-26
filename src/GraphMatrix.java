import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;


public class GraphMatrix implements Graph {

	private boolean links[][];
	private Set<CityMatrix> allCities;
	
	public GraphMatrix(String filename){
		
		int nbCities = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			if((line = br.readLine()) != null) {
				nbCities = Integer.parseInt(line);
			}
			links = new boolean[nbCities][nbCities];
			for(int i = 0; i<nbCities; i++){
				for(int j = 0; j<nbCities; j++){
					links[i][j] = false;
				}
			}
			// links [a][b] is true is b and a neighbours
			
			line = br.readLine(); //read line with window size
			for(int i=0; i < nbCities; i++){
				line = br.readLine();
				String[] tokens = line.split("\\s*[#,}{]\\s*[{]?\\s?");
				CityMatrix c = new CityMatrix(tokens[0], CityMatrix.nbCity, Disease.valueOf(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
				allCities.add(c);
			}
			//loop till the end : add neighbours
			while((line = br.readLine()) != null){
				String[] tokens = line.split("\\s*##\\s*");
				CityMatrix c1 = null, c2 = null;
			    for(CityMatrix c : allCities){
			    	if(c.name.equals(tokens[0]))
			    		c1 = c;
			    	else if(c.name.equals(tokens[1]))
			    		c2 = c;
			    	}
			    	if(c1 != null && c2 != null){
			    		links[c1.id][c2.id] = true;
			    		links[c2.id][c1.id] = true;
			    	}
			 	} 
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Set<CityMatrix> get_allCities() {
		return allCities;
	}
	
	public boolean[][] get_links() {
		return links;
	}

}
