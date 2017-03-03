import model.Matrix;
import utility.FileRead;
import view.FileSelection;

public class Main
{

	public static void main(String[] args)
	{
		FileSelection f = new FileSelection("Select input matrix file", "matrix");
		FileRead.readFile(f.getFile());
		System.out.println(Matrix.getInstance());
	}

}
