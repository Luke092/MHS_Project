package model;

import java.util.BitSet;
import java.util.Vector;

public class MonoHypothesis extends Hypothesis {

	private int cN;
	
	public MonoHypothesis(int cM, int cN) {
		super(cM);
		this.vector = new BitSet(cN);
		this.cN = cN;
	}
	
	
	
	@Override
	/**
	 * Set the support vector used in the MHS problem resolution
	 */
	public void setField(){
		int count = 0;
		int posSinglet = -1;
		
		for(int i = 0; i < cM; i++)
		{
			if(this.getBits().get(i) == true)
				count++;
			if(count == 1 && posSinglet == -1)
				posSinglet = i;
		}
		if(count != 1)
			posSinglet = -1;
		
		if(count == 0)
		{
			vector.clear();
		}
		else if(count == 1)
		{
			Matrix matrix = Matrix.getInstance();
			Vector<Byte> column = matrix.getColumn(posSinglet);
			vector.clear();
			
			for(int i = 0; i < cN; i++)
			{
				if(column.get(i) == (byte) 1)
					vector.set(i, true);
			}
		}
		else if(count > 1)
		{
			BitSet lParent = leftParent.getBits();
			BitSet child = this.getBits();
			int indexColumnSingletParent = -1;
			for(int i = 0; i < cM; i++)
			{
				if(lParent.get(i) ^ child.get(i))
				{
					indexColumnSingletParent = i;
					break;
				}
			}
			BitSet vectorLParent = leftParent.vector;
			
			Matrix matrix = Matrix.getInstance();
			Vector <Byte> columnSingletParent = matrix.getColumn(indexColumnSingletParent);
			
			for(int i = 0; i < cN; i++)
			{
				if(columnSingletParent.get(i) == 0)
				{
					if(!vectorLParent.get(i))
						vector.set(i, false);
					else
						vector.set(i, true);
				}
				else
					vector.set(i, true);
			}
		}
	}
	
	/**
	 * Check if the hypothesis is a valid solution for the MHS problem
	 */
	@Override
	public boolean check(){
		for(int i = 0; i < cN; i++){
			if(vector.get(i) == false){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Empty in monolithic case
	 */
	@Override
	public void propagate(Hypothesis h_){}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		MonoHypothesis newInstance = new MonoHypothesis(this.cM, this.cN);
		newInstance.setBits((BitSet)this.getBits().clone());
		return newInstance;
	}
}
