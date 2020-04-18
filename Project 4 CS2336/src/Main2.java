//Mohammed Ahmed, msa190000

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


//Each line in the galaxy file will contain a vertex and a list of edges with weights. example:  Name otherName,5 otherName2,3
//Each line in the routes file will contain the pilot’s name followed by a list of planets. ex: Pilot planet1 planet2

public class Main2
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
		
		File outputFile = new File("patrols2.txt");
		
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
		} // done filling matrix
		
		Graph planetsGraph = new Graph(galaxyList, adjacencyMatrix);
		
		ArrayList<Patrol> patrolList = new ArrayList<Patrol>(); //will hold each patrol object so we can sort then write them to output
		
		while(routesReader.hasNextLine())
		{
			ArrayList<String> thePath = new ArrayList<String>(); // will hold each pilot's path for processing
			
			String currentLine = routesReader.nextLine();
			Scanner lineReader = new Scanner(currentLine);
			
			String pilotName = lineReader.next();
			
			while(lineReader.hasNext())
			{
				String thePlanet = lineReader.next();
				thePath.add(thePlanet);
			}
			
			boolean valid = planetsGraph.checkPathValidity(thePath);
			int weight = planetsGraph.getPathWeight(thePath);
			
			Patrol currentPilot = new Patrol(pilotName, weight, valid);
			
			patrolList.add(currentPilot);
			lineReader.close();
		}
		
		Collections.sort(patrolList);
		
		for(Patrol x : patrolList)
		{
			outputWriter.append(x.toString() + "\n");
		}
		
		
		inputReader.close();
		planetsReader.close();
		galaxyReader.close();
		routesReader.close();
		outputWriter.close();
	} // main end
	
//*********************************************************************************8

	// Class to hold data and compare for sorting purposes
	public static class Patrol implements Comparable<Patrol>
	{
		public String name;
		public int weight;
		public boolean validity;
		
		public Patrol()
		{
			name = "";
			weight = 0;
			validity = false;
		}
		
		public Patrol(String theName, int theWeight, boolean theValidity)
		{
			this();
			name = theName;
			weight = theWeight;
			validity = theValidity;
		}
		
		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public int getWeight()
		{
			return weight;
		}

		public void setWeight(int weight)
		{
			this.weight = weight;
		}

		public boolean isValidity()
		{
			return validity;
		}

		public void setValidity(boolean validity)
		{
			this.validity = validity;
		}

		public String toString()
		{
			String result = name + "\t" + weight + "\t";
			
			if(validity == true)
			{
				result = result + "valid";
			}
			else
			{
				result = result + "invalid";
			}
			
			return result;
		}

		
		//compare weights, if weights are equal compare names
		@Override
		public int compareTo(Patrol otherObject)
		{
			if(weight > otherObject.getWeight())
			{
				return 1;
			}
			else if(weight < otherObject.getWeight())
			{
				return -1;
			}
			else if(weight == otherObject.getWeight())
			{
				if(name.compareTo(otherObject.getName()) > 0)
				{
					return 1;
				}
				else if(name.compareTo(otherObject.getName()) < 0)
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
			else
			{
				return 0;
			}
		}
		
	}

}
