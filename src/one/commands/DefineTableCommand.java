package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import one.ProjectException;
import two.Table;
import two.TableCollection;
/**
 * 
 * This class makes a table and adds it to the table collection.
 *
 */
public class DefineTableCommand implements ICommand {
	private Pattern pattern = Pattern
			.compile(
					"\\s*define\\s+table\\s+(\\p{Alpha}+)\\s+having\\s+fields\\s*+\\(\\s*+(.*)\\)\\s*;",
					Pattern.CASE_INSENSITIVE);
	private String tableName;
	private String field;

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
			field = matcher.group(2);
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

		Table table = new Table(tableName, field);
		TableCollection.get().add(tableName, table);

	}

}