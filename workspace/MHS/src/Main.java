import model.MHSMonolithic;
import model.Matrix;
import utility.FileRead;
import utility.FileWrite;
import view.FileSelection;
import java.io.*;

public class Main
{

	public static void main(String[] args)
	{
		FileSelection f = new FileSelection("Select input matrix file", "matrix");
		FileRead.readFile(f.getFile());
		Matrix matrix = Matrix.getInstance();
		System.out.println(matrix);
		matrix.pruneMatrix();
		System.out.println(matrix);
		
		MHSMonolithic mhs = new MHSMonolithic();
		mhs.explore();
		matrix.reconstructMatrix();
		mhs.expandHypothesis();
		System.out.println(mhs);
		
		FileWrite fw = new FileWrite(getOutFile(f.getFile()));
		fw.write(mhs.toString());
	}
	
	public static File getOutFile(File inFile){
		String name = inFile.getName();
		String[] fileName = name.split("[.]");
		fileName[fileName.length - 1] = "mhs";
		StringBuilder fname = new StringBuilder();
		for (int i= 0; i < fileName.length; i++){
			fname.append(fileName[i]);
			fname.append(".");
		}
		fname.deleteCharAt(fname.length() - 1);
		String[] path = inFile.getPath().split("[/]");
		StringBuilder fpath = new StringBuilder();
		for(int i = 0; i < path.length - 1; i++){
			fpath.append(path[i]);
			fpath.append("/");
		}
		fpath.append(fname.toString());
		
		File out = new File(fpath.toString());
		return out;
	}
	
	

}
