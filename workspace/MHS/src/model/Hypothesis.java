package model;

import java.lang.Comparable;
import java.util.BitSet;

public abstract class Hypothesis implements Comparable<Hypothesis>, Cloneable{
	
	private BitSet bits;
	BitSet vector;
	Hypothesis leftParent;
	
	int cM;
	
	public Hypothesis(int cM){
		this.bits = new BitSet(cM);
		this.cM = cM;
	}
	
	public void setH(Hypothesis h_){
		this.bits = h_.getBits();
	}
	
	public BitSet getBits(){
		return this.bits;
	}
	
	public void setBit(int index, boolean value){
		this.bits.set(index, value);
	}
	
	public void setBits(BitSet bs){
		this.bits = bs;
	}
	
	public abstract void setField();
	
	public abstract boolean check();
	
	public abstract void propagate(Hypothesis h_);

	/**
	 * set the left parent for the hypothesis
	 * @param _leftParent the parent hypothesis
	 */
	public void setLeftParent(Hypothesis _leftParent)
	{
		this.leftParent = _leftParent;
	}
		
	public OrderedHList generateChildren(OrderedHList _next, OrderedHList current)
	{
		OrderedHList next = _next;
		if(this.bits.isEmpty())
		{
			for(int i = 0; i < this.cM; i++)
			{
				try
				{
					Hypothesis h1 =(Hypothesis) this.clone();
					h1.setBit(i, true);
					h1.setLeftParent(this);
					h1.setField();
					h1.propagate(this);
					next.add(h1);
				} catch (CloneNotSupportedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		else
		{
//			int cont; 
			Hypothesis pred = this;
			do
			{
				pred = current.prev(pred);
			}
			while(!(pred == null || this.hammingDistance(pred) == 2));
			
			
//			if(pred != null)
//				cont = 0;
			
			for(int i = this.leftMost() - 1; i >= 0; i--)
			{
				if(pred != null)
				{
					try
					{
						Hypothesis h1 = (Hypothesis) this.clone();
						h1.getBits().set(i, true);
						h1.setLeftParent(this);
						h1.setField();
						h1.propagate(this);
						boolean cond = true;
						for(int j = this.leftMost(); j < this.rightMost() - 1; j++)
						{
							Hypothesis h2 = (Hypothesis) h1.clone();
							if(h2.getBits().get(j) != false)
							{
								h2.getBits().set(j, true);
								if(pred.compareTo(h2) != 0)
								{
									cond = false;
									Hypothesis finalH = (Hypothesis) h1.clone();
									finalH.getBits().set(this.rightMost(),false);
									
									while(pred != null && pred.compareTo(finalH) <= 0)
									{
										do
										{
											pred = current.prev(pred);
										}
										while(!(pred == null || this.hammingDistance(pred) == 2));
									}
									break;
								}
							}
							else
							{
								h1.propagate(h2);
								do
								{
									pred = current.prev(pred);
								}
								while(!(pred == null || this.hammingDistance(pred) == 2));
							}
							
						}
						if(cond)
						{
							next.add(h1);
							//cont++;
						}
					} catch (CloneNotSupportedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		
		return next;
	}
	
	@Override
	public abstract Object clone() throws CloneNotSupportedException;
	
	@Override
	public int compareTo(Hypothesis o)
	{
		BitSet h2 = o.getBits();
		for(int i = 0; i < cM; i++)
		{
			if(this.bits.get(i) != h2.get(i))
			{
				if(this.bits.get(i))
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}
		}
		return 0;
	}
	
	public int hammingDistance(Hypothesis h)
	{
		int count = 0;
		for(int i = 0; i < cM; i++)
		{
			if(this.bits.get(i) != h.getBits().get(i))
				count++;
		}
		return count;
	}
	
	public int leftMost()
	{
		for(int i = 0; i < cM; i++)
			if(this.bits.get(i))
				return i;
		return -1;
	}
	
	public int rightMost()
	{
		for(int i = cM -1; i >= 0; i--)
			if(this.bits.get(i))
				return i;
		return -1;
	}
	
	@Override
	public String toString()
	{
		StringBuilder string = new StringBuilder();
		string.append("{");
		for(int i = 0; i < cM; i++)
		{
			if(this.bits.get(i))
			{
				string.append("1 ");
			}
			else
			{
				string.append("0 ");
			}
		}
		string.deleteCharAt(string.length()-1);
		string.append("}");
		return string.toString();
	}

}
