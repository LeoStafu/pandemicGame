import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;


public class pandemicLeo {

	public static void main(String[] args){
		
		Scanner scanner = new Scanner(System.in);
		
		Set<Player> allPlayers = new HashSet<Player>() ;
		
		Graph board = new GraphAdjacent("dataPandemic.txt");
		//Graph board =new GraphMatrix("dataPandemic.txt");
		
		//Verification
		/*for (City c : ((GraphAdjacent) board).get_allCities()) {
		    System.out.println(c);
		}
		for (CityAdjacent c : ((GraphAdjacent) board).get_allCities()) {
		    if(c.name.equals("londres")){
		    	for(CityAdjacent cc : c.neighbours){
		    		System.out.println(cc);		
		    	}
		    }
		}*/
		
		//Initialize PLAYER
		initPlayers(allPlayers, board, scanner);
		Disease.initExpansion((GraphAdjacent) board);
		
		//Initialize BOARD and CITIES POSITION
		VueGenerale v;
		v = new VueExemple("Images/pandemicExemple.jpg",1200,800);
		for (City c : ((GraphAdjacent) board).get_allCities()) {
			 v.setCase(c.name, c.posX, c.posY);
		}	
		refreshPrint(allPlayers,(GraphAdjacent) board, (VueExemple) v);
		
		//MAIN LOOP
		do{
			for(Player p : allPlayers){
				parseAction(p, board, scanner, allPlayers, (VueExemple) v);
			}
			Disease.DiseaseTurn((GraphAdjacent) board);
			refreshPrint(allPlayers,(GraphAdjacent) board, (VueExemple) v);
		    
		}while(victoryCheck((GraphAdjacent) board) == "none");
			
		scanner.close();
		return;
	}
		
	
	private static void initPlayers(Set<Player> allP, Graph board, Scanner scanner){
		int nbPlayer = 2;
		System.out.println("Players registration\nEnter the number of players");
		try{
			nbPlayer = scanner.nextInt();
		}catch (InputMismatchException e){
			System.out.println("Invalid input. By default 2 players");
		}
		for(int i = 1; i <= nbPlayer; i++){
			System.out.println("Player "+i+" name ? ");
			String name = scanner.next();
			scanner.nextLine();
			System.out.println("Player "+i+" role ? (Nurse/Distributor/Doctor)");
			String role = scanner.nextLine();
			role = role.toLowerCase();
			System.out.println("!!!"+role+"!!!");
			if(role.equals("doctor")){
				allP.add(new Doctor(name));
			}else if(role.equals("distributor")){
				allP.add(new Distributor(name));
			}else{
				allP.add(new Nurse(name));
			}	
		}
		for(City c : ((GraphAdjacent) board).get_allCities()){
			if(c.name.equals("paris")){
				for(Player p : allP){
					p.position = (CityAdjacent) c;
				}
			}
		}
		
	}
	
