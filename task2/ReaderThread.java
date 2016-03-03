package task2;

//import java.util.Random;

public class ReaderThread extends Thread{
	
	private FileControl fc = new FileControl();
	
	public void run(){
		for(int i = 0; i < 3; i++){// TODO revert to 1000
			try{
				fc.readerEntry();
			}catch(InterruptedException e){System.err.println(e);}
			
			/*-------------<<<READING>>>---------*/
			System.out.println("Reader thread "+Thread.currentThread().getId()+": reading...        "+fc.printInfo());
			/*for (int j = 0; j<100; j++){
				Random r = new Random();
				int k = r.nextInt();
			}*/
			try{
				sleep(50);
			}catch(InterruptedException e){
				System.err.println(e);
			}
			try{
				fc.readerExit();
			}catch(InterruptedException e){System.err.println(e);}
		}
	}
}
