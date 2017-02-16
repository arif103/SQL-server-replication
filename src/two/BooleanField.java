package two;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.ProjectException;
import four.BooleanDataType;
import four.DataType;
import four.IntDataType;

public class BooleanField extends Field {
	boolean data;

	public BooleanField(String name, String type) {
		super(name, type,1);
	
	}

	@Override
	public void writeData(String newValueList, RandomAccessFile file) throws ProjectException {

		BooleanDataType a= new BooleanDataType(newValueList);
		a.writeData(newValueList, file );
	}


	@Override
	public String readData(RandomAccessFile file) throws ProjectException {
	
		try {
			String boole=""+file.readBoolean();
			return boole;
		} catch (IOException e) {
	
			throw new ProjectException();
		}
	
		
	}

	@Override
	public DataType newData(String value) throws ProjectException{
		return new BooleanDataType(value);
	}
	@Override
	public DataType readDataType(RandomAccessFile file) throws ProjectException{
		return new BooleanDataType(file);
	}


	
	/**
	 * @param args
	 */

}
