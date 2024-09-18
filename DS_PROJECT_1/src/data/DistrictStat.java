package data;

import java.util.Date;

import linkedlist.LinkedList;
import linkedlist.Node;

public class DistrictStat extends MartyrStat {

	private District district;
	private Date mostFreqDate;
	
	public DistrictStat(District district) {
		this.district = district;
	}
	
	@Override
	public int calcTotalMartyrs() { // In total: O(L) where L is number of locations
		int count = 0;
		LinkedList<Location> locations = district.getLocations();
		Node<Location> curr = locations.getHead();
		while (curr != null) {
			count += curr.getData().getStat().getTotalMartyrs();  // constant
			curr = curr.getNext();  // constant
		}
		return count;
	}

	@Override
	public int calcTotalFemale() { // In total: O(L) where L is number of locations
		int count = 0;
		LinkedList<Location> locations = district.getLocations();
		Node<Location> curr = locations.getHead();
		while (curr != null) {
			count += curr.getData().getStat().getTotalFemales(); // constant
			curr = curr.getNext();  // constant
		}
		return count;
	}

	@Override
	public int calcTotalMale() { // In total: O(L) where L is number of locations
		int count = 0;
		LinkedList<Location> locations = district.getLocations();
		Node<Location> curr = locations.getHead();
		while (curr != null) {
			count += curr.getData().getStat().getTotalMales(); // constant
			curr = curr.getNext();  // constant
		}
		return count;
	}

	@Override
	public int calcAvgAges() {  // In total: L * 2M -> O(LM) -> O(N) where N = ML (all martyrs in a district)
		int sum = 0, count = 0;
		LinkedList<Location> locations = district.getLocations();
		Node<Location> curr = locations.getHead();
		while (curr != null) {  // this one will loop L times where L is # of locations
			count += curr.getData().getMartyrs().length(); // O(M), M: # of martyrs in a single location
			Node<Martyr> currMartyr = curr.getData().getMartyrs().getHead();
			while (currMartyr != null) {   // O(M), M: # of martyrs in a single location
				sum += currMartyr.getData().getAge();
				currMartyr = currMartyr.getNext();
			}
			curr = curr.getNext();
		}
		return (count != 0) ? sum / count : 0;
	}

	@Override
	public void updateStats() { // L + L + L + N + N -> O(N)
		super.updateStats();
		mostFreqDate = calcMostFreqDate();
	}

	private Date calcMostFreqDate() {  // In total: O(N log N), N: # of all martyrs in a district
		if (getTotalMartyrs() == 0) return null;
		Date[] dates = new Date[getTotalMartyrs()];
		int itr = 0;
		LinkedList<Location> locations = district.getLocations();
		Node<Location> curr = locations.getHead();
		// the two nested loops will have a time of O(N) where N = ML (all martyrs in a district)
		while (curr != null) {  // loops L times
			Node<Martyr> currMartyr = curr.getData().getMartyrs().getHead();
			while (currMartyr != null) {  // Loops M times
				dates[itr++] = currMartyr.getData().getEvent();
				currMartyr = currMartyr.getNext();
			}
			curr = curr.getNext();
		}
		Helper.sort(dates); // O(N log N)
		int count = 1, maxCount = 1;
		Date mostFreqDate = dates[0];
		
		// In total: O(N)
		for (int i = 1; i < dates.length; i++) { 
			while (i < dates.length && dates[i].compareTo(dates[i - 1]) == 0) {
				count++;
				i++;
			}
			if (maxCount < count) {
				maxCount = count;
				mostFreqDate = dates[i-1];
			}
			count = 1;
		}
		System.out.printf("Most freq date: %s for district %s with num of %d\n",
				mostFreqDate, district.getName(), maxCount);
		return mostFreqDate;
	}

	public Date getMostFreqDate() {
		return mostFreqDate;
	}
	
	private Date calcMostFreqDate2() { // N^2 + N -> O(N^2)
		LinkedList<Location> locations = district.getLocations();
		Node<Location> curr = locations.getHead();
		LinkedList<Date> dates = new LinkedList<>();
		// In total: L * M * N = LM*N = N^2 -> O(N^2)
		while (curr != null) {  // loops L times
			Node<Martyr> currMartyr = curr.getData().getMartyrs().getHead();
			while (currMartyr != null) { // loops M times
				dates.insert(currMartyr.getData().getEvent()); // loops ML=N times
				currMartyr = currMartyr.getNext();
			}
			curr = curr.getNext();
		}
		int count = 1, maxCount = 1;
		Date mostFreqDate = dates.getHead().getData();
		Node<Date> currDate = dates.getHead();
		// In total: O(N)
		while (currDate != null) {  
			if (currDate.getNext() != null && currDate.getNext().getData().compareTo(currDate.getData()) == 0) {
				count++;
				currDate = currDate.getNext();
				continue;
			}
			if (maxCount < count) {
				maxCount = count;
				mostFreqDate = currDate.getData();
			}
			count = 1;
			currDate = currDate.getNext();
		}
		System.out.printf("Most freq date: %s for district %s with num of %d\n",
				mostFreqDate, district.getName(), maxCount);
		return mostFreqDate;
	}
	
	public int calcTotalMartyrsInDate(Date date) { // O(ML) -> O(N) -> N: # of martyrs in a district
		int count = 0;
		Node<Location> currLoc = district.getLocations().getHead();
		while (currLoc != null) { // loops L times
			Node<Martyr> currMartyr = currLoc.getData().getMartyrs().getHead();
			while (currMartyr != null) { // loops M times
				if (currMartyr.getData().getEvent().compareTo(date) == 0) count++;
				currMartyr = currMartyr.getNext();
			}
			currLoc = currLoc.getNext();
		}
		return count;
	}

}









