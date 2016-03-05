package task1;

import java.io.RandomAccessFile;

import java.io.IOException;

public class ReaderThread extends Thread{
	
	private static final String fileName = "Task1.txt";
	
	private static final int ALPHABET_LENGTH = 26;
	
	public static int readIndex = 0;
	
	private FileControl fc = new FileControl();
	
	public void run(){
		for(int i = 0; i < 1000; i++){
			try{
				fc.readerEntry();
			}catch(InterruptedException e){System.err.println(e);}
			
			/*-------------<<<READING>>>---------*/
			if(FileControl.showDebugInfo)
				System.out.println(Thread.currentThread().getName()+": reading...        "+fc.printInfo());
			readOneAlphabet();

			try{
				fc.readerExit();
			}catch(InterruptedException e){System.err.println(e);}
		}
	}
    
    private void readOneAlphabet(){
		byte[] alphabet = new byte[ALPHABET_LENGTH];
		int status = -1;
		try{
			RandomAccessFile reader = new RandomAccessFile(fileName, "r");
			reader.seek(readIndex);
			status = reader.read(alphabet, 0, ALPHABET_LENGTH);
			reader.close();
		}catch(IOException e){
			System.err.println(e);
		}
		if(status != -1){
			if(FileControl.showDebugInfo)
				System.out.println(new String(alphabet)); 
			readIndex += ALPHABET_LENGTH;
		}
    }
}
