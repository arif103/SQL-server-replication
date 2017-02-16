package four;

import java.util.Date;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.ProjectException;

public class DateDataType extends DataType<DateDataType> {
	private long data;
	private Pattern pattern = Pattern.compile(
			"\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d", Pattern.CASE_INSENSITIVE);

	public DateDataType(String value) throws ProjectException {
		
		value=value.trim();
		value = value.substring(1, value.length()-1);
		value=value.trim();
		Matcher matcher=pattern.matcher(value);
		if (matcher.matches()) {
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			try {
				Date d = f.parse(value);
				data = d.getTime();
			} catch (ParseException e) {
			
				throw new ProjectException("Invalid date");
			}

		}
	}
	public DateDataType(RandomAccessFile file) throws ProjectException {
		try {
			data=file.readLong();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProjectException();
		}
	}
	@Override
public void writeData(String newValueList, RandomAccessFile file) throws ProjectException {	
		
		DateDataType a=new DateDataType(newValueList);
		
		try {
			file.seek(file.length());
			file.writeLong(a.data);
		} catch (IOException e) {
			throw new ProjectException();
		}
	}
	@Override
	public int compareTo(DateDataType o) {
		if(this.data-o.data>0)
		{
			return 1;
		}
		else if(this.data-o.data<0)
		{
			return -1;
		}
		else return 0;
	}
	@Override
	public void rewriteData(String value1, RandomAccessFile file) throws ProjectException {
		DateDataType a=new DateDataType(value1);
		try {
			file.writeLong(a.data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProjectException("wrong date value");
		}
		
	}

}
