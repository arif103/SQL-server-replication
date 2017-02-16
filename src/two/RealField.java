package two;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.ProjectException;
import four.DataType;
import four.IntDataType;
import four.RealDataType;

public class RealField extends Field {

	public RealField(String name, String type) {
		super(name, type,8);
		
	}
	@Override
	public void writeData(String newValueList, RandomAccessFile file) throws ProjectException {

		RealDataType a= new RealDataType(newValueList);
		a.writeData(newValueList, file );
	}

	@Override
	public String readData(RandomAccessFile file) throws ProjectException {

		try {
			String real=""+file.readDouble();
			return real;
		} catch (IOException e) {
			throw new ProjectException();
		}
	
		
	}
	@Override
	public DataType newData(String value) throws ProjectException{
		return new RealDataType(value);
	}
	@Override
	public DataType readDataType(RandomAccessFile file) throws ProjectException{
		return new RealDataType(file);
	}
	
}
