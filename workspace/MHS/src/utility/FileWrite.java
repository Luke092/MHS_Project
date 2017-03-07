package utility;

import java.io.*;

public class FileWrite {
	
	private File out;
	
	public FileWrite(File out){
		this.out = out;
	}
	
	public void write(String s){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(this.out));
			bw.write(s);
			bw.close();
		} catch (IOException ex){
			System.err.println(ex.getMessage());
		}
	}

}
