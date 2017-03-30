package model;

import java.util.Collections;
import java.util.Vector;

/**
 * Monolithic version of the resolution
 *
 */
public class MHSMonolithic extends MHS implements Runnable {
	
	private Matrix matrix = new Matrix();
	
	public boolean isThreadEnded = false;
	
	public MHSMonolithic(Matrix _matrix)
	{
		this.matrix = _matrix;
	}
	
	@Override
	public void run() {
		this.execute();
//		this.isThreadEnded = true;
		this.executionEnd();
	}
	
	@Override
	public void explore()
	{
		MonoHypothesis h0 = new MonoHypothesis(this.matrix.getcM1(), this.matrix.getcN(), this.matrix);
		h0.setField();
		Vector<Hypothesis> current = new Vector<Hypothesis>();
		current.add(h0);
		this.delta = new Vector<>();
		
		boolean timeLimitReached = false;
		
		do
		{
//			System.out.println("Lvl: " + this.level);
//			System.out.println("Current: " + current);
			Vector<Hypothesis> next = new Vector<Hypothesis>();
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
				if((this.timeLimit >= 0) && ((this.endTime - this.startTime) >= this.timeLimit))
				{
					timeLimitReached = true;
					
					break;
				}
			}
			this.level++; // increment the level
//			System.out.println("Lv: " + this.level + "\n" + next);
			Collections.sort(next, Collections.reverseOrder());
			current = next;
		}
		while(current.size() != 0 && !timeLimitReached);
		if(!timeLimitReached)
			this.ended = true; // the algorithm has come to the end
	}
	
	@Override
	public String statistics() {
		StringBuilder sb = new StringBuilder();
		
		// matrix summary
		sb.append(";;; NxM[");
		sb.append(this.matrix.getcN());
		sb.append(", ");
		sb.append(this.matrix.getcM());
		sb.append("]\n");
		
		// pruned matrix column size
		sb.append(";;; |M'| = " + this.matrix.getcM1());
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

	public void expandHypothesis()
	{
		for(Hypothesis h: this.delta){
			h.expandHypothesis(this.matrix.getDeletedColumns());
		}
	}

	@Override
	public void execute() {
		this.matrix.pruneMatrix();
		this.setStartTime();
		this.explore();
		this.expandHypothesis();
	}
}
