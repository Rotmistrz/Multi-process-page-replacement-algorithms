
public class Process {
	private int necessaryMemory;
	private int assignedMemory;
	private LRU memory;
	
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
			if(n >= necessaryMemory) {
				break;
			}
			
			physicalMemory.add(new MemoryPage());
			n++;
		}
		
		MemoryPage.clear();
		
		for(int i = 0; i < virtualMemorySize; i++) {
			virtualMemory.add(new MemoryPage());
			n++;
		}
		
		memory = new LRU(physicalMemory, virtualMemory);
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
}
