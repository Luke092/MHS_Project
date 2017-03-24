import model.*;
import model.events.ExecutionEvent;
import utility.*;
import view.DirectorySelection;
import view.FileSelection;
import view.MyMenu;
import view.MyRead;

import java.io.*;
import java.util.Vector;

public class Main
{
	/**
	 * Time limit for the execution
	 */
	private static double timeLimit = -2;
	private static int divisionNumber = -1;
	private static String mainTitle = "Choose one of the following options";
	private static String [] mainOptions = {"Monolithic resolution","Distributed resolution"};
	private static String [] YES_NO = {"Yes","No"};
	
	public static void main(String[] args){
		if(args.length == 0){
			File f = null;	
			MyMenu mainMenu = new MyMenu(mainOptions, mainTitle, true);
			int sel = mainMenu.printMenu();
			if(sel == 0)
			{
				f = selectMatrixFile();	
				Thread t = monoExecution(f);
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}else if(sel == 1)
			{
				f = distInteractiveSel();
				distExecution(f);
			}
		} else {
			parseArgs(args);
		}
	}
	
	private static void parseArgs(String args[]){
		File f = null;
		
		if(args.length > 1){
			for(int j = 1; j < args.length; j++){
				switch(args[j]){
				case "--path":
						String path = args[++j];
						f = new File(path);
						
						if(!(f.exists())){
							System.err.println("File not found!");
							System.exit(-1);
						}
					break;
				case "--timeLimit":
					try{
						timeLimit = Double.parseDouble(args[++j]);
					} catch(ArrayIndexOutOfBoundsException ex){
						System.err.println("The option \"--timeLimit\" is not specified!");
						System.exit(-2);
					} catch (NumberFormatException ex){
						System.err.println("The option \"--timeLimit\" is a number!");
						System.exit(-2);
					}
					break;
				case "--partitions":
					try{
						divisionNumber = Integer.parseInt(args[++j]);
					} catch(ArrayIndexOutOfBoundsException ex){
						System.err.println("The option \"--partitions\" is not specified!");
						System.exit(-2);
					} catch (NumberFormatException ex){
						System.err.println("The option \"--partitions\" is a number!");
						System.exit(-2);
					}
					break;
				}
			}
		}
		
		if(args[0].equals("--mono")){
			if(f == null){
				f = selectMatrixFile();			
			}
			
			Thread t = monoExecution(f);
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
						
		} else if(args[0].equals("--dist")) {
			if(f != null){
				if(f.isDirectory()){
					distExecution(f);
					
				} else {
					
					if(divisionNumber == -1)
					{
						readPartition();
					}
					File [] matrices = matrixDivision(f);
					File dir = generateComponents(matrices);
					distExecution(dir);
				}
			} else {
				f = distInteractiveSel();
				distExecution(f);
			}
		}
	}
	
	private static void distExecution(File f)
	{
//		t.addComponent(FileRead.readFileComponent(f.getFile(), 1));
		
		if(timeLimit == -2)
		{
			readLimit();
		}
		
		FileRead.readComponents(f);		
		MHSDistributed mhs = new MHSDistributed();
		mhs.setTimeLimit(timeLimit);
		mhs.execute();
		System.out.println(mhs);
		File resultFile [] = getOutFile(f, 1,"_dist", "mhs", "");
		FileWrite fw = new FileWrite(resultFile[0]);
		fw.write(mhs.toString());
	}
	
	private static Thread monoExecution(File f){
		Matrix m = FileRead.readFileMatrix(f);
		MHSMonolithic mhs = new MHSMonolithic(m);
		
		if(timeLimit == -2)
		{
			readLimit();
		}
		mhs.setTimeLimit(timeLimit);
		
		mhs.setObserver(new ExecutionEvent() {
			
			@Override
			public void OnExecutionEnd(MHS sender) {
				System.out.println(sender);
				File outs [] = getOutFile(f, 1, "", "mhs", "");
				FileWrite fw = new FileWrite(outs[0]);
				fw.write(sender.toString());
			}
		});
		
		Thread t = new Thread(mhs);
		t.start();
		
		return t;
	}
	
