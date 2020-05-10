package PA2;

import java.util.ArrayList;
import java.util.*;

public class Program2 {
    private ArrayList<City> cities;     //this is a list of all cities, populated by Driver class.
    private Heap minHeap;

    // feel free to add any fields you'd like, but don't delete anything that's already here

    public Program2(int numCities) {
        minHeap = new Heap();
        cities = new ArrayList<City>();
    }

    /**
     * findCheapestPathPrice(City start, City dest)
     *
     * @param start - the starting city.
     * @param dest  - the end (destination) city.
     * @return the minimum cost possible to get from start to dest.
     * If no path exists, return Integer.MAX_VALUE
     */
    public int findCheapestPathPrice(City start, City dest) {
        // TODO: implement this function
    	Heap HeapMap = new Heap();
    	ArrayList<City> result = new ArrayList<>();
 
    	Map<City, Integer> distance = new HashMap<>();
    	Map<City, City> parent = new HashMap<>();
    	for(City vertex : cities) {
    		vertex.setMinCost(Integer.MAX_VALUE);
    		vertex.parent = null;
    		HeapMap.insertNode(vertex);
    	}
    	HeapMap.changeKey(start, 0);
    	distance.put(start, 0);
    	parent.put(start,null);
    	while(!HeapMap.isEmpty()) {
    		City u = HeapMap.extractMin();
    		distance.put(u, u.getMinCost());
    		result.add(u);
    		int index = 0;
    		for(City c : u.getNeighbors()) {
    			if(!HeapMap.contains(c)) {
    				index++;
    				continue;
    			}
    			int newDistance = u.getMinCost() + u.getWeights().get(index);
    			if(c.getMinCost() > newDistance) {
    				HeapMap.changeKey(c, newDistance);
    				parent.put(c, u);
    			}
    			index++;
    		}
    	}
        return distance.get(dest);
    }

    /**
     * findCheapestPath(City start, City dest)
     *
     * @param start - the starting city.
     * @param dest  - the end (destination) city.
     * @return an ArrayList of nodes representing a minimum-cost path on the graph from start to dest.
     * If no path exists, return null
     */
    public ArrayList<City> findCheapestPath(City start, City dest) {
        // TODO: implement this function
    	Heap HeapMap = new Heap();
    	ArrayList<City> result = new ArrayList<>();
    	result.clear();
 
    	Map<City, Integer> distance = new HashMap<>();
    	Map<City, City> parent = new HashMap<>();
    	for(City vertex : cities) {
    		vertex.setMinCost(Integer.MAX_VALUE);
    		vertex.parent = null;
    		HeapMap.insertNode(vertex);
    	}
    	HeapMap.changeKey(start, 0);
    	distance.put(start, 0);
    	parent.put(start,null);
    	while(!HeapMap.isEmpty()) {
    		City u = HeapMap.extractMin();
    		distance.put(u, u.getMinCost());
    		int index = 0;
    		for(City c : u.getNeighbors()) {
    			if(!HeapMap.contains(c)) {
    				index++;
    				continue;
    			}
    			int newDistance = u.getMinCost() + u.getWeights().get(index);
    			if(c.getMinCost() > newDistance) {
    				HeapMap.changeKey(c, newDistance);
    				parent.put(c, u);
    			}
    			index++;
    		}
    	}
    	City current = dest;
    	ArrayList<City> temp = new ArrayList<>();
    	while(current != start && current != null) {
    		temp.add(current);
    		current = parent.get(current);
    	}
    	temp.add(current);
    	for(int i = temp.size()-1; i >= 0 ; i--) {
    		result.add(temp.get(i));
    	}
        return result;
    }

    /**
     * findLowestTotalCost()
     *
     * @return The sum of all edge weights in a minimum spanning tree for the given graph.
     * Assume the given graph is always connected.
     * The government wants to shut down as many tracks as possible to minimize costs.
     * However, they can't shut down a track such that the cities don't remain connected.
     * The tracks you're leaving open cost some money (aka the edge weights) to maintain. Minimize the overall cost.
     */
    public int findLowestTotalCost() {
        // TODO: implement this function
    	Heap HeapMap = new Heap();
    	Map<City, City> parent = new HashMap<>();
    	ArrayList<City> result = new ArrayList<>();
    	for(City vertex : cities) {
    		vertex.setMinCost(Integer.MAX_VALUE);
    		vertex.parent = null;
    		HeapMap.insertNode(vertex);
    	}
    	City start = cities.get(0);
    	HeapMap.changeKey(start, 0);
    	while(!HeapMap.isEmpty()) {
    		City u = HeapMap.extractMin();
    		City spanningEdge = parent.get(u);
    		if(spanningEdge != null) {
    			result.add(spanningEdge);
    		}
    		int index = 0;
    		for(City c : u.getNeighbors()) {
    			
    			if(!HeapMap.contains(c)) {
    				index++;
    				continue;
    			}
    			if(c.getMinCost() >= u.getWeights().get(index)) {
    				HeapMap.changeKey(c, u.getWeights().get(index));
    				parent.put(c, u);
    			}
    			index++;
    		}
    		
    		if(HeapMap.isEmpty()) {
		    	result.add(u);
			}
    	}
    	int sum = 0;
    	for(City c : result) {
    		sum += c.getMinCost();
    	}
        return sum;
    }

    //returns edges and weights in a string.
    public String toString() {
        String o = "";
        for (City v : cities) {
            boolean first = true;
            o += "City ";
            o += v.getCityName();
            o += " has neighbors: ";
            ArrayList<City> ngbr = v.getNeighbors();
            for (City n : ngbr) {
                o += first ? n.getCityName() : ", " + n.getCityName();
                first = false;
            }
            first = true;
            o += " with weights ";
            ArrayList<Integer> wght = v.getWeights();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<City> getAllCities() {
        return cities;
    }

    //used by Driver class to populate each Node with correct neighbors and corresponding weights
    public void setEdge(City curr, City neighbor, Integer weight) {
        curr.setNeighborAndWeight(neighbor, weight);
    }

    //This is used by Driver.java and sets vertices to reference an ArrayList of all nodes.
    public void setAllNodesArray(ArrayList<City> x) {
        cities = x;
    }
}
