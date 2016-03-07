/* 
 * Comp 5461, winter 2016, Programming assignment 2
 * Federico O'Reilly Regueiro, 40012304
 * Task 1 - Driver, run this code to start task 1
 * I have included some additional info in my printouts which
 * makes it easier to see how priorities are being handled:
 * rw stands for readers waiting
 * rr stands for readers reading
 * ww stands for writers waiting and 
 * isWriting refers to a writer being inside the file
 * to print out the exact output requested in the assignment,
 * set task2.FileControl.showDebugInfo to false
 */

package task2;

public class Task2driver {

	public static void main(String[] args) {
		if(FileControl.showDebugInfo)
			System.out.println("Here we go...");
		final int numThreads = 10;
		ReaderThread[] rts = new ReaderThread[numThreads];
		WriterThread[] wts = new WriterThread[numThreads];
		for (int i = 0; i < numThreads; i++){
			rts[i] = new ReaderThread();
			wts[i] = new WriterThread();
			rts[i].setName("Reader Thread "+i);
			wts[i].setName("Writer Thread "+i);
			rts[i].start();
			wts[i].start();
		}
		for (int i = 0; i < numThreads; i++){
			try{
				rts[i].join();
				wts[i].join();
			}
			catch (InterruptedException e){
				System.err.println(e);
			}	
		}
		if(FileControl.showDebugInfo)
			System.out.println("Done!");
	}

}
