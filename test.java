package PA2;

import PA2.Driver;
import PA2.Program2;

import java.util.ArrayList;
import java.util.*;
import java.io.File;
import java.util.Scanner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;


public class test {
    private static Program2 testProgram2; // instance of your graph
    private static ArrayList<City> cities;
    

    private static class NeighborWeightTuple {
        public Integer neighborID;
        public Integer weight;

        NeighborWeightTuple(Integer neighborID, Integer weight) {
            this.neighborID = neighborID;
            this.weight = weight;
        }
    }

    public static void parseInputFile(String filename)
    throws Exception {
        int numV = 0, numE = 0;
        Scanner sc = new Scanner(new File(filename));
        String[] inputSize = sc.nextLine().split(" ");
        numV = Integer.parseInt(inputSize[0]);
        numE = Integer.parseInt(inputSize[1]);
        HashMap<Integer, ArrayList<NeighborWeightTuple>> tempNeighbors = new HashMap<>();
        testProgram2 = new Program2(numV);
        for (int i = 0; i < numV; ++i) {

            String[] pairs = sc.nextLine().split(" ");
            String[] weightPairs = sc.nextLine().split(" ");

            Integer currNode = Integer.parseInt(pairs[0]);
            City currentCity = new City(currNode);
            cities.add(currNode, currentCity);
            ArrayList<NeighborWeightTuple> currNeighbors = new ArrayList<>();
            tempNeighbors.put(currNode, currNeighbors);

            for (int k = 1; k < pairs.length; k++) {
                Integer neighborVal = Integer.parseInt(pairs[k]);
                Integer weightVal = Integer.parseInt(weightPairs[k]);
                currNeighbors.add(new NeighborWeightTuple(neighborVal, weightVal));
            }
        }
        for (int i = 0; i < cities.size(); ++i) {
            City currCity = cities.get(i);
            ArrayList<NeighborWeightTuple> neighbors = tempNeighbors.get(i);
            for (NeighborWeightTuple neighbor : neighbors) {
                testProgram2.setEdge(currCity, cities.get(neighbor.neighborID), neighbor.weight);
            }
        }

        testProgram2.setAllNodesArray(cities);
    }

    @Test
    public void file1()throws Exception  {
        cities = new ArrayList<City>();
        String filename ="./src/PA2/1.txt";
        parseInputFile(filename);
        assertEquals(1, testProgram2.findCheapestPathPrice(cities.get(0), cities.get(1)));
        ArrayList<City> test = new ArrayList<City>();
        test.add(cities.get(0));
        test.add(cities.get(1));
        assertArrayEquals(test.toArray(), testProgram2.findCheapestPath(cities.get(0), cities.get(1)).toArray());
        assertEquals(3, testProgram2.findLowestTotalCost());
    }
    @Test
    public void file2()throws Exception  {
        cities = new ArrayList<City>();
        String filename ="./src/PA2/2.txt";
        parseInputFile(filename);
        assertEquals(1, testProgram2.findCheapestPathPrice(cities.get(0), cities.get(1)));
        ArrayList<City> test = new ArrayList<City>();
        test.add(cities.get(0));
        test.add(cities.get(1));
        assertArrayEquals(test.toArray(), testProgram2.findCheapestPath(cities.get(0), cities.get(1)).toArray());
        assertEquals(6, testProgram2.findLowestTotalCost());
    }

    @Test
    public void file3()throws Exception  {
        cities = new ArrayList<City>();
        String filename ="./src/PA2/3.txt";
        parseInputFile(filename);
        ArrayList<City> test = new ArrayList<City>();
        test.add(cities.get(0));
        test.add(cities.get(1));

        assertEquals(10, testProgram2.findCheapestPathPrice(cities.get(0), cities.get(1)));
        assertEquals(20, testProgram2.findCheapestPathPrice(cities.get(0), cities.get(2)));
        assertEquals(22, testProgram2.findCheapestPathPrice(cities.get(5), cities.get(2)));
        assertEquals(13, testProgram2.findCheapestPathPrice(cities.get(1), cities.get(3)));
        assertEquals(21, testProgram2.findCheapestPathPrice(cities.get(0), cities.get(5)));
        assertArrayEquals(test.toArray(), testProgram2.findCheapestPath(cities.get(0), cities.get(1)).toArray());
        assertEquals(43, testProgram2.findLowestTotalCost());
    }

    @Test
    public void file4()throws Exception  {
        cities = new ArrayList<City>();
        String filename ="./src/PA2/4.txt";
        parseInputFile(filename);
        ArrayList<City> test = new ArrayList<City>();
        test.add(cities.get(0));   
        for(int i=2;i<8;i++){
            test.add(cities.get(i));
        }
        test.add(cities.get(1));

        assertEquals(7, testProgram2.findCheapestPathPrice(cities.get(0), cities.get(1)));
        assertArrayEquals(test.toArray(), testProgram2.findCheapestPath(cities.get(0), cities.get(1)).toArray());
        assertEquals(7, testProgram2.findLowestTotalCost());
    }

    @Test
    public void file5()throws Exception  {
        cities = new ArrayList<City>();
        String filename ="./src/PA2/5.txt";
        parseInputFile(filename);
        

        assertEquals(2, testProgram2.findCheapestPathPrice(cities.get(0), cities.get(1)));
        assertEquals(2, testProgram2.findCheapestPathPrice(cities.get(0), cities.get(2)));
        assertEquals(3, testProgram2.findCheapestPathPrice(cities.get(0), cities.get(3)));
        assertEquals(1, testProgram2.findCheapestPathPrice(cities.get(1), cities.get(2)));
        assertEquals(1, testProgram2.findCheapestPathPrice(cities.get(1), cities.get(3)));
        assertEquals(2, testProgram2.findCheapestPathPrice(cities.get(2), cities.get(3)));
        assertEquals(4, testProgram2.findLowestTotalCost());
    }
}


