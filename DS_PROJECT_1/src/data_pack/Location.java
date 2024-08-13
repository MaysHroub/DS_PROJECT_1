package data_pack;

import linkedlist_pack.LinkedList;

public class Location implements Comparable<Location> {

	private String name;
	private LinkedList<Martyr> martyrs;
	private LocationStat stat;

	
	public Location(String name) {
		setName(name);
		martyrs = new LinkedList<>();
	}

	public Location(String name, LinkedList<Martyr> martyrs) {
		setName(name);
		setMartyrs(martyrs);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Martyr> getMartyrs() {
		return martyrs;
	}

	public void setMartyrs(LinkedList<Martyr> martyrs) {
		this.martyrs = martyrs;
	}

	@Override
	public int compareTo(Location o) {
		return name.toLowerCase().compareTo(o.name.toLowerCase());
	}
	
	@Override
	public String toString() {
		return name;
	}

	public LocationStat getStat() {
		return stat;
	}

	public void setStat(LocationStat stat) {
		this.stat = stat;
	}

}
