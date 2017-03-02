package model;

import java.util.BitSet;

public class MonoHypothesis extends Hypothesis {

	private int cN;
	
	public MonoHypothesis(int cM, int cN) {
		super(cM);
		this.vector = new BitSet(cN);
		this.cN = cN;
	}
	
	@Override
	public void setField(){
		
	}
	
	@Override
	public boolean check(){
		for(int i = 0; i < this.vector.size(); i++){
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
}
