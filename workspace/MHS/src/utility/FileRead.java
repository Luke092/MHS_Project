package utility;

import java.io.*;

import model.Matrix;

public class FileRead
{
	public static void readFile(File file)
	{
		Matrix matrix = Matrix.getInstance();
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			int indexLine = 0;
			while ((line = br.readLine()) != null) 
		    {
		       if(line.charAt(0) != ';')
		       {
		    	   String [] columns = line.split(" ");
		    	   for(int i = 0; i < columns.length - 1; i++)
		    	   {
		    		   matrix.addElement(indexLine, Byte.parseByte(columns[i]));
		    	   }		
		    	   if(!columns[columns.length-1].equals("-"))
		    	   {
		    		   System.err.println("Invalid file format. Execution aborted");
		    		   //TO KILL
		    	   }
		    	   
		    	   indexLine++;
		    	   
		       }
		    }
			br.close();
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
	}
}
