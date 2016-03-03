package task1;

public class WriterThread extends Thread{
	
	private FileControl fc = new FileControl();
	
	public void run(){
		for(int i = 0; i < 3; i++){ // TODO revert to 1000
			try{
				fc.writerEntry();
			}catch(InterruptedException e){System.err.println(e);}
			
			/*-------------<<<WRITING>>>---------*/
			System.out.println("Writer thread "+Thread.currentThread().getId()+": writing...        "+fc.printInfo());
			
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