import model.*;
import model.events.ExecutionEvent;
import utility.*;
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
		File f;
		
		if(args.length > 2){
			for(int j = 2; j < args.length; j++){
				switch(args[j]){
				case "--timeLimit":
					try{
						timeLimit = Double.parseDouble(args[++j]);
					} catch(ArrayIndexOutOfBoundsException ex){
						System.err.println("L'opzione \"--timeLimit\" richiede il tempo in secondi!");
						System.exit(-2);
					} catch (NumberFormatException ex){
						System.err.println("L'opzione \"--timeLimit\" richiede il tempo in secondi!");
						System.exit(-2);
					}
					break;
				case "--partitions":
					try{
						divisionNumber = Integer.parseInt(args[++j]);
					} catch(ArrayIndexOutOfBoundsException ex){
						System.err.println("L'opzione \"--partitions\" richiede un numero intero!");
						System.exit(-2);
					} catch (NumberFormatException ex){
						System.err.println("L'opzione \"--partitions\" richiede un numero intero!");
						System.exit(-2);
					}
					break;
				}
			}
		}
		
		if(args[0].equals("--mono")){
			if(args.length > 1){
				String path = args[1];
				f = new File(path);
				
				if(!(f.exists() && f.isFile())){
					System.err.println("Il file non esiste!");
					System.exit(-1);
				}
			} else {
				FileSelection fs = new FileSelection("Select input matrix file", "matrix");
				f = fs.getFile();
			}
			
			Thread t = monoExecution(f);
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
						
		} else if(args[0].equals("--dist")) {
			if(args.length > 1){
				File tmp = new File(args[1]);
				if(tmp.isDirectory()){
					// TODO: already have components into directory
				} else {
					// TODO: create partitions
				}
			} else {
				// TODO: matrix selection
			}
		}
	}
	
	private static Thread monoExecution(File f){
		Matrix m = FileRead.readFileMatrix(f);
		MHSMonolithic mhs = new MHSMonolithic(m);
		mhs.setTimeLimit(timeLimit);
		
		mhs.setObserver(new ExecutionEvent() {
			
			@Override
			public void OnExecutionEnd(MHS sender) {
				System.out.println(sender);
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
