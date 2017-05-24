import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ListsFactory {
	public static LinkedList<Integer> createIntegerList(String filename) throws IOException {
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		String line;
		Scanner file = new Scanner(Paths.get(filename), "UTF-8");
		
		try {
			while((line = file.nextLine()) != null) {
				list.add(Integer.parseInt(line));
			}
		} catch(NoSuchElementException e) {

		} catch(Exception e) {
			throw e;
		} finally {
			file.close();
		}
		
		return list;
	}
}