	private static void parseAction(Player p, Graph board, Scanner scanner, Set<Player> allPlayers, VueExemple v){
		
		String action;
		System.out.println("\n======= Turn of "+p.name+"(player "+p.getID()+") =======");
		System.out.println(p.name +" is in "+p.position.name);
		int actionDone = 0;
		ImageSimple pawn = new ImageSimple("Images/pin.png",25,25);
		while(actionDone != 4){
			action = scanner.nextLine();
			action = action.toLowerCase();
			String[] tokens = action.split("\\s");
			tokens[0] = tokens[0].toLowerCase();
			
			try{
			//Parsing of action
				if(tokens[0].equals("pass")){
					actionDone = 4;
				}else if(tokens[0].equals("move")){
					if(tokens.length < 2){
						throw new CommandException();
					}
					tokens[1] = tokens[1].toLowerCase();
					for(CityAdjacent c : ((GraphAdjacent) board).get_allCities()){
						if(c.name.toLowerCase().equals(tokens[1])){
							String oldPos = c.name;
							if(p.move(c)){
								v.deplace(pawn, oldPos, c.name);
								actionDone = actionDone +1;
							}
						}
					}
				}else if(tokens[0].equals("heal") && (p instanceof Nurse)){
					if(tokens.length < 2){
						throw new CommandException();
					}
					for(Disease d : Disease.values()){
						if(d.name().toLowerCase().equals(tokens[1])){
							if(((Nurse) p).heal(d)){
								actionDone = actionDone +1;
							}
						}
					}
				}else if(tokens[0].equals("moveto") && (p instanceof Distributor)){
					if(tokens.length < 3){
						throw new CommandException();
					}
					Player dest = null, source = null;
					for(Player pp : allPlayers){
						if(pp.name.toLowerCase().equals(tokens[1])){
							source = pp;
						}
						if(pp.name.toLowerCase().equals(tokens[2])){
							dest = pp;
						}
						if(source != null && dest != null && !dest.equals(source)){
							if(((Distributor) p).moveTo(source, dest)){
								v.deplace(pawn, source.position.name, dest.position.name);
								actionDone = actionDone +1;
							}
						}
					}
				}else if(p instanceof Doctor){
					if(tokens[0].equals("showresearch")){
						((Doctor) p).showResearch();
					}
					if(tokens[0].equals("study")){
						if(((Doctor) p).study((GraphAdjacent) board)){
							actionDone = actionDone +1;
						}
					}
					if(tokens[0].equals("cure")){
						if(tokens.length < 2){
							throw new CommandException();
						}
						for(Disease d : Disease.values()){
							if(d.name().toLowerCase().equals(tokens[1])){
								if(((Doctor) p).cure(d, (GraphAdjacent) board )){
									actionDone = actionDone +1;
								}
							}
						}
					}
					if(tokens[0].equals("forget")){
						if(tokens.length < 2){
							throw new CommandException();
						}
						for(Disease d : Disease.values()){
							if(d.name().toLowerCase().equals(tokens[1])){
								if(((Doctor) p).forget(d)){
									actionDone = actionDone +1;
								}
							}
						}
					}
					if(tokens[0].equals("exchange")){
						if(tokens.length < 4){
							throw new CommandException();
						}
						Disease dSource = null, dTarget = null;
						for(Disease d : Disease.values()){
							if(d.name().toLowerCase().equals(tokens[1])){
								dSource = d;
							}
							if(d.name().toLowerCase().equals(tokens[2])){
								dTarget = d;
							}
						}
						for(Player pp : allPlayers){
							if(pp.name.toLowerCase().equals(tokens[3])){
								if(((Doctor) p).exchange(dSource, dTarget, pp)){
									actionDone = actionDone +1;
								}
							}
						}
					}
				}
			}catch (CommandException e){
				
			}
			//call refresh function
			refreshPrint(allPlayers,(GraphAdjacent) board, v);
			System.out.println((4-actionDone)+" actions left");
		}
		System.out.println("======= "+p.name+"(player "+p.getID()+") finished his turn =======");
		
		
	}
	
	private static String victoryCheck(GraphAdjacent board){
		int nbOutbreak = 0;
		boolean worldClean = true;
				
		for (City c : board.get_allCities()){
			for(Disease d : c.condition.keySet()){
				if(!(c.condition.isEmpty())){
					worldClean = false;
				}
				if(c.condition.get(d) >=3)
				nbOutbreak = nbOutbreak + 1;
			}
		}
		if(worldClean){
			System.out.println("\n=======\nPLAYERS WIN :) !\n=======\n");
			return("player");
		}
		if(nbOutbreak >=7){
			System.out.println("\n=======\nDISEASE WIN :( !\n=======\n");
			return("disease");
		}
		return("none");
		
	}
	
	private static void refreshPrint(Set<Player> allP, Graph board, VueExemple v){
		//refresh disease numbers
		int level = 0;
		String filename;
		ImageSimple number;
		v.getPan().removeAll();
		for(Player p : allP){
			ImageSimple pawn = new ImageSimple("Images/pin.png",25,25);
			v.positionneEnVille(p.position.name, pawn);
		}
		
		for (City c : ((GraphAdjacent) board).get_allCities()) {
			for(Disease d : c.condition.keySet()){
				level = c.condition.get(d);
				filename =  "Images/Chiffres/" + Integer.toString(level) + d.name().toLowerCase()+".png";
				number = new ImageSimple(filename,25,25);
				v.putNumber(c.name, number, d);
			}	
		}
		v.getFen().setContentPane(v.getPan());
		v.getFen().setVisible(true);
	}

}
