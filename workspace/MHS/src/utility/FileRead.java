package utility;

import java.io.*;
import java.util.BitSet;

import model.Component;
import model.Components;
import model.DistHypothesis;
import model.Hypothesis;
import model.Matrix;
import model.MonoHypothesis;

public class FileRead
{
	public static void readFileMatrix(File file)
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
						System.exit(1);
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

	public static Component readFileComponent(File file, int k)
	{
		Component comp = new Component();

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) 
			{
				if(line.charAt(0) != ';')
				{
					String [] columns = line.split(" ");
					BitSet b = new BitSet(columns.length - 1);
					if(Components.getInstance().getcM() == -1){
						Components.getInstance().setCM(columns.length - 1);
					}
					Hypothesis h = new DistHypothesis(columns.length - 1, k);
					for(int i = 0; i < columns.length - 1; i++)
					{
						if(columns[i].equals("1")){
							b.set(i);
						}
					}
					h.setBits(b);
					comp.add(h);
					if(!columns[columns.length-1].equals("-"))
					{
						System.err.println("Invalid file format. Execution aborted");
						System.exit(1);
					}

				}
			}
			br.close();
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		return comp;
	}
	
	public static boolean readComponents(File dir){
		Components comps = Components.getInstance();
		if(dir.isDirectory()){
			File[] files = dir.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".mhs");
				}
			});
			for(File f: files){
					comps.addComponent(readFileComponent(f, files.length));
			}
		} else {
			return false;
		}
		return true;
	}
}
