import java.util.HashMap;
import java.util.Iterator;



public class Doctor extends Player {

	HashMap<Disease, Integer> research;
	
	public Doctor(String n){
		super(n);
		this.research = new HashMap <Disease, Integer>();
	}
	
	public void showResearch(){
		System.out.println("Current research of "+this.name+"(player "+this.getID()+") :");
		Iterator<Disease> iter = this.research.keySet().iterator();
		while(iter.hasNext()){
			Disease key = iter.next();
			System.out.println("Disease: " + key + " - Research level : " +  this.research.get(key));
		}
	}
	
	private int getResearchSize(){
		int res = 0;
		Iterator<Disease> iter = this.research.keySet().iterator();
		while(iter.hasNext()){
			Disease key = iter.next();
			res = res + this.research.get(key);
		}
		return res;
	}
	
	public boolean study(GraphAdjacent board){
		if(this.getResearchSize() > 6){
			System.out.println("Too much research, you must forgot one of them to study more.");
			return false;
		}else{
			Disease d = Disease.getRandomDisease();
			//MAKE study easier
			/*while(board.get_curedDiseases().contains(d)){
				d = Disease.getRandomDisease();
			}*/
			System.out.println(this.name+"(player "+this.getID()+") discovered new stuff on the "+d+" disease");
			Integer cursor = this.research.get(d);
			if(this.research.containsKey(d)){
				this.research.put(d, cursor+1);
			}else{
				this.research.put(d, 1);
			}
			return true;
		}
	}
	
	public boolean cure(Disease d, GraphAdjacent board){
		if(this.research.containsKey(d)){
			Integer cursor = this.research.get(d);
			if(cursor >= 5){ //if research are at level 5, the player can cure the related disease
				board.get_curedDiseases().add(d);
				Iterator<CityAdjacent> iter = board.get_allCities().iterator();
				while(iter.hasNext()){
					CityAdjacent c = iter.next();
					c.condition.remove(d);
				}
				this.research.remove(d);
				System.out.println(this.name+"(player "+this.getID()+") totally cured the "+d+" disease");
				return true;
			}
		}
		System.out.println("More research should be done to cure this disease ");
		return false;	
	}
	
	public boolean forget(Disease d){
		if(this.research.containsKey(d)){
			Integer cursor = this.research.get(d);
			if(cursor == 1){
				this.research.remove(d);
			}else{
				this.research.put(d, cursor-1);
			}
			System.out.println(this.name+"(player "+this.getID()+") forgot a part of his research on the "+d+" disease");
			return true;
		}else{
			System.out.println("Cannot forgot a research that has not been done yet !");
			return false;
		}
	}
	
	public boolean exchange(Disease dSource, Disease dTarget, Player targ){
		if(!(targ instanceof Doctor)){
			System.out.println("Exchange not possible. Target is no Doctor !");
			return false;
		}
		if(this.equals(targ)){
			System.out.println("Exchange not possible. You can't do it with yourself !");
			return false;
		}
		Doctor target = (Doctor)targ;
		
		if(this.research.containsKey(dSource) && target.research.containsKey(dTarget)){
			//source gives its research
			Integer cursor = this.research.get(dSource);
			if(cursor != 1){
				this.research.put(dSource, cursor-1);
			}else{
				this.research.remove(dSource);
			}
			cursor = target.research.get(dSource);
			if(cursor != null){
				target.research.put(dSource, cursor+1);
			}else{
				target.research.put(dSource, 1);
			}
			
			//target gives its research
			cursor = target.research.get(dTarget);
			if(cursor != 1){
				target.research.put(dTarget, cursor-1);
			}
			else{
				target.research.remove(dTarget);
			}
			cursor = target.research.get(dTarget);
			if(cursor != null){
				this.research.put(dTarget, cursor+1);
			}else{
				this.research.put(dTarget,1);
			}
			
			return true;
		}else{
			System.out.println("Exchange not possible with current research");
			return false;
		}
	}
	
}
