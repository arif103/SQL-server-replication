package four;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import one.ProjectException;
import two.Table;

public class IntDataType extends DataType<IntDataType> {

	private int data;
	
	
	public IntDataType(String value) throws ProjectException{
		value=value.trim();
		try{
		this.data=Integer.parseInt(value);
		}catch(NumberFormatException e)
		{
			throw new ProjectException(value+" is not an integer");
		}
		
	}
	public IntDataType(RandomAccessFile file) throws ProjectException
	{
		try {
			data=file.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProjectException();
		}
	}
	
	
	
	public int getData(){
		return this.data;
	}
	
	@Override
	public void writeData(String newValueList, RandomAccessFile file) throws ProjectException {	
		


		
		try {
			file.seek(file.length());

			file.writeInt(data);
		} catch (IOException e) {
			throw new ProjectException();
		}
	}
	
	
	

	
	@Override
	public int compareTo(IntDataType o) {
		// TODO Auto-generated method stub
		return this.data-o.data;
	}
	@Override
	public void rewriteData(String value1, RandomAccessFile file) throws ProjectException {
		IntDataType a=new IntDataType(value1);
		try {
			file.writeInt(a.data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProjectException("wrong integer value");
		}
		
	}

}
