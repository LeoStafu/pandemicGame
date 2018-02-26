
public class Distributor extends Player {

	public Distributor(String n){
		super(n);
	}
	
	public boolean moveTo(Player subject, Player dest){
		if(subject.position != dest.position){
			subject.position = dest.position;
			System.out.println(subject.name+"(player "+subject.getID()+") joined "+dest.name+"(player "+dest.getID()+") in "+dest.position.name);
			return true;
		}else{
			System.out.println("Both player are already in the same city");
			return false;
		}
	}
	
	
	
}
