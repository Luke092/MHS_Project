package model;

import java.lang.Comparable;
import java.util.BitSet;

public abstract class Hypothesis implements Comparable<Hypothesis> {
	
	private BitSet bits;
	BitSet vector;
	
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
	
	public abstract void setField();
	
	public abstract boolean check();
	
	public abstract void propagate(Hypothesis h_);

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

}
