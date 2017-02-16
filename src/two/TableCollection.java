package two;

import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Pattern;

import one.ProjectException;

/**
 * 
 * 
 * Class responsible for making and manipulating table collection
 */
public class TableCollection {

	private static TableCollection tc;
	private HashMap<String, Table> map;

	/**
	 * This makes the Table Collection
	 */
	private TableCollection() {

		map = new HashMap();
	}

	/**
	 * returns the contents of the table collection
	 */
	@Override
	public String toString() {
		String result = "";
		for (Table table : map.values()) {
			result += table.toString();
		}
		return result;
	}

	/**
	 * 
	 * @return tableCollection
	 */
	public static TableCollection get() {
		if (tc == null) {
			tc = new TableCollection();
		}
		return tc;
	}

	/**
	 * This puts the table in the table collection
	 * 
	 * @param tableName
	 * @param table
	 * @throws ProjectException
	 */
	public void add(String tableName, Table table) throws ProjectException {
		tableName = tableName.toUpperCase();
		if (!map.containsKey(tableName)) {

			map.put(tableName, table);
		} else {
			throw new ProjectException("Table Name Exists");
		}
	}

	/**
	 * this renames the table
	 * 
	 * @param tableName1
	 * @param tableName2
	 * @throws ProjectException
	 */
	public void rename(String tableName1, String tableName2)
			throws ProjectException {
		tableName1 = tableName1.toUpperCase();
		tableName2 = tableName2.toUpperCase();
		if (map.containsKey(tableName1) && !map.containsKey(tableName2)
				&& !tableName1.equalsIgnoreCase(tableName2)) {
			Table newTable = map.remove(tableName1);
			newTable.setName(tableName2);
			map.put(tableName2, newTable);
		} else {
			throw new ProjectException("Incorrect rename command");
		}
	}

	/**
	 * drops the table from the table collection
	 * 
	 * @param tableName
	 * @throws ProjectException
	 */
	public void drop(String tableName) throws ProjectException {
		tableName = tableName.toUpperCase();
		if (map.containsKey(tableName.toUpperCase())) {
			map.get(tableName.toUpperCase()).drop();
			map.remove(tableName);

		} else {
			throw new ProjectException("Incorrect drop command");
		}
	}

	/**
	 * Responsible for writing the XML string
	 * 
	 * @return
	 */
	public String toXMLString() {
		String result = "";
		result = "<TableCollection>\n";
		for (Table table : map.values()) {
			result += table.toXMLString();
		}
		result = result + "\n</TableCollection>";
		return result;
	}

	public void insert(String valueList, String tableName)
			throws ProjectException {
		tableName = tableName.toUpperCase();
		if (map.get(tableName) == null) {
			throw new ProjectException("table name does not exist");
		} else {
			map.get(tableName).insert(valueList);
		}

	}

	public String toDataString(String tableName) throws ProjectException {
		tableName = tableName.toUpperCase();
		if (map.get(tableName) == null) {
			throw new ProjectException("table name does not exist");
		} else {
			String result = "";
			result += map.get(tableName).toDataString();

			return result;
		}

	}

	public String select(String tableName, String whereClause)
			throws ProjectException {
		tableName = tableName.toUpperCase();
		if (map.get(tableName) == null) {
			throw new ProjectException("table name does not exist");
		} else {

			String result = "";
			result += map.get(tableName).toSelectString(whereClause);

			return result;
		}

	}

	public void delete(String tableName, String booleanExpression)
			throws ProjectException {
		tableName = tableName.toUpperCase();
		if (map.get(tableName) == null) {
			throw new ProjectException("table name does not exist");
		} else {
			if (booleanExpression == null) {
				
				map.get(tableName).delete();
				
				
			} else {
				map.get(tableName).delete(booleanExpression);

			}

		}

	}

	public void update(String tableName, String fieldName, String value, String booleanExpression) throws ProjectException {
		tableName = tableName.toUpperCase();
		if (map.get(tableName) == null) {
			throw new ProjectException("table name does not exist");
		} else {
			if (booleanExpression == null) {
				
				map.get(tableName).update(fieldName,value);
				
				
			} else {
				map.get(tableName).update(fieldName,value,booleanExpression);

			}

		}
		
	}

}
