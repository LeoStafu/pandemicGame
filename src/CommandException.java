
public class CommandException extends Exception {

	public CommandException(){
		System.out.println("Erreur dans la ligne de commande. Action imcomplete (nombre de paramètres) ou impossible !");
	}
}
