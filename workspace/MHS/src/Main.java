import model.*;
import model.events.ExecutionEvent;
import utility.*;
import view.DirectorySelection;
import view.FileSelection;
import java.io.*;
import java.util.Vector;

public class Main
{
	/**
	 * Time limit for the execution
	 */
	private static double timeLimit = 60;
	private static int divisionNumber = 2;
	
	public static void main(String[] args){
		if(args.length == 0){
			//TODO: interactive choice
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
				FileSelection fs = new FileSelection("Select input matrix file", "matrix");
				f = fs.getFile();
				if(f == null)
				{
					System.out.println("File not selected. Termined");
					System.exit(0);
				}				
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
					// TODO: create partitions
				}
			} else {
				// TODO: ask user if directory or file
				DirectorySelection ds = new DirectorySelection("Select input directory");
				f = ds.getFile();
				if(f == null)
				{
					System.out.println("File not selected. Termined");
					System.exit(0);
				}	
				distExecution(f);
			}
		}
	}
	
	private static void distExecution(File f)
	{
		Components t = Components.getInstance();
//		t.addComponent(FileRead.readFileComponent(f.getFile(), 1));
		FileRead.readComponents(f);
		t.pruneComponents();
		
		MHSDistributed mhs = new MHSDistributed();
		mhs.setStartTime();
		mhs.setTimeLimit(timeLimit);
		mhs.explore();
		System.out.println(mhs);
		File resultFile [] = getOutFile(f, 1,"_dist", "mhs", "");
		FileWrite fw = new FileWrite(resultFile[0]);
		fw.write(mhs.toString());
	}
	
	private static Thread monoExecution(File f){
		Matrix m = FileRead.readFileMatrix(f);
		MHSMonolithic mhs = new MHSMonolithic(m);
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
	
	public static void main2(String[] args)
	{
		//MATRIX SELECTION
		FileSelection f = new FileSelection("Select input matrix file", "matrix");

		Matrix matrix =	FileRead.readFileMatrix(f.getFile());
		System.out.println(matrix);
				
		//SINGLE MATRIX PRUNING + MONOMHS
//		matrix.pruneMatrix();
//		System.out.println(matrix);
//		
//		MHSMonolithic mhs = new MHSMonolithic(matrix);
//		mhs.setStartTime();
//		mhs.setTimeLimit(timeLimit);
//		mhs.explore();
//		matrix.reconstructMatrix();
//		mhs.expandHypothesis();
//		
//		System.out.println(mhs);		
//		FileWrite fw = new FileWrite(getOutFile(f.getFile()));
//		fw.write(mhs.toString());
		
		//MATRIX DIVISION
		Matrix [] matrices = matrix.divideRandomMatrix(divisionNumber);
		for(int i = 0; i < matrices.length; i++)
		{
			System.out.println(matrices[i]);
		}
		
		File [] filesout = getOutFile(f.getFile(), divisionNumber, "", "matrix", f.getFile().getName() + "_dist");
		
		for(int i = 0; i < matrices.length; i++)
		{
			FileWrite fw = new FileWrite(filesout[i]);
			fw.write(matrices[i].toString());
		}
		
		//COMPONENTS CREATION
		Matrix [] Nmatrices = FileRead.readMatrices(new File(f.getFile().getAbsolutePath()+ "_dist"));
		File [] filesout2 = getOutFile(f.getFile(), divisionNumber, "", "mhs", f.getFile().getName() + "_dist");
		
		Vector<MHSMonolithic> mhss = new Vector<>();
		
		for(int i = 0; i < 	Nmatrices.length; i++)
		{
//			Nmatrices[i].pruneMatrix();
//			System.out.println(Nmatrices[i]);
			
			MHSMonolithic mhs = new MHSMonolithic(Nmatrices[i]);
//			mhs.setStartTime();
//			mhs.setTimeLimit(timeLimit);
//			mhs.explore();
			mhs.run();
			mhss.add(mhs);
//			Nmatrices[i].reconstructMatrix();
//			mhs.expandHypothesis();
//			
//			System.out.println(mhs);		
//			FileWrite fw = new FileWrite(filesout2[i]);
//			fw.write(mhs.toString());
		}
		
		boolean isThreadsFinished;
		
		do{
			isThreadsFinished = true;
			for(MHSMonolithic mhs: mhss){
				isThreadsFinished = isThreadsFinished && mhs.isThreadEnded;
			}
		}while(!isThreadsFinished);
		
		//DISTRIBUTED MHS
		Components t = Components.getInstance();
//		t.addComponent(FileRead.readFileComponent(f.getFile(), 1));
		FileRead.readComponents(new File(f.getFile().getAbsolutePath()+ "_dist"));
		t.pruneComponents();
		
		MHSDistributed mhs = new MHSDistributed();
		mhs.setStartTime();
		mhs.setTimeLimit(timeLimit);
		mhs.explore();
		System.out.println(mhs);
		File resultFile [] = getOutFile(f.getFile(), 1,"_dist", "mhs", f.getFile().getName() + "_dist");
		FileWrite fw = new FileWrite(resultFile[0]);
		fw.write(mhs.toString());
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
	
	

}
