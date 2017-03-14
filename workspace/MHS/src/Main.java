import model.Components;
import model.MHSDistributed;
import model.MHSMonolithic;
import model.Matrix;
import utility.FileRead;
import utility.FileWrite;
import view.FileSelection;
import java.io.*;

public class Main
{
	/**
	 * Time limit for the execution
	 */
	private static double timeLimit = 600;
	private static int divisionNumber = 3;
	
	public static void main(String[] args)
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
		
		for(int i = 0; i < 	Nmatrices.length; i++)
		{
			Nmatrices[i].pruneMatrix();
			System.out.println(Nmatrices[i]);
			
			MHSMonolithic mhs = new MHSMonolithic(Nmatrices[i]);
			mhs.setStartTime();
			mhs.setTimeLimit(timeLimit);
			mhs.explore();
			Nmatrices[i].reconstructMatrix();
			mhs.expandHypothesis();
			
			System.out.println(mhs);		
			FileWrite fw = new FileWrite(filesout2[i]);
			fw.write(mhs.toString());
		}
		
		//DISTRIBUTED MHS
		Components t = Components.getInstance();
//		t.addComponent(FileRead.readFileComponent(f.getFile(), 1));
		FileRead.readComponents(new File(f.getFile().getAbsolutePath()+ "_dist"));
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
	public static File [] getOutFile(File inFile,int numberFiles,String nameExt, String ext, String dir){
		
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
