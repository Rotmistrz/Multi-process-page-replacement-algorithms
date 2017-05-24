import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

public abstract class PageReplacementAlgorithm {
	protected Memory physicalMemory;
	protected Memory virtualMemory;
	protected int missErrors = 0;
	
	protected abstract MemoryPage throwOutPage();
	
	public int run(LinkedList<Integer> requests) {
		missErrors = 0;
		
		ListIterator<Integer> it = requests.listIterator();
		
		int current;
		MemoryPage page;
		
		Date currentTime = new Date();
		
		while(it.hasNext()) {
			if(Calendar.getInstance().getTimeInMillis() <= currentTime.getTime()) {
				continue;
			}
			
			current = it.next();
			
			if(!physicalMemory.isInMemory(current)) {
				missErrors++;
				//System.out.println(current + " out of physical memory; missErrors = " + missErrors);
				
				try {
					receivePage(current);
				} catch(PageDoesntExistException pdee) {
					System.out.println("Page #" + current + " doesn't exist in any memory. Possible incorrect result of simulation.");
				}
			} else {
				//System.out.println(current + " in physical memory.");
			}
			
			currentTime = new Date();
			
			page = physicalMemory.getById(current);
			page.lastRequestTime = currentTime.getTime();
		}
		
		return missErrors;
	}
	
	public PageReplacementAlgorithm(Memory physicalMemory, Memory virtualMemory) {
		this.physicalMemory = physicalMemory;
		this.virtualMemory = virtualMemory;
	}
	
	public PageReplacementAlgorithm receivePage(int id) throws PageDoesntExistException {
		MemoryPage thrownOut = throwOutPage();
		MemoryPage page = virtualMemory.getById(id);
		
		if(page instanceof NoMemoryPage) {
			throw new PageDoesntExistException();
		} else {
			Date currentTime = new Date();
			page.locatedInMemoryTime = currentTime.getTime();
			physicalMemory.add(page);
			virtualMemory.remove(page);
			virtualMemory.add(thrownOut);
		}
		
		return this;
	}
	
	public boolean isInPhysicalMemory(int id) {
		return (physicalMemory.getById(id) instanceof NoMemoryPage) ? false : true;
	}
	
	public static Memory copyMemory(Memory memory) {
		Memory copied = new Memory(memory.size());
		
		ListIterator<MemoryPage> it = memory.listIterator();
		MemoryPage current;
		
		while(it.hasNext()) {
			current = it.next();
			MemoryPage copy = new MemoryPage();
			copy.id = current.id;
			copy.lastRequestTime = current.lastRequestTime;
			copy.locatedInMemoryTime = current.locatedInMemoryTime;
			
			copied.add(copy);
		}
		
		return copied;
	}
}
