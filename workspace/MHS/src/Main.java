import model.MHSMonolithic;
import model.Matrix;
import utility.FileRead;
import view.FileSelection;

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
//		matrix.reconstructMatrix();
//		System.out.println(matrix);
		
		MHSMonolithic mhs = new MHSMonolithic();
		mhs.explore();
		//Vector<Hypothesis> delta = mhs.getDelta(); 
		System.out.println(mhs);
	}
	
	

}
