package four;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.ProjectException;

public class BooleanDataType extends DataType<BooleanDataType> {

	boolean data;
	public BooleanDataType(String value) throws ProjectException
	{
		value=value.trim();
		if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))
		{
			data=Boolean.parseBoolean(value);
		}
		else
		{
			throw new ProjectException(value+" is not a boolean ");
		}
	}
	public BooleanDataType(RandomAccessFile file) throws ProjectException {
		try {
			data=file.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProjectException();
		}
	}
	@Override
public void writeData(String newValueList, RandomAccessFile file) throws ProjectException {	
		
		BooleanDataType a=new BooleanDataType(newValueList);
		
		try {
			file.seek(file.length());
			file.writeBoolean(a.data);
		} catch (IOException e) {
			throw new ProjectException();
		}
	}
	@Override
	public int compareTo(BooleanDataType o) {
		// TODO Auto-generated method stub
		if(this.data==o.data)
		{
		return 0;	
		}
		else{
			return 1;
		}
	}
	@Override
	public void rewriteData(String value1, RandomAccessFile file) throws ProjectException {
		BooleanDataType a=new BooleanDataType(value1);
		try {
			file.writeBoolean(a.data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProjectException("wrong boolean");
		}
		
	}

}
