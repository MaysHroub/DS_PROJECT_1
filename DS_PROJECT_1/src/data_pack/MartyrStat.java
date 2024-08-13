package data_pack;

public abstract class MartyrStat {

	private int totalMartyrs, totalMale, totalFemale, avgAges;

	public abstract int calcTotalMartyrs();

	public abstract int calcTotalFemale();

	public abstract int calcTotalMale();

	public abstract int calcAvgAges();

	public void updateStats() { // Time complexity depends on whether this method is for districts or locations
		totalMartyrs = calcTotalMartyrs();
		totalMale = calcTotalMale();
		totalFemale = calcTotalFemale();
		avgAges = calcAvgAges();
	}

	public int getTotalMartyrs() {
		return totalMartyrs;
	}

	public int getTotalMales() {
		return totalMale;
	}

	public int getTotalFemales() {
		return totalFemale;
	}

	public int getAvgAges() {
		return avgAges;
	}

}
