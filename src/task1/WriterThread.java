/* 
 * Comp 5461, winter 2016, Programming assignment 2
 * Federico O'Reilly Regueiro, 40012304
 * Writer Thread class
 */

package task1;

import java.io.RandomAccessFile;

import java.io.IOException;

public class WriterThread extends Thread{
	
	private static final String fileName = "Task1.txt";
	
	private static final int ALPHABET_LENGTH = 26;
	
	private static int writeIndex = 0;

	private FileControl fc = new FileControl();
	
	public void run(){
		for(int i = 0; i < 1000; i++){
			try{
				fc.writerEntry();
			}catch(InterruptedException e){System.err.println(e);}
			
			/*-------------<<<WRITING>>>---------*/
			if(FileControl.showDebugInfo)
				System.out.println(Thread.currentThread().getName()+": writing...        "+fc.printInfo());
			writeOneAlphabet();

			try{
				fc.writerExit();
			}catch(InterruptedException e){System.err.println(e);}
		}
	}
	
	private void writeOneAlphabet(){
		byte[] alphabet = new byte[ALPHABET_LENGTH];
		for(byte c = 'a'; c <= 'z'; c++)
			alphabet[(int)(c-'a')] = c;
		try{
			RandomAccessFile writer = new RandomAccessFile(fileName, "rw");
			writer.seek(writeIndex);
			writer.write(alphabet, 0, ALPHABET_LENGTH);
			writer.close();
		}catch(IOException e){
			System.err.println(e);
		}
		writeIndex += ALPHABET_LENGTH;
    }
}

