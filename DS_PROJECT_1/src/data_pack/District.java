package data_pack;

import linkedlist_pack.LinkedList;

public class District implements Comparable<District> {
	
	private String name;
	private LinkedList<Location> locations;
	private DistrictStat stat;

	
	public District(String name) {
		setName(name);
		locations = new LinkedList<>();
	}
	
	public District(String name, LinkedList<Location> locations) {
		setName(name);
		setLocations(locations);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Location> getLocations() {
		return locations;
	}

	public void setLocations(LinkedList<Location> locations) {
		this.locations = locations;
	}

	@Override
	public int compareTo(District o) {
		return name.toLowerCase().compareTo(o.name.toLowerCase());
	}
	
	@Override
	public String toString() {
		return name;
	}

	public DistrictStat getStat() {
		return stat;
	}

	public void setStat(DistrictStat stat) {
		this.stat = stat;
	}
	
}
