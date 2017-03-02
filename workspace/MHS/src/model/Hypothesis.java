package model;

import java.util.BitSet;

public abstract class Hypothesis {
	
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

}
