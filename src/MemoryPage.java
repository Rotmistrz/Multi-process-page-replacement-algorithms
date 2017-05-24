
public class MemoryPage {
    private static int I = 0;
   
    public int id;
    public long lastRequestTime;
    public long locatedInMemoryTime;
    public boolean requestBit;
   
    public MemoryPage() {
        this.id = I++;
        this.lastRequestTime = 0;
        this.locatedInMemoryTime = 0;
        this.requestBit = false;
    }
    
    public static void clear() {
    	I = 0;
    }
}