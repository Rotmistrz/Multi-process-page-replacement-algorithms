import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
 
public class Memory {
    private int size;
    private LinkedList<MemoryPage> memory;
   
    public Memory(int size) {
        this.size = size;
        memory = new LinkedList<MemoryPage>();
    }
   
    public boolean add(MemoryPage page) {
    	if(memory.size() < size) {
            memory.addLast(page);
            return true;
        } else {
            return false;
        }
    }
   
    public MemoryPage poll() {
        return memory.poll();
    }
   
   
    public boolean isInMemory(int id) {
        return (getById(id) instanceof NoMemoryPage) ? false : true;
    }
   
    public MemoryPage get(int index) {
        return memory.get(index);
    }
   
    public MemoryPage getById(int id) {
        ListIterator<MemoryPage> it = memory.listIterator();
       
        MemoryPage current;
        MemoryPage found = new NoMemoryPage();
        
        while(it.hasNext()) {
            current = it.next();
            
        	if(current.id == id) {
                found = current;
                break;
            }
        }
       
        return found;
    }
    
    public Memory increase(MemoryPage page) {
    	size++;
    	
    	memory.add(page);
    	
    	return this;
    }
    
    public MemoryPage decrease() {
    	if(memory.size() <= 1) {
    		return new NoMemoryPage();
    	}
    	
    	MemoryPage page = new NoMemoryPage();
    	
    	try {
    		page = memory.getLast();
    		memory.remove(page);
    		
    	} catch(Exception e) {
    		System.out.println("size: " + size + "; memorySize: " + memory.size());
    	}
    	
    	size--;
    	
    	return page;
    }
   
    public int size() {
        return this.size;
    }
   
    public Memory remove(MemoryPage page) {
        memory.remove(page);
       
        return this;
    }
   
    public ListIterator<MemoryPage> listIterator() {
        return memory.listIterator();
    }
}