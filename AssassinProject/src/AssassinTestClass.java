import java.util.*;
public class AssassinTestClass {

	public static void main(String[] args) {

		
		List<String> names = new ArrayList<String>();
		names.add("Alpha");
		names.add("Bravo");
		
		
		List<String> singlePlayer = new ArrayList<String>();
		singlePlayer.add("Alpha");
		
		List<String> emptyList = new ArrayList<String>();
		
	//	AssassinManager empty = new AssassinManager(emptyList);
		
		AssassinManager testMe = new AssassinManager(names);
		testMe.printKillRing();
		
		AssassinManager testWinner = new AssassinManager(singlePlayer);
		
		System.out.println(testMe.killRingContains("Alpha"));
		System.out.println(testMe.gameOver());
		System.out.println("\n\n");
		
		testMe.printGraveyard();
		testWinner.printKillRing();
		
		//empty.printGraveyard();
	}

}

//	CONSTRUCTOR WORKS
//	printKillRing() WORKS
//	killRingContains(String name) WORKS
//	graveyardContains(String name) WORKS
//	gameOver() WORKS
//	winner() WORKS

//TODO: kill(), printGraveyard().