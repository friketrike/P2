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
