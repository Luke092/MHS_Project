import model.Hypothesis;
import model.Matrix;
import model.MonoHypothesis;
import model.OrderedHList;
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
//		matrix.pruneMatrix();
//		System.out.println(matrix);
//		matrix.reconstructMatrix();
//		System.out.println(matrix);
		
		OrderedHList delta = exploreHMonolithic(matrix.getcM(), matrix.getcN());
		System.out.println(delta);
	}
	
	public static OrderedHList exploreHMonolithic(int cM, int cN)
	{
		MonoHypothesis h0 = new MonoHypothesis(cM, cN);
		h0.setField();
		OrderedHList current = new OrderedHList();
		current.add(h0);
		OrderedHList delta = new OrderedHList();
		
		do
		{
			OrderedHList next = new OrderedHList();
			for(Hypothesis h : current)
			{
				System.out.println(h);
				if(h.check())
				{
					delta.add(h);
					current.remove(h);
				}
				else
				{
					next = h.generateChildren(next, current);
				}
				System.out.println(next);
			}
			current = next;
		}
		while(current.size() != 0);
		return delta;
	}

}
