import java.util.Arrays;

public class quickSortTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] array = new int[11];
		array[0] = 10;
		array[1] = 34;
		array[2] = 0;
		array[3] = 21;
		array[4] = 14;
		array[5] = 37;
		array[6] = 66;
		array[7] = 71;
		array[8] = 42;
		array[9] = 9;
		array[10] = 7;
		
		int[]smallarray = new int[3];
		smallarray[0] = 10;
		smallarray[1] = 34;
		smallarray[2] = 0;
		//smallarray[3] = 21;
		//smallarray[4] = 14;
		
		quickSort(array, 0, 10);
		
		//System.out.println(Arrays.toString(insertionSort(smallarray, 0, 2)));
		System.out.println(Arrays.toString(array));

	}
	
	//quick sort method
	public static void quickSort(int[] array, int first, int last) {
		
		if (last - first < 3) { //length is less than 3
			insertionSort(array, first, last);
		}
		else {
			int middle = first + (last-first)/2;
			int pivot = mot(array[first], array[middle], array[last]);
			int split_point = partition(array, first, last, pivot);
			quickSort(array, first, split_point-1); //first half of the array
			quickSort(array, split_point+1, last);
		}
		
	}
	
	//insert sort method
	public static int[] insertionSort(int[] array, int first, int last) {
		
		for (int i= first+1; i<(last-first+1); i++) {
			
			int key = array[i];
			int j = i-1; //pointer
			
			while (j >= 0 && array[j] > key) {
				array[j+1] = array[j];
				j = j-1;
			}		
			array[j + 1] = key;			
		}
		
		return array;

	}
	
	//median of 3
	public static int mot(int first, int middle, int last) {
		
		int median = 0;
		
		if (middle > first && middle < last) {
			median = middle;
		}
		else if (middle > last && middle < first) {
			median = middle;
		}
		else if (first > middle && first < last) {
			median = first;
		}
		else if (first > last && first < middle) {
			median = first;
		}
		else if (last > first && last < middle) {
			median = last;
		}
		else if (last > middle && last < first) {
			median = last;
		}
		
		return median;
	}
	
	//partition method
	public static int partition(int[] array, int first, int last, int pivot) {
		int firstValue = array[first];
		int lastValue = array[last];
		int pivotIndex = 0;
		
		//swapping pivot and last
		//for loop to put the last value at where the pivot is located
		for (int i=0; i<array.length; i++) {
			
			if (array[i] == pivot)
				pivotIndex = i;
		}
		
		array[pivotIndex] = lastValue;
		array[last] = pivot;
		pivotIndex = last;
		
		int i = first;
		int j = last - 1;
		boolean loop = true; 
		
		
		//move i right and j left until A[i] > pivot and A[j] < pivot
		while (loop == true) {
			while(array[i] <= pivot) {
				i++; 
			}	
			while(array[j] >= pivot && j!=0) {
				j--;
			}
			if(i < j) { //i and j have not yet crossed
				int temp = array[j];
				array[j] = array[i];
				array[i] = temp;
				
			}
			else {
				loop = false;
				
			}
		}
		
		//swapping pivot and i
		array[pivotIndex] = array[i]; 
		array[i] = pivot;
		
		
		return i;
		
	}
	
	
	

}
