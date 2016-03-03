package task1;

public class FileControl{
	private static int writerCount = 0;
	private static int readerCount = 0;
	private static boolean isWriting = false;
	private static Object mutex = new Object();
		
	public void writerEntry() throws InterruptedException{
		long tId = Thread.currentThread().getId();
		synchronized(mutex){
			writerCount++;
			System.out.println("Writer thread "+tId+": waiting to write, "+printInfo());
			System.out.flush();
			while(isWriting || readerCount > 0){
				mutex.notifyAll();
				mutex.wait();
			}
			isWriting = true;
			System.out.println("Writer thread "+tId+": ready to write,   "+printInfo());
			System.out.flush();	
		}
	}
	public void writerExit()throws InterruptedException{
		long tId = Thread.currentThread().getId();
		synchronized(mutex){
			writerCount--;
			isWriting = false;
			System.out.println("Writer thread "+tId+": finished writing, "+printInfo());
			System.out.flush();
			mutex.notifyAll();
		}
	}
	public void readerEntry()throws InterruptedException{
		long tId = Thread.currentThread().getId();
		synchronized(mutex){
			System.out.println("Reader thread "+tId+": waiting to read,  "+printInfo());
			System.out.flush();
			while(writerCount > 0) {
				mutex.notifyAll();//maybe a writer can go...
				mutex.wait();
			}
			readerCount++;
			System.out.println("Reader thread "+tId+": ready to read,    "+printInfo());
			System.out.flush();
			mutex.notifyAll(); // unlimited readers...
		}
	}
	public void readerExit()throws InterruptedException{
		long tId = Thread.currentThread().getId();
		synchronized(mutex){
			readerCount--;
			System.out.println("Reader thread "+tId+": finished reading, "+printInfo());
			System.out.flush();
			mutex.notifyAll();
		}
	}
	
	public String printInfo(){
		return String.format("rr:%2d ww:%2d isWriting:%b", readerCount, writerCount, isWriting);
	}
}
