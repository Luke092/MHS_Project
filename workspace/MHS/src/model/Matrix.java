package model;

import java.util.Vector;

public class Matrix {
	
	private Vector<Vector<Byte>> matrix;
	
	/**
	 * List of deleted columns.
	 * For matrix reconstruction a blank column have to be inserted
	 * before this index 
	 */
	private Vector<Integer> deletedColumns;
	
	private static Matrix instance = null;
	
	/**
	 * Create a new Matrix
	 */
	private Matrix(){
		this.matrix = new Vector<>();
	}
	
	public static Matrix getInstance(){
		if(instance == null){
			instance = new Matrix();
		}
		return instance;
	}
	
	public void addElement(int row, byte value){
		if(row < this.matrix.size()){
			this.matrix.get(row).add(value);
		} else {
			Vector<Byte> tmp = new Vector<>();
			tmp.add(value);
			this.matrix.add(tmp);
		}
	}
	
	/**
	 * Delete empty columns
	 */
	public void pruneMatrix(){
		for(int i = this.matrix.size(); i >= 0; i--){
			Vector<Byte> row = this.matrix.elementAt(i);
			int j = 0;
			for(j = 0; j < row.size(); j++){
				if(row.get(j) == 1){
					break;
				}
			}
			if(j >= row.size()){
				this.deletedColumns.addElement(i);
				this.matrix.remove(i);
			}
		}
	}
	
	/**
	 * Reconstruct original matrix
	 */
	public void reconstructMatrix(){
		for(int i = this.deletedColumns.size(); i >=0; i--){
			Vector<Byte> row = new Vector<>();
			for(int j = 0; j < this.matrix.get(0).size(); j++){
				row.add((byte) 0);
			}
			this.matrix.insertElementAt(row, this.deletedColumns.get(i));
		}
	}

}
