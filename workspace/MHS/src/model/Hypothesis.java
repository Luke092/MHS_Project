package model;

import java.lang.Comparable;
import java.util.BitSet;
import java.util.Vector;

/**
 * Object representing the hypothesis
 *
 */
public abstract class Hypothesis implements Comparable<Hypothesis>, Cloneable{

	/**
	 * Bitset representing the binary interpretation of the hypothesis
	 */
	private BitSet bits;

	/**
	 * Bitset representing the support vector used during the execution
	 */
	public BitSet vector;

	/**
	 * Pointer to the parent that generates this hypothesis as a left child
	 */
	Hypothesis leftParent;

	/**
	 * Number of elements in the hypothesis
	 */
	int cM;

	/**
	 * Constructor of the hypothesis
	 * @param cM number of elements the hypothesis contains. Equal to the number of columns of the matrix
	 */
	public Hypothesis(int cM){
		this.bits = new BitSet(cM);
		this.cM = cM;
	}

	/**
	 * Gets the bitset representing the binary hypothesis
	 * @return a BitSet object
	 */
	public BitSet getBits(){
		return this.bits;
	}

	/**
	 * Sets a single bit in the bitset to the specified value
	 * @param index index of the bit to set
	 * @param value boolean value of the bit to set
	 */
	public void setBit(int index, boolean value){
		this.bits.set(index, value);
	}

	/**
	 * Sets a bitset for the hypothesis
	 * @param bs the new bitset
	 */
	public void setBits(BitSet bs){
		this.bits = bs;
	}

	/**
	 * 
	 */
	public abstract void setField();

	/**
	 * 
	 * @return true if the hypothesis is a solution, otherwise false
	 */
	public abstract boolean check();

	/**
	 * 
	 * @param h_ parent hypothesis
	 */
	public abstract void propagate(Hypothesis h_);

	/**
	 * set the left parent for the hypothesis
	 * @param _leftParent the parent hypothesis
	 */
	public void setLeftParent(Hypothesis _leftParent)
	{
		this.leftParent = _leftParent;
	}

	/**
	 * Generates the children hypothesis of this hypothesis. The children are selected pruning the H-tree
	 * @param _next list of the previous hypotheses's children to concatenate
	 * @param current list of the hypotheses analyzed at the current level
	 * @return the updated list of the children
	 */
	public Vector<Hypothesis> generateChildren(Vector<Hypothesis> _next, Vector<Hypothesis> current)
	{
		Vector<Hypothesis> next = _next;
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
					e.printStackTrace();
				}

			}
		}
		else
		{
			Hypothesis pred = this;
			// contains the index of pred in current list
			int predIndex = current.indexOf(this); 
			do
			{
				if(--predIndex >= 0)
					pred = current.get(predIndex);
				else
					pred = null;
			}
			while(!(pred == null || this.hammingDistance(pred) == 2));

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
						for(int j = this.leftMost(); j <= this.rightMost(); j++)
						{
							Hypothesis h2 = (Hypothesis) h1.clone();
							if(h1.getBits().get(j) != false)
							{
								h2.getBits().set(j, false);

								//Faulted algorithm
								//								if(pred == null || pred.compareTo(h2) != 0)
								//								{
								//									cond = false;
								//									Hypothesis finalH = (Hypothesis) h1.clone();
								//									finalH.getBits().set(this.rightMost(),false);
								//									
								//									while(pred != null && pred.compareTo(finalH) <= 0)
								//									{
								//										do
								//										{
								//											pred = current.prev(pred);
								//										}
								//										while(!(pred == null || this.hammingDistance(pred) == 2));
								//									}
								//									break;
								//								}
								//								else
								//								{
								//									h1.propagate(h2);
								//									do
								//									{
								//										pred = current.prev(pred);
								//									}
								//									while(!(pred == null || this.hammingDistance(pred) == 2));
								//								}

								// Brute-force
								//								if(!current.contains(h2)){
								//									cond = false;
								//									break;
								//								}

								// Correct algorithm
								while(!(pred == null || pred.compareTo(h2) == 0 || pred.compareTo(h2) >= 0)){
									if(--predIndex >= 0)
										pred = current.get(predIndex);
									else
										pred = null;
								}

								if(pred == null || pred.compareTo(h2) != 0){
									cond = false;
								} else {
									h1.propagate(pred); // pred == h2 but the vector object is filled
								}
							}

						}
						if(cond)
						{
							next.add(h1);
						}
					} catch (CloneNotSupportedException e)
					{
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

	/**
	 * Calculates the hamming distance between this hypothesis and another
	 * @param h the hypothesis to compare
	 * @return the int value of the hamming distance
	 */
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

	/**
	 * Gets the position of the leftmost true value in the bitset
	 * @return the index of the bit
	 */
	public int leftMost()
	{
		for(int i = 0; i < cM; i++)
			if(this.bits.get(i))
				return i;
		return -1;
	}

	/**
	 * Gets the position of the rightmost true value in the bitset
	 * @return the index of the bit
	 */
	public int rightMost()
	{
		for(int i = cM -1; i >= 0; i--)
			if(this.bits.get(i))
				return i;
		return -1;
	}

	/**
	 * Restores the dimension of the hypothesis
	 * @param deletedColumns the index of the matrix columns removed during the pruning phase.
	 */
	public void expandHypothesis(Vector<Integer> deletedColumns){
		int finalSize = this.cM + deletedColumns.size();
		BitSet newBits = new BitSet(finalSize);
		boolean setted = false;
		int hIndex = 0;
		for(int i = 0; i < finalSize; i++){
			setted = false;
			for (int j = 0; j < deletedColumns.size(); j++){
				if(deletedColumns.get(j) == i){
					newBits.set(i, false);
					setted = true;
					break;
				}
			}
			if(!setted){
				newBits.set(i, this.bits.get(hIndex));
				hIndex++;
			}
		}
		this.bits = newBits;
		this.cM = finalSize;
	}

	public void pruneHypothesis(Vector<Integer> deletedColumns){
		BitSet newBits = new BitSet(this.cM - deletedColumns.size());
		int newIndex = 0;
		int delColIndex = 0;
		for(int i = 0; i < this.cM; i++){
			boolean isDeleted = false;
			if(delColIndex < deletedColumns.size()){
				if(deletedColumns.get(delColIndex) == i){
					isDeleted = true;
					delColIndex++;					
				}
			}
			if(!isDeleted){
				newBits.set(newIndex, this.bits.get(i));
				newIndex++;
			}
		}
		this.bits = newBits;
	}

	@Override
	public String toString()
	{
		StringBuilder string = new StringBuilder();
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
		string.append(" -");
		return string.toString();
	}

}
