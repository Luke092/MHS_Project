package model;

import java.util.Vector;

public class OrderedHList extends Vector<Hypothesis>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public synchronized boolean add(Hypothesis e)
	{
		int indexInsert= 0;
		for(int i = 0; i < this.size(); i++)
		{
			int result = e.compareTo(this.get(i));
			if(result > 0)
			{
				break;
			}
			if(result == 0)
			{
				return false;
			}
			indexInsert++;
		}
		super.add(indexInsert, e);
		return true;
	}
	
	@Override
	public synchronized Hypothesis get(int index) {
		if(index >= 0)
			return super.get(index);
		else
			return null;
	}
	
	/**
	 * Returns the element preceding the selected one in the vector
	 * @param h the vector element
	 * @return the element preceding the input one
	 */
	// Inefficient way to get prev
//	public Hypothesis prev(Hypothesis h)
//	{
//		int indexH = this.indexOf(h) - 1;
//		if(indexH >= 0)
//			return this.get(indexH);
//		else
//			return null;
//	}
	
	// Check if an Hypothesis in into the list
//	@Override
//	public boolean contains(Object o) {
//		boolean found = false;
//		for(Hypothesis h: this){
//			if(h.compareTo((Hypothesis) o) == 0){
//				found = true;
//				break;
//			}
//		}
//		return found;
//	}
}
