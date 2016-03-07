/* 
 * Comp 5461, winter 2016, Programming assignment 2
 * Federico O'Reilly Regueiro, 40012304
 * Task 2 - FileControl for, handles priorities for r/w access
 * I have included some additional info in my printouts which
 * makes it easier to see how priorities are being handled:
 * rw stands for readers waiting
 * rr stands for readers reading
 * ww stands for writers waiting and 
 * isWriting refers to a writer being inside the file
 * to print out the exact output requested in the assignment,
 * set showDebugInfo to false
 */

package task2;

public class FileControl{
	private static int readerCount = 0;
	private static int readerWaitingList = 0;
	private static int writerWaitingList = 0;
	private static boolean isWriting = false;
	private static Object mutex = new Object();
	// init showDebugInfo to false to get exactly what the assignment sheet asks for 
	protected static boolean showDebugInfo = true; 
	
	public void writerEntry() throws InterruptedException{
		String tName = Thread.currentThread().getName();
		synchronized(mutex){
			writerWaitingList++;
			if( (readerWaitingList > writerWaitingList) && (readerCount > writerWaitingList) ||
					isWriting || (readerCount > 0) ){
				System.out.println(tName+": waiting to write, "+printInfo());
				System.out.flush();
			}
			while( (readerWaitingList > writerWaitingList) && (readerCount > writerWaitingList) ||
					isWriting || (readerCount > 0) ){
				mutex.wait();
			}
			isWriting = true;
			writerWaitingList--;
			mutex.notifyAll();
			System.out.println(tName+": ready to write,   "+printInfo());
			System.out.flush();	
		}
	}
	public void writerExit()throws InterruptedException{
		String tName = Thread.currentThread().getName();
		synchronized(mutex){
			isWriting = false;
			System.out.println(tName+": finished writing, "+printInfo());
			System.out.flush();
			mutex.notifyAll();
		}
	}
	public void readerEntry()throws InterruptedException{
		String tName = Thread.currentThread().getName();
		synchronized(mutex){
			readerWaitingList++;
			mutex.notifyAll();
			if( ((writerWaitingList > 0) && (writerWaitingList >= readerCount) ) || 
					(writerWaitingList >= readerWaitingList) || isWriting) {
				System.out.println(tName+": waiting to read,  "+printInfo());
				System.out.flush();
			}
			while( ((writerWaitingList > 0) && (writerWaitingList >= readerCount) ) || 
					(writerWaitingList >= readerWaitingList) || isWriting) {
				mutex.wait();
			}
			readerWaitingList--;
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
		return String.format("rw:%2d rr:%2d ww:%2d isWriting:%b", readerWaitingList, readerCount, writerWaitingList, isWriting);
	}
}
