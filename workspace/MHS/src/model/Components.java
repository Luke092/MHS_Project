package model;

import java.util.BitSet;
import java.util.Vector;

public class Components {
	
	private Vector<Component> components;
	
	private static Components instance;
	
	private int cM;
	
	public static Components getInstance(){
		if(instance == null){
			instance = new Components();
		}
		return instance;
	}
	
	private Components(){
		this.components = new Vector<>();
		this.cM = -1;
	}
	
	public void addComponent(Component c){
		this.components.add(c);
	}
	
	public BitSet checkHypothesis(Hypothesis h){
		BitSet result = new BitSet(this.components.size());
		result.clear();
		for(int i = 0; i < this.components.size(); i++){
			if(this.components.get(i).isMHS(h)){
				result.set(i);
			}
		}
		return result;
	}
	
	public int getK(){
		return this.components.size();
	}
	
	public int getcM(){
		return this.cM;
	}
	
	public void setCM(int value){
		this.cM = value;
	}

}
