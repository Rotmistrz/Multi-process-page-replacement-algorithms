import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

public class LRU extends PageReplacementAlgorithm {

	public LRU(Memory physicalMemory, Memory virtualMemory) {
		super(physicalMemory, virtualMemory);
	}

	@Override
	protected MemoryPage throwOutPage() {
		ListIterator<MemoryPage> it = physicalMemory.listIterator();
		
		MemoryPage oldest = it.next();
		MemoryPage current;
		
		while(it.hasNext()) {
			current = it.next();
			
			if(current.lastRequestTime < oldest.lastRequestTime) {
				oldest = current;
			}
		}
		
		physicalMemory.remove(oldest);
		
		return oldest;
	}

}