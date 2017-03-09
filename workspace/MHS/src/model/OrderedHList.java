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
	
	/**
	 * Returns the element preceding the selected one in the vector
	 * @param h the vector element
	 * @return the element preceding the input one
	 */
	public Hypothesis prev(Hypothesis h)
	{
		int indexH = this.indexOf(h) - 1;
		if(indexH >= 0)
			return this.get(indexH);
		else
			return null;
	}
}
