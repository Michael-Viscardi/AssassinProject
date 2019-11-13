
/**
 * @author Michael Viscardi
 * @version 1.3 Revised Edition
 * AssassinManager for Data Structures Second Period.
 * This class will allow you to Manage a game of Assassin in conjunction with AssassinMain.
 * 
 * Public methods include:
 * AssassinManager(List<String> names) - constructor for the class
 * printKillRing() - prints current living players and who they're stalking
 * printGraveYard() - prints current dead players and who killed them.
 * killRingContains(String name) - returns a boolean representing if name is still alive.
 * graveyardContains(String name) - returns a boolean representing if name has been assassinated.
 * gameOver() - returns a boolean representing if the game is over.
 * winner() - returns the name of the winner of the game.
 * kill(String name) - kills name, moving them from the killRing to the Graveyard.
 * 
 * private methods include:
 * addToGraveyard(AssassinNode addition) - Should not be used out of the context of this class.
 * Adds addition to the front of the graveyard.
 * 
 */
import java.util.*;

public class AssassinManager {

	//killRing is NOT circular in my program. (It is null-terminating)
	private AssassinNode killRing;
	private AssassinNode graveyard;

	// The Constructor - names is the input List object.
	// Precondition: names must not be empty. Throws an IllegalArgumentException if
	// names has no elements or is null.
	// Postcondition: A new AssassinManager is constructed.
	public AssassinManager(List<String> names) {
		if (names == null || names.size() == 0) {
			throw new IllegalArgumentException("input list is empty or null.");
		}

		this.killRing = new AssassinNode(names.get(0));//Initializes killRing to first name in list
		AssassinNode current = this.killRing;
		// The following loop initializes the rest  of the AssassinNodes for the remaining names.
		for (int nameCount = 1; nameCount < names.size(); nameCount++) { 
			current.next = new AssassinNode(names.get(nameCount));
			current = current.next;
		}

	}
	
	//printKillRing
	// Precondition: AssassinManager object has been created to call this method on.
	// Postcondition: Prints killRing according to the specified format. Outputs
	// [NAME] is stalking [NAME] if the game has been won.
	public void printKillRing() {
		AssassinNode current = this.killRing;
		if (this.gameOver()) {
			System.out.println("\t" + this.winner() + " is stalking " + this.winner());
		} else {
			while (current.next != null) {
				System.out.println("\t" + current.name + " is stalking " + current.next.name);
				current = current.next;
			}
			//Prints last stalking because list null-terminates.
			System.out.println("\t" + current.name + " is stalking " + this.killRing.name);

		}
	}

	//printGraveyard
	// Precondition: AssassinManager object has been created to call this method on.
	// Postcondition: Prints graveyard according to the specified format. Outputs
	// nothing if the graveyard is empty.
	public void printGraveyard() {
		AssassinNode current = this.graveyard;
		while (current != null) {
			System.out.println("\t" + current.name + " was killed by " + current.killer);
			current = current.next;
		}
	}

	//killRingContains - name is the String being searched for.
	// Precondition: AssassinManager object has been created to call this method on.
	// Postcondition: Returns a boolean representing if the name is in killRing. Ignores case.
	public boolean killRingContains(String name) {
		boolean contained = false;
		AssassinNode current = this.killRing;
		while (current != null) {				//Loop to search for name in killRing.
			if (current.name.equalsIgnoreCase(name)) {
				contained = true;
			}
			current = current.next;
		}
		return contained;
	}

	//graveYardContains - name is the String being searched for.
	// Precondition: AssassinManager object has been created to call this method on.
	// Postcondition: Returns a boolean representing if name is in graveyard. Ignores case.
	public boolean graveyardContains(String name) {
		boolean contained = false;
		AssassinNode current = this.graveyard;
		while (current != null) {				//Loop to search for name in graveyard.
			if (current.name.equalsIgnoreCase(name)) {
				contained = true;
			}
			current = current.next;
		}
		return contained;
	}

	//gameOver
	// Precondition: AssassinManager object has been created to call this method on.
	// Postcondition: Returns a boolean representing if the game is over or not.
	public boolean gameOver() {
		return this.killRing.next == null;
	}

	//winner
	// Precondition: AssassinManager object has been created to call this method on.
	// Postcondition: Returns the Assassin winner's name when over, returns null
	// otherwise.
	public String winner() {
		if (this.gameOver()) {
			return this.killRing.name;
		} else {
			return null;
		}
	}

	//kill - name is the name of the person you want to kill.
	
	// Preconditions: The game must still be running and name must be = .name field
	// of one of the Assassin Nodes. If the game is over,
	// this method throws an IllegalStateException. If name is not in killRing then
	// this method throws an IllegalArgumentException.
	
	// Postcondition: The AssassinNode with the .name field = name is moved to the
	// graveyard after being removed from the killRing.
	// The order of the killRing is preserved.
	public void kill(String name) {
		if (this.gameOver()) {		//First precondition test
			throw new IllegalStateException("Game is over. Cannot kill winner.");
		} else {
			AssassinNode current = this.killRing;
			if (!this.killRingContains(name)) {	//Second precondition test.
				throw new IllegalArgumentException("Name is not in killRing");
			} else {
				current = this.killRing;
				AssassinNode addition = this.killRing; // Addition is the person that was killed.
				if (addition.name.equalsIgnoreCase(name)) {
					while (current.next != null) { 		//If the person killed was the first in the
						current = current.next; 		//killRing, this loop finds the name of the
					} 									//last person for killer.
					addition.killer = current.name;
					this.killRing = addition.next;
					addToGraveyard(addition);
				} else {
					current = this.killRing.next;
					AssassinNode killer = this.killRing;
					boolean found = false;
					while (current != null && !found) { // If the person killed was not the first
														// in the killRing, this loop finds who
														// was killed and then their killer.
						if (current.name.equalsIgnoreCase(name)) {
							found = true;
							addition = current;
							addition.killer = killer.name;
							if (addition.next == null) { // Edge case that the person being killed
								killer.next = null;		 // is the last one in the killRing.
							} else {
								killer.next = killer.next.next;	//Removes victim from killRing.
							}
							addToGraveyard(addition);
						} else {
							current = current.next;		//Iterating current and killer.
							killer = killer.next;
						}
					}
				}
			}
		}
	}

	// Postcondition: adds input to graveyard. This method is only used to prevent
	// redundant code that adds the person killed to the graveyard.
	// It should not be used outside of the context of this class.
	private void addToGraveyard(AssassinNode input) {
		input.next = null;
		if (this.graveyard == null) {	//Adds to front of graveyard if graveyard is empty.
			this.graveyard = input;
		} else {						//Adds to front of graveyard, moving all values down.
			AssassinNode temp = this.graveyard;
			this.graveyard = input;
			this.graveyard.next = temp;
		}
	}

}
