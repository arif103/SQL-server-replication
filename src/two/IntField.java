package two;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import four.DataType;
import four.IntDataType;
import one.ProjectException;

public class IntField extends Field {

	public IntField(String value, String type) {
		super(value, type,4);
		
		
	}
	
	@Override
	public void writeData(String newValueList, RandomAccessFile file) throws ProjectException {
		IntDataType a= new IntDataType(newValueList);
		a.writeData(newValueList, file );
		
	}
	



	@Override
	public String readData(RandomAccessFile file) throws ProjectException {
		
		
		try {
			
			String integer=""+file.readInt();
			return integer;
		} catch (IOException e) {
			
			throw new ProjectException();
		}
	
		
	}
	
	@Override
	public DataType newData(String value) throws ProjectException{
		return new IntDataType(value);
	}
	@Override
	public DataType readDataType(RandomAccessFile file) throws ProjectException{
		return new IntDataType(file);
	}

/**	@Override
	public String readSelectData(RandomAccessFile file, String value) {

		try {
			if(file.readInt)
			String integer=""+file.readInt();
			return integer;
		} catch (IOException e) {
			
			throw new ProjectException();
		}
	}
	**/
	
	



}
