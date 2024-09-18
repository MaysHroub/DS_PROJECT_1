package data;

import linkedlist.Node;

public class LocationStat extends MartyrStat {

	private Location location;
	private Martyr youngest, oldest;
	
	public LocationStat(Location location) {
		this.location = location;
	}

	@Override
	public int calcTotalMartyrs() {  // O(M)
		return location.getMartyrs().length();  
	}

	@Override
	public int calcTotalFemale() {  // O(M)
		int count = 0;
		Node<Martyr> curr = location.getMartyrs().getHead();
		while (curr != null) {  // O(M)
			if (curr.getData().getGender() == 'F')
				count++;
			curr = curr.getNext();
		}
		return count;
	}

	@Override
	public int calcTotalMale() {  // O(M)
		int count = 0;
		Node<Martyr> curr = location.getMartyrs().getHead();
		while (curr != null) {  // O(M)
			if (curr.getData().getGender() == 'M')
				count++;
			curr = curr.getNext();
		}
		return count;
	}

	@Override
	public int calcAvgAges() {  // O(2M) -> O(M)
		int sum = 0;
		Node<Martyr> curr = location.getMartyrs().getHead();
		while (curr != null) {  // O(M)
			sum += curr.getData().getAge();
			curr = curr.getNext();
		}
		int count = location.getMartyrs().length(); // O(M)
		return (count != 0) ? sum / count : 0;
	}

	@Override
	public void updateStats() { // In total: O(M)
		super.updateStats();
		youngest = findYoungestMartyr();
		oldest = findOldestMartyr();
	}
	
	public Martyr findYoungestMartyr() {  // O(M)
		Node<Martyr> curr = location.getMartyrs().getHead();
		if (curr == null) return null;
		Martyr youngest = curr.getData();
		curr = curr.getNext();
		while (curr != null) {  // O(M)
			if (youngest.getAge() > curr.getData().getAge())
				youngest = curr.getData();
			curr = curr.getNext();
		}
		return youngest;
	}

	public Martyr findOldestMartyr() {  // O(M)
		Node<Martyr> curr = location.getMartyrs().getHead();
		if (curr == null) return null;
		Martyr oldest = curr.getData();
		curr = curr.getNext();
		while (curr != null) {  // O(M)
			if (oldest.getAge() < curr.getData().getAge())
				oldest = curr.getData();
			curr = curr.getNext();
		}
		return oldest;
	}
	
	public Martyr getYoungest() {
		return youngest;
	}

	public Martyr getOldest() {
		return oldest;
	}

}
