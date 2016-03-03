package task1;

public class ReaderThread extends Thread{
	
	private FileControl fc = new FileControl();
	
	public void run(){
		for(int i = 0; i < 3; i++){// TODO revert to 1000
			try{
				fc.readerEntry();
			}catch(InterruptedException e){System.err.println(e);}
			
			/*-------------<<<READING>>>---------*/
			System.out.println("Reader thread "+Thread.currentThread().getId()+": reading...        "+fc.printInfo());
			
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
