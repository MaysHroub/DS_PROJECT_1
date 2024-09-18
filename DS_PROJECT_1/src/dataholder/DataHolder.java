package dataholder;

import data.*;
import doublylinkedlist.DNode;
import doublylinkedlist.DoublyLinkedList;
import linkedlist.Node;

public class DataHolder {
	
	private DoublyLinkedList<District> districts;
	

	public DataHolder(DoublyLinkedList<District> districts) {
		this.districts = districts;
		districts.setCurrentNode(districts.getHead());
		updateDataStats(); // O(DN)
	}
	
	private void updateDataStats() { // D*(LM + N) -> [LM=N] -> O(DN)
		DNode<District> currDist = districts.getHead();
		while (currDist != null) { // loops D times
			Node<Location> curr = currDist.getData().getLocations().getHead();
			while (curr != null) { // loops L times
				curr.getData().getStat().updateStats(); // O(M)
				curr = curr.getNext();
			}
			currDist.getData().getStat().updateStats(); // O(N)
			currDist = currDist.getNext();
		}
	}

	public District nextDistrict() {
		return districts.moveNext();
	}
	
	public District prevDistrict() {
		return districts.movePrev();
	}
	
	public District getCurrentDistrict() {
		return districts.getCurrentData();
	}
	
	public Location nextLocation() {
		return getCurrentDistrict().getLocations().moveNext();
	}
	
	public Location getCurrentLocation() {
		return getCurrentDistrict().getLocations().getCurrentData();
	}
	
	public DoublyLinkedList<District> getDistricts() {
		return districts;
	}

}
