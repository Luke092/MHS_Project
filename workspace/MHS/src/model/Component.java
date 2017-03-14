package model;

import java.util.LinkedList;

public class Component {

	private LinkedList<Hypothesis> queue;
	
	public Component(){
		this.queue = new LinkedList<>();
	}
	
	public void add(Hypothesis h){
		this.queue.add(h);
	}
	
	public boolean isMHS(Hypothesis h){
		Hypothesis head = this.queue.peek();
		int i = 0;
		
		while(head != null && h.compareTo(head) <= 0){
			if(h.getBits().cardinality() == head.getBits().cardinality()){
				int compare = h.compareTo(head);
				if(compare > 0){
					return false;
				} else if(compare == 0){
					this.queue.remove(i);
					return true;
				}
			} else if (h.getBits().cardinality() < head.getBits().cardinality()) {
				return false;
			}
			if(++i < this.queue.size())
				head = this.queue.get(i);
			else
				head = null;
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

}
