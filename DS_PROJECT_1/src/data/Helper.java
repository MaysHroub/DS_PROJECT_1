package data;

public class Helper {

	public static void sort(Comparable[] data) {
		sort(data, 0, data.length - 1);
	}

	private static void sort(Comparable[] data, int left, int right) {
		if (left < right) {
			int m = (left + right) / 2; 
			sort(data, left, m); 
			sort(data, m + 1, right);  
			merge(data, left, m, right); 
		}
	}

	private static void merge(Comparable[] data, int left, int m, int right) {
		int n1 = m - left + 1;
		int n2 = right - m;
		Comparable[] leftArr = new Comparable[n1];
		Comparable[] rightArr = new Comparable[n2];
		for (int i = 0; i < n1; ++i)
			leftArr[i] = data[left + i];
		for (int j = 0; j < n2; ++j)
			rightArr[j] = data[m + 1 + j];
		int i = 0, j = 0;
		int k = left;
		while (i < n1 && j < n2) {
			if (leftArr[i].compareTo(rightArr[j]) <= 0) {
				data[k] = leftArr[i];
				i++;
			} else {
				data[k] = rightArr[j];
				j++;
			}
			k++;
		}
		while (i < n1) {
			data[k] = leftArr[i];
			i++;
			k++;
		}
		while (j < n2) {
			data[k] = rightArr[j];
			j++;
			k++;
		}
	}

}
