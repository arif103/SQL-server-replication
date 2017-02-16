package two;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.ProjectException;
import four.BooleanDataType;
import four.CharDataType;
import four.DataType;
import four.IntDataType;

/** CharFied subclass of Field that has an extra parameter "size" of type integer
 */
public class CharField extends Field {

	private int size;
	public CharField(String name, String type, int size1) {
		super(name,type,size1*2);
		this.size=size1;
		
	}
	/**
	 * This returns the String of name, type and size
	 */
	@Override
	public String toString()
	{
		return name+", "+type+", "+size;
		
	}
	/**
	 * writes the xml String
	 */
	public String toXMLString()
	{
		return "<Name>"+name+"</Name>\n"+"<Type>"+type+"</Type>\n"+"<Size>"+size+"</Size>\n";
		
	}
	
	
	@Override
	public void writeData(String newValueList, RandomAccessFile file) throws ProjectException {

		CharDataType a= new CharDataType(newValueList,size);
		a.writeData(newValueList, file );
	}
	@Override
	public String readData(RandomAccessFile file) throws ProjectException {

		try {
			String character ="";
			for(int i=0;i<size;i++)
			{
			character=character+""+file.readChar();
			
			}
			return character;
		} catch (IOException e) {
			throw new ProjectException();
		}
		
	}
	@Override
	public DataType newData(String value) throws ProjectException{
		return new CharDataType(value,size);
	}
	@Override
	public DataType readDataType(RandomAccessFile file) throws ProjectException{
		return new CharDataType(file,size);
	}
	
}
