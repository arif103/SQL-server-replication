package four;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.ProjectException;

public class CharDataType extends DataType<CharDataType> {
	int size;
	private char[] data;
	private String word;
private Pattern pattern = Pattern.compile(
		"'.*'", Pattern.CASE_INSENSITIVE);
	public CharDataType(String value, int size) throws ProjectException {
		this.size=size;
		this.data=new char[size];
		value=value.trim();
		Matcher matcher=pattern.matcher(value);
		value=value.substring(1, value.length()-1);
		value=value.trim();
		
		if(matcher.matches() && value.length()==size)
		{
			
			for(int i=0; i<size;i++)
			{
			this.data[i]=value.charAt(i);
			word+=""+value.charAt(i);
			}
		}
		else
		{
			throw new ProjectException(value+" is not a character ");
		}
		
	}
	public String getWord()
	{
		return this.word;
	}
	public CharDataType(RandomAccessFile file, int size) throws ProjectException {
	
			try {
				this.data=new char[size];
				for(int i=0;i<size;i++)
				{
				data[i]=file.readChar();
				word+=""+data[i];
				}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProjectException();
		}
	}
	@Override
	public void writeData(String newValueList, RandomAccessFile file) throws ProjectException  {	
		
		CharDataType a=new CharDataType(newValueList,size);
		
		try {
			for(int i=0;i<size;i++)
			{
			file.seek(file.length());
			file.writeChar(data[i]);
			}
		} catch (IOException e) {
			throw new ProjectException();
		}
	}
	@Override
	public int compareTo(CharDataType o) {
		
		return this.getWord().compareTo(o.word);
	}
	@Override
	public void rewriteData(String value1, RandomAccessFile file) throws ProjectException {
		CharDataType a=new CharDataType(value1,size);
		
		try {
			for(int i=0;i<size;i++)
			{
			if(i!=0)
			{
				file.seek(file.getFilePointer()+2);
			}
			file.writeChar(data[i]);
			}
		} catch (IOException e) {
			throw new ProjectException();
		}
	}

}
