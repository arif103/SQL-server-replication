package four;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.ProjectException;

public class RealDataType extends DataType<RealDataType> {
private double data;
	public RealDataType(String value) throws ProjectException {
	value=value.trim();
		try{
			this.data=Double.parseDouble(value);
			}catch(NumberFormatException e)
			{
				throw new ProjectException(value+" is not a double");
			}
	
	}

	public RealDataType(RandomAccessFile file) throws ProjectException {
		try {
			data=file.readDouble();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProjectException();
		}
	}

	@Override
public void writeData(String newValueList, RandomAccessFile file) throws ProjectException {	
		
		RealDataType a=new RealDataType(newValueList);
		
		try {
			file.seek(file.length());
			file.writeDouble(a.data);
		} catch (IOException e) {
			throw new ProjectException();
		}
	}
	/**
	 * @param args
	 */

	@Override
	public int compareTo(RealDataType o) {
		// TODO Auto-generated method stub
		if(this.data-o.data>0.0)
		{
			return 1;
		}
		else if(this.data-o.data<0.0)
		{
			return -1;
		}
		else return 0;
	}
	@Override
	public void rewriteData(String value1, RandomAccessFile file) throws ProjectException {
		RealDataType a=new RealDataType(value1);
		try {
			file.writeDouble(a.data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProjectException("wrong boolean");
		}
		
	}
	

}
