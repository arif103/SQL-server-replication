package four;

import java.io.RandomAccessFile;

import one.ProjectException;

public abstract class DataType<T> implements Comparable<T>{

	public int getData() {
		return 0;
	}

	public abstract void writeData(String newValueList, RandomAccessFile file)
			throws ProjectException;

	public void writeLongData(long newValueList, RandomAccessFile file)
			throws ProjectException {
		
	}

	public abstract void rewriteData(String value1, RandomAccessFile file) throws ProjectException;

//	public abstract int compareTo(DataType value1);
//
//	public int compareTo(IntDataType o) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	

	
	
}
