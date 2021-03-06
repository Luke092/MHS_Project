package model;

import java.util.LinkedList;
import java.util.Vector;

public class Component {

	private LinkedList<Hypothesis> queue;
	
	public Component(){
		this.queue = new LinkedList<>();
	}
	
	public void add(Hypothesis h){
		this.queue.add(h);
	}
	
	public boolean isColumnEmpty(int column){
		boolean isEmpty = true;
		for(Hypothesis h : this.queue){
			if(h.getBits().get(column)){
				isEmpty = false;
				break;
			}
		}
		return isEmpty;
	}
	
	public boolean isMHS(Hypothesis h){
		Hypothesis head = this.queue.peek();
		int i = 0;
		int compare = (head == null)? 2 : h.compareTo(head);
		while(head != null && compare <= 0){

			if(compare == 0){
				this.queue.remove(i);
				return true;
			}
			else if (h.getCardinality() < head.getCardinality()) {
				return false;
			}
			if(++i < this.queue.size())
				head = this.queue.get(i);
			else
				head = null;
			
			compare = (head == null)? 2 : h.compareTo(head);
		}
		
		
		return false;
		
//		for(int i = 0; i < this.queue.size(); i++){
//			if(h.compareTo(this.queue.get(i)) == 0){
//				return true;
//			}
//		}
//		return false;
	}
	
	
	@Override
	public String toString() {
		return queue.toString();
	}

	public void pruneComponent(Vector<Integer> deletedColumns) {
		for(Hypothesis h: this.queue){
			h.pruneHypothesis(deletedColumns);
		}
	}
	
	public int getSize(){
		return this.queue.size();
	}

}
