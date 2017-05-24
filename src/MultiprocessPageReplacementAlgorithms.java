import java.util.ArrayList;

public class MultiprocessPageReplacementAlgorithms {

	public static void main(String[] args) throws Exception {
		int[] memoryArray = { 7, 5, 12, 8, 7, 6, 5, 9, 13, 11 };
		
		MemoryManager memorymanager = new MemoryManager(90);
		ArrayList<Process> processesProportional = memorymanager.proportionalAllocation(memoryArray);
		ArrayList<Process> processesEqual = memorymanager.equalAllocation(memoryArray);
		
		PageReplacementManager prm = PageReplacementManager.createFromFile("random-5000.txt");
		
		int result = prm.run(processesProportional);
		
		System.out.println("Przydzia³ proporcjonalny: " + result);
		
		result = prm.run(processesEqual);
		
		System.out.println("Przydzia³ równy: " + result);
	}

}
