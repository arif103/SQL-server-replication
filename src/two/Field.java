package two;

import java.io.RandomAccessFile;

import one.ProjectException;
import four.DataType;

/**
 * 
 * 
 * 
 *	this class contains the name and data type
 */
public abstract class Field {

	/**
	 * @param args
	 * 
	 */
	protected String name;
	protected String type;
	protected int byteSize ;
	
	/** Field Constructor
	 * 
	 * @param name
	 * @param type
	 */
	public Field(String name, String type, int byteSize)
	{
		this.name=name;
		this.type=type;
		this.byteSize=byteSize;
	}
	/**
	 * Field constructor with two inputs
	 * @param name2
	 * @param type2
	 */

	public Field(String name2, String type2) {
		
		this.name=name;
		this.type=type;
		
	}

	/** Method for returning the result in String
	 * @return String
	 */
	@Override
	public String toString()
	{
		return name+", "+type;
		
	}
	/**
	 * This returns the name of the field.
	 * @return name
	 */
	public String toDataString()
	{
		return name;
	}
	
	/**
	 * 
	 * @return XML string
	 */
	public String toXMLString()
	{
		return "<Name>"+name+"</Name>\n"+"<Type>"+type+"</Type>\n";
		
	}
	
	/**
	 * This returns the byte size of a row.
	 * @return byteSize
	 */
	public int getByteSize(){
		return this.byteSize;
	}
	/** Get method 
	 * 
	 * @return string name of the field
	 */
	public String getName()
	{
		return this.name;
	}
	/**Get type method
	 * 
	 * @return type of String
	 */
	public String getType()
	{
		return this.type;
	}
	/**
	 * This writes data into the binary file
	 * @param newValueList
	 * @param file
	 * @throws ProjectException
	 */
	public abstract void writeData(String newValueList, RandomAccessFile file) throws ProjectException ;
	
	/**
	 * This reads data from binary file.
	 * @param file
	 * @return
	 * @throws ProjectException
	 */
	public abstract String readData(RandomAccessFile file) throws ProjectException;
	//public abstract String readSelectData(RandomAccessFile file, String value);
	public abstract DataType newData(String value) throws ProjectException;
	public DataType readDataType(RandomAccessFile file) throws ProjectException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	

}
