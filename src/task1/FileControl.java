/* 
 * Comp 5461, winter 2016, Programming assignment 2
 * Federico O'Reilly Regueiro, 40012304
 * Task 1 - FileControl for, handles priorities for r/w access
 * I have included some additional info in my printouts which
 * makes it easier to see how priorities are being handled:
 * rr stands for readers reading
 * ww stands for writers waiting and 
 * isWriting refers to a writer being inside the file
 * to print out the exact output requested in the assignment,
 * set showDebugInfo to false
 */

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
	
	// this helper is already being called from the monitor, 
	// so we don't use synchronized here
	public String printInfo(){
		if(!showDebugInfo) 
			return("");
		return String.format("rr:%2d ww:%2d isWriting:%b", readerCount, writerCount, isWriting);
	}
}
