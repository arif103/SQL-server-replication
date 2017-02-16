package two;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import four.BooleanDataType;
import four.DataType;
import one.ProjectException;

/**
 * 
 * This makes the table using the fields
 */
public class Table {

	private ArrayList<Field> allField = new ArrayList<Field>();
	private String tableName;
	private RandomAccessFile file;
	private int rowByteSize;
	private RandomAccessFile varchar;
	private int position;
	private int bytePosition;
	private int setPosition;
	private int setBytePosition;

	public RandomAccessFile getFile() {
		return this.file;
	}

	/**
	 * constructor responsible for making the table
	 * 
	 * @param tableName
	 *            , field
	 * @throws ProjectException
	 */
	public Table(String tableName, String field) throws ProjectException {

		this(tableName);
		int size;
		Pattern pat = Pattern.compile(
				"\\s*+(char)+\\s*+\\(+\\s*+(\\d+)\\s*+\\)\\s*",
				Pattern.CASE_INSENSITIVE);

		try {
			for (String nameType : field.split(",")) {

				String[] nameOrType = nameType.trim().split("\\s+", 2);

				Matcher matcher = pat.matcher(nameOrType[1].trim());

				if (matcher.matches()) {
					size = Integer.parseInt(matcher.group(2));
					if (!fieldChecker(nameOrType[0])) {
						allField.add(new CharField(nameOrType[0], "char", size));
					} else {
						throw new ProjectException("Field already exists");
					}
				} else {
					if (nameOrType[1].equalsIgnoreCase("integer")) {
						if (!fieldChecker(nameOrType[0])) {
							allField.add(new IntField(nameOrType[0],
									nameOrType[1]));
						} else {
							throw new ProjectException("Field already exists");
						}
					} else if (nameOrType[1].equalsIgnoreCase("varchar")) {
						if (!fieldChecker(nameOrType[0])) {
							allField.add(new VarcharField(nameOrType[0],
									nameOrType[1]));
						} else {
							throw new ProjectException("Field already exists");
						}
					} else if (nameOrType[1].equalsIgnoreCase("date")) {
						if (!fieldChecker(nameOrType[0])) {
							allField.add(new DateField(nameOrType[0],
									nameOrType[1]));
						} else {
							throw new ProjectException("Field already exists");
						}
					} else if (nameOrType[1].equalsIgnoreCase("real")) {
						if (!fieldChecker(nameOrType[0])) {
							allField.add(new RealField(nameOrType[0],
									nameOrType[1]));
						} else {
							throw new ProjectException("Field already exists");
						}
					} else if (nameOrType[1].equalsIgnoreCase("boolean")) {
						if (!fieldChecker(nameOrType[0])) {
							allField.add(new BooleanField(nameOrType[0],
									nameOrType[1]));
						} else {
							throw new ProjectException("Field already exists");
						}
					}

					else {
						throw new ProjectException();
					}
				}

			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Wrong Command");
		}

	}

	/**
	 * constructor that only gives the tableName to the table
	 * 
	 * @param tableName
	 * @throws ProjectException
	 */
	public Table(String tableName) throws ProjectException {
		this.tableName = tableName;
		File dir = new File("directory");
		dir.mkdir();
		rowByteSize = this.getByteSize();
		try {
			file = new RandomAccessFile(new File("directory", tableName), "rw");
			varchar = new RandomAccessFile(new File("directory", "varchar"),
					"rw");

		} catch (FileNotFoundException e) {
			throw new ProjectException();
		}

	}

	public int getByteSize() {
		if (rowByteSize == 0) {
			for (Field f : allField) {
				rowByteSize += f.getByteSize();

			}

		}
		return rowByteSize;
	}

	public int getPosition() {
		this.bytePosition=0;
		//if (bytePosition == 0) {
			for (int i = 0; i < position; i++) {
				bytePosition += allField.get(i).getByteSize();
			}
		//}
		return bytePosition;
	}
	public int getsetPosition() {
		this.setBytePosition=0;
		
			for (int i = 0; i < setPosition; i++) {
				setBytePosition += allField.get(i).getByteSize();
			}
	
		return setBytePosition;
	}

	/**
	 * Returns the table String
	 */
	@Override
	public String toString() {
		String table = "";
		table = tableName + "\n";
		for (Field field : allField) {
			table += field.toString() + "\n";
		}

		return table;

	}

	public String toDataString() throws ProjectException {
		int count = 0;
		String result = "";
		if (rowByteSize == 0) {
			rowByteSize = this.getByteSize() + 1;
		}
		result = tableName + "\n";
		for (Field field : allField) {
			result += field.toDataString() + "\t";
		}
		result += "\n";
		try {

			while (count < file.length() / rowByteSize) {
				file.seek((count * rowByteSize));
				boolean val=file.readBoolean();
				if(val){
				for (int i = 0; i < allField.size(); i++) {
					result += allField.get(i).readData(file) + "\t";
				}
				result += "\n";
				}
				
				count++;
			}
		} catch (IOException e) {

			throw new ProjectException();
		}
		return result;
	}

	/**
	 * responsible for writing the xml string
	 * 
	 * @return table
	 */
	public String toXMLString() {
		String table = "";
		table = "<Table Tablename= " + "\"" + tableName + "\"" + ">\n" + table;
		for (Field field : allField) {
			table = table + "<Field>\n";
			table += field.toXMLString() + "\n";
			table += "</Field>\n";
		}
		table += "</Table>\n";
		return table;

	}

	/**
	 * checks if field already exists
	 * 
	 * @param newField
	 * @return
	 */
	public boolean fieldChecker(String newField) {
		for (Field field : allField) {

			if (field.getName().trim().equalsIgnoreCase(newField)) {
				return true;
			}

		}
		return false;

	}

	/**
	 * adds the field to the table
	 * 
	 * @param field
	 */
	public void add(Field field) {

		allField.add(field);
	}

	/**
	 * sets the table name
	 * 
	 * @param tableName
	 */
	public void setName(String tableName) {

		this.tableName = tableName;
	}

	public void insert(String valueList) throws ProjectException {
		long length;
		try {
			length = file.length();
		} catch (IOException e2) {
			throw new ProjectException();
		}
		try {
			file.seek(file.length());

		} catch (IOException e) {

			throw new ProjectException();
		}
		String[] newValueList = valueList.split(",");
		BooleanDataType first = new BooleanDataType("true");
		first.writeData("true", file);
		try {
			for (int i = 0; i < allField.size(); i++) {
				allField.get(i).writeData(newValueList[i], file);

			}
		} catch (ProjectException e) {
			try {
				file.setLength(length);
				throw new ProjectException(" Wrong Insert");
			} catch (IOException e1) {
				throw new ProjectException();
			}
			catch(ArrayIndexOutOfBoundsException e1)
			{
				throw new ProjectException();
			}
		}
		

	}

	public void print(String tableName2) throws ProjectException {

		for (int i = 0; i < allField.size(); i++) {
			allField.get(i).readData(file);

		}

	}

	public void drop() throws ProjectException {
		try {
			file.close();
			varchar.close();
			File f = new File("directory", tableName);
			f.delete();

		} catch (IOException e) {
			throw new ProjectException();
		}

	}

	public String toSelectString(String whereClause) throws ProjectException {
		Pattern pattern = Pattern.compile("(.*?)(>=|<=|>|<|!=|=)(.*)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(whereClause.trim());
		String fieldName;
		String relationalOperator;
		String value;
		if (matcher.matches()) {
			fieldName = matcher.group(1).trim();
			relationalOperator = matcher.group(2).trim();
			value = matcher.group(3).trim();
			int count = 0;
			String result = "";
			if (rowByteSize == 0) {
				rowByteSize = this.getByteSize() + 1;
			}
			
			result = tableName + "\n";

			for (Field field : allField) {
				result += field.toDataString() + "\t";
			}

			Field whereField = null;
			for (int i = 0; i < allField.size(); i++) {
				if (allField.get(i).name.equalsIgnoreCase(fieldName)) {
					position = i;

					whereField = allField.get(i);
					break;
				}
				
				
			}
			if (whereField == null) {
				throw new ProjectException();
			}

			else {
				DataType valueChecker = whereField.newData(value);
			
				this.bytePosition=getPosition();
				
				
				result += "\n";
				try {

					while (count < file.length() / rowByteSize) {
						// file.seek(count * rowByteSize);
						file.seek(((count * rowByteSize)) + getPosition()+1);
					
						DataType value1 = whereField.readDataType(file);
						switch (relationalOperator) {
						case ">=":
							if (value1.compareTo(valueChecker) >= 0) {
								file.seek(count * rowByteSize);
								if(file.readBoolean()){
								for (int i = 0; i < allField.size(); i++) {
									result += allField.get(i).readData(file)
											+ "\t";
								}
								result += "\n";
								}
							}
							break;
						case "<=":
							if (value1.compareTo(valueChecker) <= 0) {
								file.seek(count * rowByteSize);
								if(file.readBoolean()){
								for (int i = 0; i < allField.size(); i++) {
									result += allField.get(i).readData(file)
											+ "\t";
								}
								result += "\n";
								}
							}
							break;
						case ">":
							if (value1.compareTo(valueChecker) > 0) {
								file.seek(count * rowByteSize);
								if(file.readBoolean()){
								for (int i = 0; i < allField.size(); i++) {
									result += allField.get(i).readData(file)
											+ "\t";
								}
								result += "\n";
								}
							}
							break;
						case "<":
							if (value1.compareTo(valueChecker) < 0) {
								file.seek(count * rowByteSize);
								if(file.readBoolean()){
								for (int i = 0; i < allField.size(); i++) {
									result += allField.get(i).readData(file)
											+ "\t";
								}
								result += "\n";
								}
							}
							break;
						case "!=":
							if (value1.compareTo(valueChecker) != 0) {
								file.seek(count * rowByteSize);
								if(file.readBoolean()){
								for (int i = 0; i < allField.size(); i++) {
									result += allField.get(i).readData(file)
											+ "\t";
								}
								result += "\n";
								}
							}
							break;
						case "=":
							if (value1.compareTo(valueChecker) == 0) {
								file.seek(count * rowByteSize);
								if(file.readBoolean()){
								for (int i = 0; i < allField.size(); i++) {
									result += allField.get(i).readData(file)
											+ "\t";
								}
								result += "\n";
								}
							}
							break;
						}

						count++;
					}

				} catch (IOException e) {

					throw new ProjectException();
				}
				return result;
			}
		} else {
			throw new ProjectException("Invalid Where clause");
		}

	}

	public void delete() throws ProjectException {
		int count=0;
		rowByteSize=this.getByteSize();

		try{
		while (count < file.length() / rowByteSize) {
			file.seek((count * rowByteSize));
			file.writeBoolean(false);
			count++;
		}
	} catch (IOException e) {

		throw new ProjectException();
	}	
	}
	

	public void delete(String booleanExpression) throws ProjectException
	{
		Pattern pattern = Pattern.compile("(.*?)(>=|<=|>|<|!=|=)(.*)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(booleanExpression.trim());
		String fieldName;
		String relationalOperator;
		String value;
		if (matcher.matches()) {
			fieldName = matcher.group(1).trim();
			relationalOperator = matcher.group(2).trim();
			value = matcher.group(3).trim();
			int count = 0;
			if (rowByteSize == 0) {
				rowByteSize = this.getByteSize() + 1;
			}
			Field whereField = null;
			for (int i = 0; i < allField.size(); i++) {
				if (allField.get(i).name.equalsIgnoreCase(fieldName)) {
					position = i;

					whereField = allField.get(i);
					break;
				}
				
				
			}
			if (whereField == null) {
				throw new ProjectException();
			}

			else {
				DataType valueChecker = whereField.newData(value);
			
				this.bytePosition=getPosition();
				
				
				
				try {

					while (count < file.length() / rowByteSize) {
						// file.seek(count * rowByteSize);
						file.seek(((count * rowByteSize)) + getPosition()+1);
					
						DataType value1 = whereField.readDataType(file);
						switch (relationalOperator) {
						case ">=":
							if (value1.compareTo(valueChecker) >= 0) {
								file.seek((count * rowByteSize));
								file.writeBoolean(false);
								
							}
							break;
						case "<=":
							if (value1.compareTo(valueChecker) <= 0) {
								file.seek((count * rowByteSize));
								file.writeBoolean(false);
							}
							break;
						case ">":
							if (value1.compareTo(valueChecker) > 0) {
								file.seek((count * rowByteSize));
								file.writeBoolean(false);

							}
							break;
						case "<":
							if (value1.compareTo(valueChecker) < 0) {
								file.seek((count * rowByteSize));
								file.writeBoolean(false);
							}
							break;
						case "!=":
							if (value1.compareTo(valueChecker) != 0) {
								file.seek((count * rowByteSize));
								file.writeBoolean(false);
								
							break;
							}
						case "=":
							if (value1.compareTo(valueChecker) == 0) {
								file.seek((count * rowByteSize));
								file.writeBoolean(false);;
							}
							break;
						}

						count++;
					}

				} catch (IOException e) {

					throw new ProjectException();
				}
	}
			
			}
	


	
}

	public void update(String fieldName, String value) throws ProjectException {
		int count=0;
		rowByteSize=this.getByteSize();
		String fieldName1=fieldName.trim();
		String value1=value.trim();
		Field whereField=null;
		for(int i=0;i<allField.size();i++)
		{
			if(allField.get(i).name.equalsIgnoreCase(fieldName1))
			{
				setPosition=i;
				whereField=allField.get(i);
				
			}
		}
		if (whereField == null) {
			throw new ProjectException();
		}

		else {
			DataType valueChecker = whereField.newData(value1);
		
		
		try{
		while (count < file.length() / rowByteSize) {
			file.seek((count * rowByteSize));
			if(file.readBoolean())
			{
				file.seek((count*rowByteSize)+getsetPosition()+1);
				valueChecker.rewriteData(value1, file);
				
			}
			count++;
		}
	} catch (IOException e) {

		throw new ProjectException();
	}	
		}
	}

	public void update(String setFieldName,String setValue,String booleanExpression) throws ProjectException {
		
		Pattern pattern = Pattern.compile("(.*?)(>=|<=|>|<|!=|=)(.*)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(booleanExpression.trim());
		String fieldName;
		String relationalOperator;
		String value;
		String setFieldName1=setFieldName.trim();
		String setValue1=setValue;
		if (matcher.matches()) {
			fieldName = matcher.group(1).trim();
			relationalOperator = matcher.group(2).trim();
			value = matcher.group(3).trim();
			int count = 0;
			
			if (rowByteSize == 0) {
				rowByteSize = this.getByteSize() + 1;
			}
			
			
			

			Field whereField = null;
			Field setField=null;
			for (int i = 0; i < allField.size(); i++) {
				if (allField.get(i).name.equalsIgnoreCase(fieldName)) {
					position = i;

					whereField = allField.get(i);
					break;
				}
			}
				for (int j = 0; j < allField.size(); j++) {
					if (allField.get(j).name.equalsIgnoreCase(setFieldName1)) {
						setPosition = j;

						setField = allField.get(j);
						break;
					}
				
			}
			if (whereField == null ||setField==null) {
				throw new ProjectException();
			}

		else {
			DataType valueChecker = whereField.newData(value);
			DataType setValueChecker=setField.newData(setValue1);
			this.setBytePosition=getsetPosition()+1;
		
		try{
		while (count < file.length() / rowByteSize) {
			file.seek(((count * rowByteSize)) + getPosition()+1);
			
			DataType value1 = whereField.readDataType(file);
			switch (relationalOperator) {
			case ">=":
				if (value1.compareTo(valueChecker) >= 0) {
					file.seek(count * rowByteSize);
					if(file.readBoolean()){
						file.seek((count*rowByteSize)+setBytePosition);
						valueChecker.rewriteData(setValue1, file);
				
					}
				}
				break;
			case "<=":
				if (value1.compareTo(valueChecker) <= 0) {
					file.seek(count * rowByteSize);
					if(file.readBoolean()){
						file.seek((count*rowByteSize)+setBytePosition);
						valueChecker.rewriteData(setValue1, file);
					}
				}
				break;
			case ">":
				if (value1.compareTo(valueChecker) > 0) {
					file.seek(count * rowByteSize);
					if(file.readBoolean()){
						file.seek((count*rowByteSize)+setBytePosition);
						valueChecker.rewriteData(setValue1, file);
					}
				}
				break;
			case "<":
				if (value1.compareTo(valueChecker) < 0) {
					file.seek(count * rowByteSize);
					if(file.readBoolean()){
						file.seek((count*rowByteSize)+setBytePosition);
						valueChecker.rewriteData(setValue1, file);
					}
				}
				break;
			case "!=":
				if (value1.compareTo(valueChecker) != 0) {
					file.seek(count * rowByteSize);
					if(file.readBoolean()){
						file.seek((count*rowByteSize)+setBytePosition);
						valueChecker.rewriteData(setValue1, file);
					}
				}
				break;
			case "=":
				if (value1.compareTo(valueChecker) == 0) {
					file.seek(count * rowByteSize);
					if(file.readBoolean()){
						file.seek((count*rowByteSize)+setBytePosition);
						valueChecker.rewriteData(setValue1, file);
					}
				}
				break;
			}
			count++;
		}
	} catch (IOException e) {

		throw new ProjectException();
	}	
		}
		
	}
}
	public class Where {
		
		public void whereClause(String whereClause) throws ProjectException
		{
			Pattern pattern = Pattern.compile("(.*?)(>=|<=|>|<|!=|=)(.*)",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(whereClause.trim());
			String fieldName;
			String relationalOperator;
			String value;
			if (matcher.matches()) {
				fieldName = matcher.group(1).trim();
				relationalOperator = matcher.group(2).trim();
				value = matcher.group(3).trim();
				int count = 0;
				if (rowByteSize == 0) {
					rowByteSize =Table.this.getByteSize() + 1;
				}
				
				Field whereField = null;
				for (int i = 0; i < allField.size(); i++) {
					if (allField.get(i).name.equalsIgnoreCase(fieldName)) {
						position = i;

						whereField = allField.get(i);
						break;
					}
					
					
				}
				if (whereField == null) {
					throw new ProjectException();
				}

				else {
					DataType valueChecker = whereField.newData(value);
					Table.this.bytePosition=getPosition();
					
					
				
					try {

						while (count < file.length() / rowByteSize) {
							// file.seek(count * rowByteSize);
							file.seek(((count * rowByteSize)) + getPosition()+1);
						
							DataType value1 = whereField.readDataType(file);
							switch (relationalOperator) {
							case ">=":
								if (value1.compareTo(valueChecker) >= 0) {
									file.seek(count * rowByteSize);
									if(file.readBoolean()){
									
									
									}
								}
								break;
							case "<=":
								if (value1.compareTo(valueChecker) <= 0) {
									file.seek(count * rowByteSize);
									if(file.readBoolean()){
									
								
									}
								}
								break;
							case ">":
								if (value1.compareTo(valueChecker) > 0) {
									file.seek(count * rowByteSize);
									if(file.readBoolean()){
								
								}
								}
								break;
								
							case "<":
								if (value1.compareTo(valueChecker) < 0) {
									file.seek(count * rowByteSize);
									if(file.readBoolean()){
									
								}
								}
								break;
							case "!=":
								if (value1.compareTo(valueChecker) != 0) {
									file.seek(count * rowByteSize);
									if(file.readBoolean()){
									
								}
								}
								break;
							case "=":
								if (value1.compareTo(valueChecker) == 0) {
									file.seek(count * rowByteSize);
									if(file.readBoolean()){
									
								}
								}
								break;
							}

							count++;
						}

					} catch (IOException e) {

						throw new ProjectException();
					}
				
				}
			} else {
				throw new ProjectException("Invalid Where clause");
			}
			
			}

	
	}
}
