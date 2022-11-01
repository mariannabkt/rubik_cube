import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		// Insert number of final correct sides from user
		System.out.print("Insert the number of faces of the cube you want to be completed: ");
		Scanner in = new Scanner(System.in);
		int k = in.nextInt();
		in.close();
		
		//Initializations
		State initialState = new State (k);
		initialState.print();
		SpaceSearcher searcher = new SpaceSearcher();
		
		// Use the Manhattan distance. ++++++++++++++++++++++++FIX+++++++++++++++++++++++
		State terminalState = searcher.AStarClosedSet(initialState, 2);
		
		if(terminalState == null) 
		{
			System.out.println("Could not find a solution.");
		}
		else
		{
			// Get the path from end to start.
			State temp = terminalState;
			ArrayList<State> path = new ArrayList<>();
			path.add(terminalState);
			
			while(temp.getFather() != null)
			{
				path.add(temp.getFather());
				temp = temp.getFather();
			}
			
			// Reverse the path and print.
			Collections.reverse(path);
			for(State item: path)
			{
				item.print();
			}
		}
	}
}