	private static File[] matrixDivision(File f)
	{
		Matrix matrix = FileRead.readFileMatrix(f);
		Matrix [] matrices = matrix.divideRandomMatrix(divisionNumber);
		for(int i = 0; i < matrices.length; i++)
		{
			System.out.println(matrices[i]);
		}
		
		File dir = new File(f.getPath() + "_dist");
		if(dir.isDirectory())
		{
			for(File fDel : dir.listFiles())
			{
				fDel.delete();
			}
		}
		File [] filesout = getOutFile(f, divisionNumber, "", "matrix", f.getName() + "_dist");
				
		for(int i = 0; i < matrices.length; i++)
		{
			FileWrite fw = new FileWrite(filesout[i]);
			fw.write(matrices[i].toString());
		}
		
		return filesout;
	}
	
	private static File generateComponents(File [] matrices)
	{
		Thread [] t = new Thread[matrices.length];
		for(int i = 0; i < matrices.length; i++)
		{
			t[i] = monoExecution(matrices[i]);
		}
		
		for(int i = 0; i < t.length; i++)
		{
			try
			{
				t[i].join();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		return matrices[0].getParentFile();
	}
	
	private static File selectMatrixFile()
	{
		FileSelection fs = new FileSelection("Select input matrix file", "matrix");
		File f = fs.getFile();
		if(f == null)
		{
			System.out.println("File not selected. Termined");
			System.exit(0);
		}
		return f;
	}
	
	/**
	 * Creates the output solution file
	 * @param inFile the input file
	 * @return the output file
	 */
	public static File [] getOutFile(File inFile,int numberFiles, String nameExt, String ext, String dir){
		
		File [] out = new File[numberFiles];
		for(int i = 0; i < numberFiles; i++)
		{
			String name = inFile.getName();
			String[] fileName = name.split("[.]");
			StringBuilder fname = new StringBuilder();
			for (int j= 0; j < fileName.length - 1; j++){
				fname.append(fileName[j]);
				fname.append(".");
			}
			
			if(!nameExt.equals(""))
			{
				fname.deleteCharAt(fname.length() - 1);
				fname.append(nameExt + ".");
			}
			
			if(numberFiles > 1)
				fname.append("N" + i + ".");
			
			fname.append(ext);
			StringBuilder fpath = new StringBuilder();
			fpath.append(inFile.getParentFile());
			
			if(fpath.indexOf("/") >= 0)
			{
				if(!dir.equals(""))
				{
					fpath.append("/" + dir);
					File directory = new File(fpath.toString());
					if(!directory.exists())
						directory.mkdir();
				}
				fpath.append("/" + fname.toString());
			}
			else
			{
				if(!dir.equals(""))
				{
					fpath.append("\\" + dir);
					File directory = new File(fpath.toString());
					if(!directory.exists())
						directory.mkdir();
				}
				fpath.append("\\" + fname.toString());
			}
				
			
			out[i] = new File(fpath.toString());
		}
		return out;
	}
	
	public static void readLimit()
	{
		MyMenu menuTimeLimit = new MyMenu(YES_NO, "Do you want to insert a time limit?", true);
		int sel = menuTimeLimit.printMenu();
		if(sel == 0)
		{
			timeLimit = MyRead.readTimeLimit();
		}
		else
		{
			timeLimit = -1;
		}
	}
	
	public static void readPartition()
	{
		System.out.println("Insert the number of partitions: ");
		divisionNumber = MyRead.readLimitedInt(2, -1);
	}
	
	public static File distInteractiveSel()
	{
		String [] options = {"Select the matrix","Select the components"};
		MyMenu menu = new MyMenu(options, "Choose which file you want to load ", true);
		int sel = menu.printMenu();
		
		if(sel == 0)
		{
			File f = selectMatrixFile();
			

			if(divisionNumber == -1)
			{
				readPartition();
			}
			File [] matrices = matrixDivision(f);
			File dir = generateComponents(matrices);
			return dir;
			
		}else if (sel == 1)
		{
			DirectorySelection ds = new DirectorySelection("Select input directory");
			File f = ds.getFile();
			if(f == null)
			{
				System.out.println("File not selected. Termined");
				System.exit(0);
			}	
			return f;
		}
		
		return null;
	}
}
