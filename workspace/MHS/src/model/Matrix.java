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
		this.deletedColumns = new Vector<>();
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
	 * 
	 * @param index index of the column
	 * @return the column selected
	 */
	public Vector<Byte> getColumn(int index)
	{
		Vector <Byte> column = new Vector<>();
		for(int i = 0; i < matrix.size(); i++)
		{
			column.add(matrix.get(i).get(index));
		}
		return column;
	}
	
	public int getcM()
	{
		return this.matrix.get(0).size();
	}
	
	public int getcN()
	{
		return this.matrix.size();
	}
	
	/**
	 * Delete empty columns
	 */
	public void pruneMatrix(){
		
		for(int j = this.getcM() - 1; j >= 0; j--)
		{
			int i = 0;
			for(i = 0; i < this.getcN(); i++)
			{
				if(this.matrix.get(i).get(j) == (byte) 1)
					break;
			}
			if(i == this.getcN())
			{
				for(int k= 0; k < this.getcN(); k++)
				{
					matrix.get(k).remove(j);
				}
				this.deletedColumns.add(j);
			}
		}
	}
	
	/**
	 * Reconstruct original matrix
	 */
	public void reconstructMatrix(){
		for(int j = this.deletedColumns.size() -1 ; j >=0; j--){
			for(int i = 0; i < this.getcN(); i++)
			{
				matrix.get(i).add(this.deletedColumns.get(j), (byte) 0);
			}
		}
	}
	
	public Vector<Integer> getDeletedColumns(){
		return this.deletedColumns;
	}
	
	@Override
	public String toString()
	{
		StringBuilder string = new StringBuilder();
		for(int i = 0; i < matrix.size(); i++)
		{
			Vector<Byte> row = this.matrix.elementAt(i);
			for(int j = 0; j < row.size() ; j++)
			{
				string.append(row.get(j) + " ");
			}
			string.append("\n");
		}
		return string.toString();
	}

}
