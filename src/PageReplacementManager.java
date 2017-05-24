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
	private Process min;
	private Process max;
	
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
	
	public int runWithPriority(ArrayList<Process> processes) {
		clear();
		
		int size = requests.size();
		ListIterator<Request> it = requests.listIterator();
		Request current;
		Process process;
		
		int i = 0;
		int j = 50;
		
		while(it.hasNext()) {
			current = it.next();
			
			process = processes.get(current.processId);
			
			System.out.println(i + 1 + " " + current.processId + " " + current.pageId);
			
			if(!(process.receivePage(current.pageId))) {
				missErrors++;
				process.increaseMissErrors();
			}
			
			if(++i >= j) {
				checkExtremes(processes);
				j += 50;
			}
		}
		
		return missErrors;
	}
	
	public PageReplacementManager checkExtremes(ArrayList<Process> processes) {
		countExtremeProcesses(processes);
		
		min.decrease();
		max.increase();
		
		ListIterator<Process> it = processes.listIterator();
		Process current;
		
		while(it.hasNext()) {
			current = it.next();
			current.clear();
		}
		
		return this;
	}
	
	public PageReplacementManager countExtremeProcesses(ArrayList<Process> processes) {
		Process tmpMin = processes.get(0);
		Process tmpMax = processes.get(0); 
		
		ListIterator<Process> it = processes.listIterator();
		Process current;
		
		while(it.hasNext()) {
			current = it.next();
			
			if(current.getMissErrors() >= tmpMax.getMissErrors() && current.size() > 1) {
				tmpMax = current;
			} else if (current.getMissErrors() <= tmpMin.getMissErrors() && current.size() > 1) {
				tmpMin = current;
			}
		}
		
		this.min = tmpMin;
		this.max = tmpMax;
		
		return this;
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
