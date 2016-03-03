package task2;

public class FileControl{
	private static int readerCount = 0;
	private static int readerWaitingList = 0;
	private static int writerWaitingList = 0;
	private static boolean isWriting = false;
	//private static boolean isReading = false;
	private static Object mutex = new Object();
		
	public void writerEntry() throws InterruptedException{
		long tId = Thread.currentThread().getId();
		synchronized(mutex){
			writerWaitingList++;
			//mutex.notifyAll();
			if( (readerWaitingList > writerWaitingList) && (readerCount > writerWaitingList) ||
					isWriting || (readerCount > 0) ){
				System.out.println("Writer thread "+tId+": waiting to write, rw:"+readerWaitingList+" rr:"+readerCount+" ww:"+writerWaitingList+" isWriting:"+isWriting);
				System.out.flush();
			}
			while( (readerWaitingList > writerWaitingList) && (readerCount > writerWaitingList) ||
					isWriting || (readerCount > 0) ){
				mutex.notifyAll(); // got woken up to go back to waiting, notify someone else...
				mutex.wait();
			}
			isWriting = true;
			writerWaitingList--;
			mutex.notifyAll();
			System.out.println("Writer thread "+tId+": ready to write, rw:"+readerWaitingList+" rr:"+readerCount+" ww:"+writerWaitingList+" isWriting:"+isWriting);
			System.out.flush();	
		}
	}
	public void writerExit()throws InterruptedException{
		long tId = Thread.currentThread().getId();
		synchronized(mutex){
			isWriting = false;
			mutex.notifyAll();
			System.out.println("Writer thread "+tId+": finished writing, rw:"+readerWaitingList+" rr:"+readerCount+" ww:"+writerWaitingList+" isWriting:"+isWriting);
			System.out.flush();			
		}
	}
	public void readerEntry()throws InterruptedException{
		long tId = Thread.currentThread().getId();
		synchronized(mutex){
			readerWaitingList++;
			mutex.notifyAll();
			if( ((writerWaitingList > 0) && (writerWaitingList >= readerCount) ) || 
					(writerWaitingList >= readerWaitingList) || isWriting) {
				System.out.println("Reader thread "+tId+": waiting to read, rw:"+readerWaitingList+" rr:"+readerCount+" ww:"+writerWaitingList+" isWriting:"+isWriting);
				System.out.flush();
			}
			while( ((writerWaitingList > 0) && (writerWaitingList >= readerCount) ) || 
					(writerWaitingList >= readerWaitingList) || isWriting) {
				mutex.notifyAll(); //maybe a writer can go...
				mutex.wait();
			}
			readerWaitingList--;
			readerCount++;
			mutex.notifyAll(); // unlimited readers...
			//isReading = true;
			System.out.println("Reader thread "+tId+": ready to read, rw:"+readerWaitingList+" rr:"+readerCount+" ww:"+writerWaitingList+" isWriting:"+isWriting);
			System.out.flush();
		}
	}
	public void readerExit()throws InterruptedException{
		long tId = Thread.currentThread().getId();
		synchronized(mutex){
			readerCount--;
			mutex.notifyAll();
			//if(readerCount == 0)
			//	isReading = false;
			System.out.println("Reader thread "+tId+": finished reading, rw:"+readerWaitingList+" rr:"+readerCount+" ww:"+writerWaitingList+" isWriting:"+isWriting);
			System.out.flush();
			
		}
	}
}
