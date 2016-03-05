package task1;

public class FileControl{
	private static int writerCount = 0;
	private static int readerCount = 0;
	private static boolean isWriting = false;
	private static Object mutex = new Object();
	// init showDebugInfo to false to get exactly what the assignment sheet asks for 
	protected static boolean showDebugInfo = true; 
		
	public void writerEntry() throws InterruptedException{
		String tName = Thread.currentThread().getName();
		synchronized(mutex){
			writerCount++;
			if (isWriting || readerCount > 0){
				System.out.println(tName+": waiting to write, "+printInfo());
				System.out.flush();
			}
			while(isWriting || readerCount > 0){
				mutex.wait();
			}
			isWriting = true;
			System.out.println(tName+": ready to write,   "+printInfo());
			System.out.flush();	
		}
	}
	public void writerExit()throws InterruptedException{
		String tName = Thread.currentThread().getName();
		synchronized(mutex){
			writerCount--;
			isWriting = false;
			System.out.println(tName+": finished writing, "+printInfo());
			System.out.flush();
			mutex.notifyAll();
		}
	}
	public void readerEntry()throws InterruptedException{
		String tName = Thread.currentThread().getName();
		synchronized(mutex){
			if (writerCount > 0) {
				System.out.println(tName+": waiting to read,  "+printInfo());
				System.out.flush();
			}
			while(writerCount > 0) {
				mutex.wait();
			}
			readerCount++;
			System.out.println(tName+": ready to read,    "+printInfo());
			System.out.flush();
			mutex.notifyAll(); // unlimited readers, they'll check their conditions...
		}
	}
	public void readerExit()throws InterruptedException{
		String tName = Thread.currentThread().getName();
		synchronized(mutex){
			readerCount--;
			System.out.println(tName+": finished reading, "+printInfo());
			System.out.flush();
			mutex.notifyAll();
		}
	}
	
	public String printInfo(){
		if(!showDebugInfo) 
			return("");
		return String.format("rr:%2d ww:%2d isWriting:%b", readerCount, writerCount, isWriting);
	}
}
