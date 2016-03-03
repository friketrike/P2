package task2;

public class Task2driver {

	public static void main(String[] args) {
		final int numThreads = 10;
		ReaderThread[] rts = new ReaderThread[numThreads];
		WriterThread[] wts = new WriterThread[numThreads];
		for (int i = 0; i < numThreads; i++){
			rts[i] = new ReaderThread();
			wts[i] = new WriterThread();
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
		System.out.println("Done!");
	}

}
