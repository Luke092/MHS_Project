package model;

import java.util.BitSet;

public class DistHypothesis extends Hypothesis {
	
	private BitSet vector;
	
	private int k;

	public DistHypothesis(int cM, int k) {
		super(cM);
		this.k = k;
		this.vector = new BitSet(k);
	}

	@Override
	public void setField() {
		if(this.getBits().cardinality() == 0){
			this.vector.clear();
		} else {
			this.vector = Components.getInstance().checkHypothesis(this);
		}
	}

	@Override
	public boolean check() {
		boolean cond = true;
		int i = 0;
		while(i < this.k && cond){
			if(!this.vector.get(i)){
				cond = false;
			}
			i++;
		}
		return cond;
	}

	@Override
	public void propagate(Hypothesis h_) {
		System.out.println("H: " + this + " H': " + h_);
		System.out.println("h.vector = " + this.vector + " h'.vector = " + ((DistHypothesis)h_).getVector());
		this.vector.or(((DistHypothesis)h_).getVector());
		System.out.println("result.vector = " + this.vector);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		DistHypothesis h = new DistHypothesis(this.cM, this.k);
		h.setBits((BitSet)this.getBits().clone());
		return h;
	}
	
	public BitSet getVector(){
		return this.vector;
	}

}
