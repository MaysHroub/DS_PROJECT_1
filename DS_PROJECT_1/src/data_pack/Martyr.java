package data_pack;

import java.util.Date;

public class Martyr implements Comparable<Martyr> {
	
	private String name;
	private int age;
	private char gender;
	private Date event;
	private District district;
	private Location location;

	public Martyr() { }
	
	public Martyr(String name, int age, char gender, Date event, District district, Location location) {
		setName(name);
		setAge(age);
		setGender(gender);
		setEvent(event);
		setDistrict(district);
		setLocation(location);
	}
	
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Date getEvent() {
		return event;
	}

	public void setEvent(Date event) {
		this.event = event;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age < 0) throw new IllegalArgumentException("Invalid age value");
		this.age = age;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		if (gender != 'F' && gender != 'M') 
			throw new IllegalArgumentException("We don't allow other genders here hhhh :)");
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Martyr)) return false;
		Martyr m = (Martyr) obj;
		return name.equalsIgnoreCase(m.name) && age == m.age; // check the name for the combo box selection
	}

	@Override
	public int compareTo(Martyr o) {
		return age - o.age;
	}
	
	// csv data : Name,Event,Age,Location,District,Gender
	public static Martyr constructMartyr(String csvData) {
		String[] data = csvData.split(",");
		if (data[0].toLowerCase().contains("unknown") || data[2].equals("") ||
				data[5].charAt(0) != 'F' && data[5].charAt(0) != 'M')
			return null;
		
		Martyr martyr = new Martyr();
		martyr.setName(data[0]);
		String[] dateInfo = data[1].split("/");
		@SuppressWarnings("deprecation")
		// mm/dd/yyyy
		Date date = new Date(Integer.parseInt(dateInfo[2])-1900, Integer.parseInt(dateInfo[0])-1, Integer.parseInt(dateInfo[1]));
		martyr.setEvent(date);
		martyr.setAge(Integer.parseInt(data[2])); 
		martyr.setLocation(new Location(data[3]));
		martyr.setDistrict(new District(data[4]));
		martyr.setGender(data[5].charAt(0));
		return martyr;
	}
	
	public String getInfo() {
		return String.format("Name: %s\nEvent: %s\nAge: %d\nGender: %c\nLocation: %s\nDistrict: %s", 
		name, event, age, gender, location, district);
	}
	

}








