package one.commands;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.ProjectException;
import two.Table;
import two.TableCollection;
/**
 * 
 * This prints out the tables from the table collection.
 *
 */
public class PrintCommand implements ICommand {
	private Pattern pattern = Pattern.compile("\\s*print\\s+(.*);",
			Pattern.CASE_INSENSITIVE);
	private String tableName;

	/* this method is responsible for matching */
	/*
	 * (non-Javadoc)
	 * 
	 * @see one.commands.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if (matcher.matches()) {
			tableName = matcher.group(1);

			return true;
		}
		return false;
	}

	/* this method is responsible for execution of the command */
	/*
	 * (non-Javadoc)
	 * 
	 * @see one.commands.ICommand#execute()
	 */
	@Override
	public void execute() throws ProjectException {

		TableCollection hm = TableCollection.get();
		if (tableName.trim().equalsIgnoreCase("dictionary")) {

			System.out.println(hm.toString());
		}
		else{
		System.out.println(hm.toDataString(tableName));
		}
	}

}