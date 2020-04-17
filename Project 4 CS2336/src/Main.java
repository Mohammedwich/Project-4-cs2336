//Mohammed Ahmed, msa190000

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;


//Each line in the galaxy file will contain a vertex and a list of edges with weights. example:  Name otherName,5 otherName2,3
//Each line in the routes file will contain the pilot’s name followed by a list of planets. ex: Pilot planet1 planet2

public class Main
{

	public static void main(String[] args) throws FileNotFoundException
	{
		String galaxyFileName;
		String routesFileName;
		Scanner inputReader = new Scanner(System.in);
		
		System.out.println("Enter the galaxy file name: ");
		galaxyFileName = inputReader.nextLine();
		
		System.out.println("Enter the routes file name: ");
		routesFileName = inputReader.nextLine();
		
		File galaxyFile = new File(galaxyFileName);
		File routesFile = new File(routesFileName);
		
		if(galaxyFile.exists() == false)
		{
			System.out.println("The file " + galaxyFileName + " does not exist.");
			inputReader.close();
			return;
		}
		if(routesFile.exists() == false)
		{
			System.out.println("The file " + routesFileName + " does not exist.");
			inputReader.close();
			return;
		}
		
		//-----------------------------------------------------------
		
		Scanner galaxyReader = new Scanner(galaxyFile);
		Scanner routesReader = new Scanner(routesFile);
		
		//Will hold galaxy names so we can use their indices to identify them. 
		//These indices will be used as the index for the respective planet in the 2D adjacency matrix for the planets
		ArrayList<String> galaxyList = new ArrayList<String>(); 
		
		//add all planet names to the list
		while(galaxyReader.hasNextLine())
		{
			String currentLine = galaxyReader.nextLine();
			Scanner lineReader = new Scanner(currentLine);
			
			galaxyList.add(lineReader.next());
			lineReader.close();
		}
		
		galaxyReader.reset(); //reset the scanner so we can process the whole file now
		
		//Each planet will have a row of its own represented by its index from galaxyNamesList. Each row will contain an integer
	// representing the distance between the planet of that row and the planet represented by the index of the column this integer is in
		ArrayList<ArrayList<Integer>> adjacencyMatrix = new ArrayList<ArrayList<Integer>>();
		
		//Fill the list so we have an empty(zero initialized) Size x Size matrix so they exist so we can reference those indices later
		for(int i = galaxyList.size(); i > 0; --i)
		{
			ArrayList<Integer> theList = new ArrayList<Integer>(galaxyList.size()); 
			for(int j = 0; j < galaxyList.size(); ++j)
			{
				theList.add(0);
			}
			
			adjacencyMatrix.add(theList);
		}
		
			
		int currentRowIndex = 0;
		
		while(galaxyReader.hasNextLine())
		{
			String currentLine = galaxyReader.nextLine();
			currentLine = currentLine.replace(",", " "); //replace all commas with space so we can pick the planet and the weight individually
			Scanner lineReader = new Scanner(currentLine);
			
			lineReader.next(); //skip the planet name. We only need its list of adjacent planets to put into the current row in the matrix 
						
			while(lineReader.hasNext())
			{
				String planetName = lineReader.next(); //The neighbor planet
				String weightAsString = lineReader.next();
				int weight = Integer.parseInt(weightAsString);
				
				int planetIndex = galaxyList.indexOf(planetName); //The neighbor planet
				
				adjacencyMatrix.get(currentRowIndex).set(planetIndex, weight); 
			}
			
			lineReader.close();
		}
		
		
		
		
		inputReader.close();
		galaxyReader.close();
		routesReader.close();
	}

}
