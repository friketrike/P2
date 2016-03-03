package task1;

//import java.util.Random;

public class WriterThread extends Thread{
	
	private FileControl fc = new FileControl();
	
	public void run(){
		for(int i = 0; i < 3; i++){ // TODO revert to 1000
			try{
				fc.writerEntry();
			}catch(InterruptedException e){System.err.println(e);}
			
			/*-------------<<<WRITING>>>---------*/
			System.out.println(Thread.currentThread().getId()+" is writing...");
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
				fc.writerExit();
			}catch(InterruptedException e){System.err.println(e);}
		}
	}
}