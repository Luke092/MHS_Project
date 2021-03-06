package model;

import java.util.Collections;
import java.util.Vector;

public class MHSDistributed extends MHS{

	@Override
	public void explore() {
		Components comp = Components.getInstance();
		int dim = comp.getcM() - comp.getDeletedColumns().size();
		if(dim < 0){
			// components has no elements, so no calculation is needed
			return;
		}
		DistHypothesis h0 = new DistHypothesis(dim, comp.getK());
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
//					System.out.println("Added h = " + h + " to delta.");
					this.delta.add(h);
					current.remove(i);
					i--;
				}
				else
				{
					next = h.generateChildren(next, current);
				}
//				System.out.println("H: " + h + " vector:" + ((DistHypothesis)h).getVector());
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
		Components components = Components.getInstance();
		StringBuilder sb = new StringBuilder();
		
		
		// Column size
		sb.append(";;; |M| = ");
		sb.append(components.getcM());
		sb.append("\n");
		
		//Deleted Columns
		sb.append(";;; |M'| = ");
		sb.append(components.getcM() - components.getDeletedColumns().size());
		sb.append("\n");
		
		// Component size
		sb.append(";;; # of components = ");
		sb.append(components.getK());
		sb.append("\n");
		
		// Components cardinality
		sb.append(components.getComponentsCardinality());
		
		
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
			h.expandHypothesis(Components.getInstance().getDeletedColumns());
		}
	}

	@Override
	public void execute() {
		Components.getInstance().pruneComponents();
		this.setStartTime();
		this.explore();
		this.expandHypothesis();		
	}

}
