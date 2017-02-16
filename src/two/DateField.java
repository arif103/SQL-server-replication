package two;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import one.ProjectException;
import four.DataType;
import four.DateDataType;
import four.IntDataType;

public class DateField extends Field {
	public DateField(String name, String type) {
		super(name, type,8);
	}
	@Override
	public void writeData(String newValueList, RandomAccessFile file) throws ProjectException {
		DateDataType a= new DateDataType(newValueList);
		a.writeData(newValueList, file );
	}

	@Override
	public String readData(RandomAccessFile file) throws ProjectException {

		try {
			
			Date d=new Date(file.readLong());
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
	        String dateText = df2.format(d);
			String date=""+dateText;
			return date;
		} catch (IOException e) {
			throw new ProjectException();
		}
		
	}
	
	@Override
	public DataType newData(String value) throws ProjectException{
		return new DateDataType(value);
	}
	@Override
	public DataType readDataType(RandomAccessFile file) throws ProjectException{
		return new DateDataType(file);
	}
}
