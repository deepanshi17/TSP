package PA2;

import java.util.ArrayList;

public class Heap {
	private ArrayList<City> minHeap;

	public Heap() {
		minHeap = new ArrayList<City>();
	}

	/**
	 * buildHeap(ArrayList<City> cities) Given an ArrayList of Cities, build a
	 * min-heap keyed on each City's minCost Time Complexity - O(n)
	 *
	 * @param cities
	 */
	public void buildHeap(ArrayList<City> cities) {
		// TODO: implement this method
		for (City c : cities) {
			minHeap.add(c);
		}
		for (int i = 0; i < cities.size(); i++) {
			minHeap.get(i).index = i;
		}
		for (int i = (cities.size() / 2) - 1; i >= 0; i--) {
			HeapifyDown(i);
		}
	}

	private void HeapifyDown(int i) {
		int j = i;
		int left = (2 * i) + 1;
		int right = (2 * i) + 2;
		if (left <= minHeap.size() - 1 && minHeap.get(left).getMinCost() < minHeap.get(j).getMinCost()) {
			j = left;
		}
		if (left <= minHeap.size() - 1 && minHeap.get(left).getMinCost() == minHeap.get(j).getMinCost()) {
			j = (minHeap.get(left).getCityName() < minHeap.get(j).getCityName()) ? left : j;
		}
		if (right <= minHeap.size() - 1 && minHeap.get(right).getMinCost() == minHeap.get(j).getMinCost()) {
			j = (minHeap.get(right).getCityName() < minHeap.get(j).getCityName()) ? right : j;
		}
		if (right <= minHeap.size() - 1 && minHeap.get(right).getMinCost() < minHeap.get(j).getMinCost()) {
			j = right;
		}
		if (j != i) {
			City temp = minHeap.get(j);
			int saveIndex2 = minHeap.get(i).index;
			int saveIndex = temp.index;
			minHeap.set(j, minHeap.get(i));
			minHeap.get(j).index = saveIndex;
			minHeap.set(i, temp);
			minHeap.get(i).index = saveIndex2;
			HeapifyDown(j);
		}

	}

	private void HeapifyUp(int i) {
		int parent = (int) (i - 1) / 2;
		if (i != 0 && minHeap.get(i).getMinCost() < minHeap.get(parent).getMinCost()) {
			City temp = minHeap.get(parent);
			int saveIndex2 = minHeap.get(i).index;
			int saveIndex = temp.index;
			minHeap.set(parent, minHeap.get(i));
			minHeap.get(parent).index = saveIndex;
			minHeap.set(i, temp);
			minHeap.get(i).index = saveIndex2;
			HeapifyUp(parent);
		}
		
		if (i != 0 && minHeap.get(i).getMinCost() == minHeap.get(parent).getMinCost()) {
			if (minHeap.get(i).getCityName() < minHeap.get(parent).getCityName()) {
				City temp = minHeap.get(parent);
				int saveIndex2 = minHeap.get(i).index;
				int saveIndex = temp.index;
				minHeap.set(parent, minHeap.get(i));
				minHeap.get(parent).index = saveIndex;
				minHeap.set(i, temp);
				minHeap.get(i).index = saveIndex2;
				HeapifyUp(parent);
			}
		}
	}

	/**
	 * insertNode(City in) Insert a City into the heap. Time Complexity - O(log(n))
	 *
	 * @param in - the City to insert.
	 */
	public void insertNode(City in) {
		// TODO: implement this method
		minHeap.add(in);
		in.index = minHeap.size() - 1;
		HeapifyUp(minHeap.size() - 1);
	}

	/**
	 * findMin()
	 *
	 * @return the minimum element of the heap. Must run in constant time.
	 */
	public City findMin() {
		// TODO: implement this method
		return minHeap.get(0);
	}

	/**
	 * extractMin() Time Complexity - O(log(n))
	 *
	 * @return the minimum element of the heap, AND removes the element from said
	 *         heap.
	 */
	public City extractMin() {
		// TODO: implement this method
		City min = findMin();
		delete(0);
		return min;
	}

	/**
	 * delete(int index) Deletes an element in the min-heap given an index to delete
	 * at. Time Complexity - O(log(n))
	 *
	 * @param index - the index of the item to be deleted in the min-heap.
	 */
	public void delete(int index) {
		// TODO: implement this method
		int left = (2 * index) + 1;
		int right = (2 * index) + 2;
		int parent = (int) (index - 1) / 2;
		if (minHeap.size() > 1) {
			minHeap.set(0, minHeap.get(minHeap.size() - 1));
			minHeap.get(0).index = 0;
			minHeap.remove(minHeap.size() - 1);
		} else {
			minHeap.clear();
		}
		if (parent > 0) {
			if (minHeap.get(index).getMinCost() < minHeap.get(parent).getMinCost()) {
				HeapifyUp(index);
			}
		}
		if (right <= minHeap.size() - 1) {
			if (minHeap.get(index).getMinCost() > minHeap.get(left).getMinCost()
					|| minHeap.get(index).getMinCost() > minHeap.get(right).getMinCost()) {
				HeapifyDown(index);
			}
		}
	}

	/**
	 * changeKey(City c, int newCost) Updates and rebalances a heap for City c. Time
	 * Complexity - O(log(n))
	 *
	 * @param c       - the city in the heap that needs to be updated.
	 * @param newCost - the new cost of city c in the heap (note that the heap is
	 *                keyed on the values of minCost)
	 */
	public void changeKey(City c, int newCost) {
		// TODO: implement this method
		int index = c.index;
		int left = (2 * index) + 1;
		int right = (2 * index) + 2;
		int parent = (int) (index - 1) / 2;
		c.setMinCost(newCost);
		minHeap.set(index, c);
		if (minHeap.get(index).getMinCost() < minHeap.get(parent).getMinCost() && parent >= 0) {
			HeapifyUp(index);
		}
		if (right <= minHeap.size() - 1) {
			if (minHeap.get(index).getMinCost() > minHeap.get(left).getMinCost()
					|| minHeap.get(index).getMinCost() > minHeap.get(right).getMinCost()) {
				HeapifyDown(index);
			}
		}
	}

	public boolean isEmpty() {
		if (minHeap.isEmpty())
			return true;
		else
			return false;
	}

	public boolean contains(City c) {
		if (minHeap.contains(c))
			return true;
		else
			return false;
	}

	public String toString() {
		String output = "";
		for (int i = 0; i < minHeap.size(); i++) {
			output += minHeap.get(i).getCityName() + " ";
		}
		return output;
	}

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

	public ArrayList<City> toArrayList() {
		return minHeap;
	}
}
