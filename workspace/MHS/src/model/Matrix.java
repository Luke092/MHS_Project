package model;

import java.util.Random;
import java.util.Vector;

public class Matrix {
	
	private Vector<Vector<Byte>> matrix;
	
	/**
	 * List of deleted columns.
	 * For matrix reconstruction a blank column have to be inserted
	 * before this index 
	 */
	private Vector<Integer> deletedColumns;
	
	private int cM1;
	
//	private static Matrix instance = null;
	
	/**
	 * Create a new Matrix
	 */
	public Matrix(){
		this.matrix = new Vector<>();
		this.deletedColumns = new Vector<>();
	}
	
//	/**
//	 * Gets the Matrix instance
//	 * @return the pointer to the object
//	 */
//	public static Matrix getInstance(){
//		if(instance == null){
//			instance = new Matrix();
//		}
//		return instance;
//	}
	
	/**
	 * Adds a single element to the matrix
	 * @param row row of the element
	 * @param value value of the element
	 */
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
	 * Gets the column of the matrix
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
	
	/**
	 * Gets the number of columns of the matrix
	 * @return the int number
	 */
	public int getcM()
	{
		return this.matrix.get(0).size();
	}
	
	/**
	 * Gets the number of rows of the matrix
	 * @return the int number
	 */
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
		this.cM1 = this.matrix.get(0).size();
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
	
	/**
	 * Gets the vector containing the indexes of the columns deleted in the pruning phase
	 * @return the vector containing the int indexese
	 */
	public Vector<Integer> getDeletedColumns(){
		return this.deletedColumns;
	}
	
	public void addRow(Vector <Byte> row)
	{
		matrix.add(row);
	}
	
	/**
	 * 
	 * @return dimension of pruned matrix
	 */
	public int getcM1(){
		return this.cM1;
	}
	
	public Matrix [] divideRandomMatrix(int numberMatrices)
	{	
		if(this.matrix.size() >= numberMatrices)
		{
			Matrix [] matrices = new Matrix[numberMatrices];
			for(int i = 0; i < matrices.length; i++)
			{
				matrices[i] = new Matrix();
				matrices[i].addRow(this.matrix.get(i));
			}
			
			Random rand = new Random();
			for(int i = numberMatrices; i < matrix.size(); i++)
			{
				matrices[rand.nextInt(numberMatrices)].addRow(this.matrix.get(i));
			}
			return matrices;
		}
		else
		{
			System.err.println("Division number too big");
			return null;			
		}
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
			string.append("-");
			string.append("\n");
		}
		return string.toString();
	}

}
