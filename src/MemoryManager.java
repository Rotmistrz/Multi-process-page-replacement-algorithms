import java.util.ArrayList;

public class MemoryManager {
	public int memorySize;
	
	public MemoryManager(int memorySize) {
		this.memorySize = memorySize;
	}
	
	public ArrayList<Process> proportionalAllocation(int[] array) {
		ArrayList<Process> processes = new ArrayList<Process>();
		int assignedMemory;
		int memoryToAssign = 0;
		int necessaryMemory;
		
		for(int i = 0; i < array.length; i++) {
			memoryToAssign += array[i];
		}
		
		for(int i = 0; i < array.length; i++) {
			necessaryMemory = array[i];
			
			assignedMemory = necessaryMemory * 100 / memorySize;
			
			System.out.println("Potrzebna pamiêæ: " + necessaryMemory + ", zaalokowana pamiêæ: " + assignedMemory);
			
			processes.add(new Process(necessaryMemory, assignedMemory));
		}
		
		return processes;
	}
	
	public ArrayList<Process> equalAllocation(int[] array) {
		ArrayList<Process> processes = new ArrayList<Process>();
		int memoryToAssign = memorySize / array.length;
		int necessaryMemory;
		
		for(int i = 0; i < array.length; i++) {
			necessaryMemory = array[i];
			
			System.out.println("Potrzebna pamiêæ: " + necessaryMemory + ", zaalokowana pamiêæ: " + memoryToAssign);
			
			processes.add(new Process(necessaryMemory, memoryToAssign));
		}
		
		return processes;
	}
}
