package model;

import java.util.BitSet;
import java.util.Vector;

public class Components {

	private Vector<Component> components;
	
	private Vector<Integer> deletedColumns;

	private static Components instance;

	private int cM;
	
	private StringBuilder componentsCardinality;

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
		
		this.componentsCardinality = new StringBuilder();
	}

	public void addComponent(Component c){
		this.components.add(c);
		this.componentsCardinality.append(";;; Component C" + 
					(this.components.size() - 1) + 
					": |MHS|=" + c.getSize() + "\n");
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
		for(int j = 0; j < this.cM ; j++){
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
	
	public String getComponentsCardinality(){
		return this.componentsCardinality.toString();
	}

}
