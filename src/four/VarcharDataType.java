package four;

import java.io.IOException;
import java.io.RandomAccessFile;

import one.ProjectException;

public class VarcharDataType extends DataType<VarcharDataType> {

	private String data;
	private long location;
	private RandomAccessFile file1;
		public VarcharDataType(String value) {
			value=value.trim();
			value=value.substring(1, value.length()-1);
			this.data=value;
			
			
		}
		public VarcharDataType(RandomAccessFile file,RandomAccessFile file1) throws ProjectException {
			try {
				location=file.readLong();
				file1.seek(location);
				data=file1.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new ProjectException();
			}
		}
		@Override
		public void writeData(String newValueList, RandomAccessFile file1) throws ProjectException {	
			this.file1=file1;
			VarcharDataType a=new VarcharDataType(newValueList);
			
			try {
				file1.seek(file1.length());
				file1.writeUTF(a.data);
			} catch (IOException e) {
				throw new ProjectException();
			}
		}
		
		@Override
		public void writeLongData(long newValueList, RandomAccessFile file) throws ProjectException {	
			
			try {
			
				file.seek(file.length());
				file.writeLong(newValueList);
			} catch (IOException e) {
				throw new ProjectException();
			}
		}
		@Override
		public int compareTo(VarcharDataType o) {
			// TODO Auto-generated method stub
			return data.compareTo(o.data);
		}
		@Override
		public void rewriteData(String newValueList, RandomAccessFile file1) throws ProjectException {	
			this.file1=file1;
			VarcharDataType a=new VarcharDataType(newValueList);
			
			try {
				file1.seek(file1.length());
				file1.writeUTF(a.data);
			} catch (IOException e) {
				throw new ProjectException();
			}
		}
		
		
		
	}

