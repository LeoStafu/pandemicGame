
public class Nurse extends Player {

	public Nurse(String n){
		super(n);
	}
	
	public boolean heal(Disease d){	
		if(this.position.condition.containsKey(d)){
			Integer cursor = this.position.condition.get(d);
			if(cursor == 1){ //if disease was at level 1, it disappear
				this.position.condition.remove(d);
			}else{
				this.position.condition.put(d, cursor-1);
			}
			System.out.println("Disease" +d+" healed in "+this.position.name+" by "+this.name+"(player "+this.getID()+")");
			return true;
		}else{
			System.out.println("Chosen disease is absent in this city");
			return false;
		}
	}
	
}
