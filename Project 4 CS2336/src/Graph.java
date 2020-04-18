import java.util.ArrayList;

public class Graph
{
	ArrayList<String> planetsList;
	ArrayList<ArrayList<Integer>> adjacencyMatrix;
	
	Graph()
	{
		planetsList = new ArrayList<String>();
		adjacencyMatrix = new ArrayList<ArrayList<Integer>>();
	}
	
	Graph(ArrayList<String> thePlanets, ArrayList< ArrayList<Integer>> theMatrix)
	{
		planetsList = thePlanets;
		adjacencyMatrix = theMatrix;
	}
	
	
	public ArrayList<ArrayList<Integer>> getMatrix()
	{
		return adjacencyMatrix;
	}
	
	public void setMatrix(ArrayList<ArrayList<Integer>> theMatrix)
	{
		adjacencyMatrix = theMatrix;
	}
	
	
	public boolean checkPathValidity(ArrayList<String> thePath)
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

		
		boolean result = true;
		int pathSize = thePath.size();
		
		for(int i = 1; i < pathSize; ++i)
		{
			String currentPlanet = thePath.get(i-1);
			String nextPlanet = thePath.get(i);
			
			int currentPlanetIndex = planetsList.indexOf(currentPlanet);
			int nextPlanetIndex = planetsList.indexOf(nextPlanet);
			
			int edgeWeight = adjacencyMatrix.get(currentPlanetIndex).get(nextPlanetIndex);
			
			if(edgeWeight == 0) // if there is no edge between the two, the path is invalid and we return false
			{
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	
	public int getPathWeight(ArrayList<String> thePath)
	{
		if( checkPathValidity(thePath) == false )
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
			
			int edgeWeight = adjacencyMatrix.get(currentPlanetIndex).get(nextPlanetIndex);
			
			result += edgeWeight;			
		}
		
		return result;		
	}
}
