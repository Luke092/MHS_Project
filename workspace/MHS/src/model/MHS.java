package model;

import java.util.HashMap;
import java.util.Vector;

/**
 * Object containing the solution and the methods to resolve the problem considered
 *
 */
public abstract class MHS {
	
	/**
	 * MHS vector, store all mhs found
	 */
	Vector<Hypothesis> delta;
	
	/**
	 * State if the algorithm is stopped before the end of the analysis
	 */
	boolean ended = false;
	
	/**
	 * Level of H witch the algorithm was stopped
	 */
	int level = 0;
	
	/**
	 * Starting time of the execution
	 */
	double startTime = 0;
	
	/**
	 * Ending time of the execution
	 */
	double endTime = 0;
	
	/**
	 * Time limit of the execution
	 */
	double timeLimit = 0;
	
	/**
	 * Sets the starting time of the execution
	 */
	public void setStartTime()
	{		
		this.startTime = (double) System.nanoTime()/Math.pow(10, 9);
	}
	
	/**
	 * Sets the time limit of the execution
	 * @param limit time limit of the execution
	 */
	public void setTimeLimit(double limit)
	{
		this.timeLimit = limit;
	}
	
	/**
	 * Gets the period of the execution
	 * @return the number of seconds used to resolve the problem
	 */
	public double getDurationTime()
	{
		return this.endTime - this.startTime;
	}
	
	/**
	 * Explores the Hypothesis space
	 */
	public abstract void explore();
	
	/**
	 * Gets various statistics about the execution
	 * @return the string containing the statistics
	 */
	public abstract String statistics(); 
	
	/**
	 * Calculates the distribution of the MHS by cardinality 
	 */
	public String calculateCardinality(){
		HashMap<Integer, Integer> distribution = new HashMap<>();
		for(int i = 0; i < this.delta.size(); i++){
			Hypothesis h = delta.get(i);
			int k = h.getBits().cardinality();
			if(distribution.containsKey(k)){
				distribution.replace(k, distribution.get(k) + 1);
			} else {
				distribution.put(k, 1);
			}
		}
		StringBuffer str = new StringBuffer();
		for(Integer k: distribution.keySet()){
			str.append(";;; Cardinality: ");
			str.append(k);
			str.append("\t");
			str.append("#: ");
			str.append(distribution.get(k));
			str.append("\n");
		}
		return str.toString();
	}
	
	/**
	 * Gets the vector containing the solutions to the problem
	 * @return the vector containing the solutions
	 */
	public Vector<Hypothesis> getDelta(){
		return this.delta;
	}
	
	/**
	 * Restores the real dimension of the hypotheses 
	 */
	public void expandHypothesis(){
		for(Hypothesis h: this.delta){
			h.expandHypothesis(Matrix.getInstance().getDeletedColumns());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.statistics());
		
		for(Hypothesis h: this.delta){
			sb.append(h.toString());
			sb.append("\n");
		}
		
		return sb.toString();
	}
	

}
