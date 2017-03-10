package model;

import java.util.Vector;

/**
 * Monolithic version of the resolution
 *
 */
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
		
		boolean timeLimitReached = false;
		
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
				
				this.endTime = (double) System.nanoTime()/Math.pow(10, 9);
				if((this.endTime - this.startTime) >= this.timeLimit)
				{
					timeLimitReached = true;
					
					break;
				}
			}
			this.level++; // increment the level
//			System.out.println("Lv: " + this.level + "\n" + next);
			current = next;
		}
		while(current.size() != 0 && !timeLimitReached);
		if(!timeLimitReached)
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
		
		// pruned matrix column size
		sb.append(";;; |M'| = " + m.getcM1());
		sb.append("\n");
		
		
		//level reached (if not completed)
		if(!this.ended)
		{
			sb.append(";;; Execution stopped before conclusion \n");
			sb.append(";;; Level reached: " + this.level);
			sb.append("\n");
		}
		
		// mhs dimension
		sb.append(";;; |MHS| = ");
		sb.append(delta.size());
		sb.append("\n");
		
		// distribution
		sb.append(";;; Distribution Map\n");
		sb.append(this.calculateCardinality());
		
		//execution time
		sb.append(";;; Execution Time: " + this.getDurationTime() + " seconds\n");
		
		return sb.toString();
	}
}
