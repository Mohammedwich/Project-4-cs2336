//Mohammed Ahmed, msa190000

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;


//Each line in the galaxy file will contain a vertex and a list of edges with weights. example:  Name otherName,5 otherName2,3
//Each line in the routes file will contain the pilot�s name followed by a list of planets. ex: Pilot planet1 planet2

public class Main
{

	public static void main(String[] args) throws IOException
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
			System.out.println("The file " + galaxyFileName + " does not exist. Program exiting...");
			inputReader.close();
			return;
		}
		if(routesFile.exists() == false)
		{
			System.out.println("The file " + routesFileName + " does not exist. Program exiting...");
			inputReader.close();
			return;
		}
		
		File outputFile = new File("patrols.txt");
		
		outputFile.createNewFile();
		
		if(outputFile.exists() == false)
		{
			System.out.println("The output file  patrols.txt  was not created. Program exiting...");
			inputReader.close();
			return;
		}
		
		//-----------------------------------------------------------
		
		Scanner planetsReader = new Scanner(galaxyFile); // used to collect planet names (first word of each line)
		Scanner galaxyReader = new Scanner(galaxyFile); // used for reading the whole file and processing
		Scanner routesReader = new Scanner(routesFile);
		FileWriter outputWriter = new FileWriter(outputFile);
		
		//Will hold galaxy names so we can use their indices to identify them. 
		//These indices will be used as the index for the respective planet in the 2D adjacency matrix for the planets
		ArrayList<String> galaxyList = new ArrayList<String>(); 
		
		//add all planet names to the list
		while(planetsReader.hasNextLine())
		{
			String currentLine = planetsReader.nextLine();
			Scanner lineReader = new Scanner(currentLine);
			
			galaxyList.add(lineReader.next());
			lineReader.close();
		}
		
		planetsReader.close();
		
		
		//Each planet will have a row of its own represented by its index from galaxyNamesList. Each row will contain an integer
	// representing the distance between the planet of that row and the planet represented by the index of the column this integer is in
		ArrayList<ArrayList<Integer>> adjacencyMatrix = new ArrayList<ArrayList<Integer>>();
		
		while(galaxyReader.hasNextLine())
		{
			//This list will be added to the array after weights are placed in the appropriate positions
			ArrayList<Integer> neighborsList = new ArrayList<Integer>(galaxyList.size()); 
			for(int j = 0; j < galaxyList.size(); ++j)
			{
				neighborsList.add(0);
			}
			
			
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
				
				neighborsList.set(planetIndex, weight); 
			}
			
			adjacencyMatrix.add(neighborsList);
			
			lineReader.close();
		}
		
		
		
		
		
		
		
		inputReader.close();
		planetsReader.close();
		galaxyReader.close();
		routesReader.close();
	} // main end
	
//*********************************************************************************8
	public static boolean checkPathValidity(ArrayList<ArrayList<Integer>> theMatrix, ArrayList<String> planetsList, ArrayList<String> thePath)
	{
		// deal with edge cases
		if(thePath.size() == 0)
		{
			return false;
		}
		if(thePath.size() == 1)
		{
			String singlePlanet = thePath.get(0);
			
			if(planetsList.contains(singlePlanet) == true)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		//---------------------------------------------
		
		boolean result = true;
		int pathSize = thePath.size();
		
		for(int i = 1; i < pathSize; ++i)
		{
			String currentPlanet = thePath.get(i-1);
			String nextPlanet = thePath.get(i);
			
			int currentPlanetIndex = planetsList.indexOf(currentPlanet);
			int nextPlanetIndex = planetsList.indexOf(nextPlanet);
			
			int edgeWeight = theMatrix.get(currentPlanetIndex).get(nextPlanetIndex);
			
			if(edgeWeight == 0) // if there is no edge between the two, the path is invalid and we return false
			{
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	
	public static int getPathWeight(ArrayList<ArrayList<Integer>> theMatrix, ArrayList<String> planetsList, ArrayList<String> thePath)
	{
		if( checkPathValidity(theMatrix, planetsList, thePath) == false )
		{
			return 0;
		}
		
		int result = 0;
		int pathSize = thePath.size();
		
		for(int i = 1; i < pathSize; ++i)
		{
			String currentPlanet = thePath.get(i-1);
			String nextPlanet = thePath.get(i);
			
			int currentPlanetIndex = planetsList.indexOf(currentPlanet);
			int nextPlanetIndex = planetsList.indexOf(nextPlanet);
			
			int edgeWeight = theMatrix.get(currentPlanetIndex).get(nextPlanetIndex);
			
			result += edgeWeight;			
		}
		
		return result;		
	}

}
