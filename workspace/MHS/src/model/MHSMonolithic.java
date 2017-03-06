package model;

import java.util.Vector;

public class MHSMonolithic extends MHS {
	
	@Override
	public void explore()
	{
		Matrix m = Matrix.getInstance();
		MonoHypothesis h0 = new MonoHypothesis(m.getcM(), m.getcN());
		h0.setField();
		OrderedHList current = new OrderedHList();
		current.add(h0);
		this.delta = new Vector<>();
		
		do
		{
//			System.out.println("Lvl: " + this.level);
//			System.out.println("Current: " + current);
			OrderedHList next = new OrderedHList();
			for(int i = 0; i < current.size(); i++)
			{
				Hypothesis h = current.get(i);
				if(h.check())
				{
					this.delta.add(h);
					current.remove(i);
					i--;
				}
				else
				{
					next = h.generateChildren(next, current);
				}
//				System.out.println("H: " + h);
//				System.out.println("Next: " + next);
			}
			this.level++; // increment the level
//			System.out.println("Delta lv: " + this.level + " = " + this.delta);
			current = next;
		}
		while(current.size() != 0);
		this.ended = true; // the algorithm has come to the end
	}
	
	@Override
	public String statistics() {
		Matrix m = Matrix.getInstance();
		StringBuilder sb = new StringBuilder();
		
		// matrix summary
		sb.append(";;; M[");
		sb.append(m.getcN());
		sb.append(", ");
		sb.append(m.getcM());
		sb.append("]\n");
		
		// mhs dimension
		sb.append(";;; |MHS| = ");
		sb.append(delta.size());
		sb.append("\n");
		
		// distribution
		sb.append(";;; Distribution Map \"Cardinality\"=\"# of mhs\"\n");
		sb.append(";;; ");
		sb.append(this.calculateCardinality());
		sb.append("\n");
		
		return sb.toString();
	}
}
