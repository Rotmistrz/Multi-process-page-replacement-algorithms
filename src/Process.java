
public class Process {
	private int necessaryMemory;
	private int assignedMemory;
	private LRU memory;
	public int id;
	
	private static int I = 0;
	
	public Process(int necessaryMemory, int assignedMemory) {
		this.necessaryMemory = necessaryMemory;
		this.assignedMemory = assignedMemory;
		
		int necessaryVirtualMemorySize = necessaryMemory - assignedMemory;
		int virtualMemorySize;
		
		if(necessaryVirtualMemorySize < 0) {
			virtualMemorySize = 0;
		} else {
			virtualMemorySize = necessaryVirtualMemorySize;
		}
		
		Memory physicalMemory = new Memory(assignedMemory);
		Memory virtualMemory = new Memory(virtualMemorySize);
		
		MemoryPage.clear();
		int n = 0;
		
		for(int i = 0; i < assignedMemory; i++) {
			if(!physicalMemory.add(new MemoryPage())) {
				System.out.println("physicalMemory: Nie mieœci siê w pamiêci: " + n);
			}
			n++;
		}
		
		for(int i = 0; i < virtualMemorySize; i++) {
			if(!virtualMemory.add(new MemoryPage())) {
				System.out.println("virtualMemory: Nie mieœci siê w pamiêci");
			}
			n++;
		}
		
		memory = new LRU(physicalMemory, virtualMemory);
		id = I++;
		
	}
	
	public boolean receivePage(int id) {
		if(memory.isInPhysicalMemory(id)) {
			return true;
		} else {
			try {
				memory.receivePage(id);
			} catch(Exception e) {
				
			}
			
			return false;
		}
	}
	
	public Process increase() {
		memory.increase();
		
		return this;
	}
	
	public Process decrease() {
		memory.decrease();
		
		return this;
	}
	
	public Process increaseMissErrors() {
		memory.increaseMissErrors();
		
		return this;
	}
	
	public Process clear() {
		memory.clear();
		
		return this;
	}
	
	public int getMissErrors() {
		return memory.missErrors;
	}
	
	public int size() {
		return memory.physicalMemory.size();
	}
}
