package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import one.ProjectException;
import two.TableCollection;


/**
 * 
 * This is responsible for dropping a table from the table collection.
 *
 */

public class DropTableCommand implements ICommand {
	private Pattern pattern = Pattern.compile("\\s*drop\\s+table\\s+(.*);",
			Pattern.CASE_INSENSITIVE);
	private String tableName;
	/* this method is responsible for matching */
	/* (non-Javadoc)
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
	/* (non-Javadoc)
	 * @see one.commands.ICommand#execute()
	 */
	@Override
	public void execute() throws ProjectException {
		TableCollection hm=TableCollection.get();
		hm.drop(tableName);
	}

}