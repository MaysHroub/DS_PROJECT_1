package dataholder_pack;

import data_pack.*;
import dll_pack.DNode;
import dll_pack.DoublyLinkedList;
import linkedlist_pack.Node;

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
