package model;

import java.util.BitSet;
import java.util.Vector;

public class Components {

	private Vector<Component> components;
	
	private Vector<Integer> deletedColumns;

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
		
		this.deletedColumns = new Vector<>();
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

	/**
	 * Delete empty columns
	 */
	public void pruneComponents(){
		for(int j = this.getcM() - 1; j >= 0 ; j--){
			boolean isColumnEmpty = true;
			for(Component c: this.components){
				if(!c.isColumnEmpty(j)){
					isColumnEmpty = false;
					break;
				}
			}
			if(isColumnEmpty){
				this.deletedColumns.add(j);
			}
		}
		for(Component c: this.components){
			c.pruneComponent(this.deletedColumns);
		}
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

	public Vector<Integer> getDeletedColumns() {
		return this.deletedColumns;
	}

}
