import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PageReplacementManager {
	private LinkedList<Request> requests;
	private int missErrors;
	
	public PageReplacementManager() {
		requests = new LinkedList<Request>();
		clear();
	}
	
	public PageReplacementManager add(Request request) {
		requests.add(request);
		
		return this;
	}
	
	public int run(ArrayList<Process> processes) {
		clear();
		
		ListIterator<Request> it = requests.listIterator();
		Request current;
		
		while(it.hasNext()) {
			current = it.next();
			
			if(!(processes.get(current.processId).receivePage(current.pageId))) {
				missErrors++;
			}
		}
		
		return missErrors;
	}
	
	public PageReplacementManager clear() {
		missErrors = 0;
		
		return this;
	}
	
	public static PageReplacementManager createFromFile(String filename) throws IOException {
		PageReplacementManager manager = new PageReplacementManager();
		
		String line;
		Scanner file = new Scanner(Paths.get(filename), "UTF-8");
		String[] data;
		
		try {
			while((line = file.nextLine()) != null) {
				data = StringHelper.split(line, " ");
				
				manager.add(new Request(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
			}
		} catch(NoSuchElementException e) {

		} catch(Exception e) {
			throw e;
		} finally {
			file.close();
		}
		
		return manager;		
	}
}
