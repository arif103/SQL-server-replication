package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.ProjectException;

import two.TableCollection;

/**
 * 
 * This class renames Table name.
 *
 */
public class RenameCommand implements ICommand {
	private Pattern pattern = Pattern.compile(
			"\\s*rename\\s+table\\s+(\\S+)\\s+to\\s+(.*);", Pattern.CASE_INSENSITIVE);
	private String tableName1;
	private String tableName2;
	/* this method is responsible for matching */
	/* (non-Javadoc)
	 * @see one.commands.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if (matcher.matches()) {
			tableName1 = matcher.group(1);
			tableName2 = matcher.group(2);
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
		hm.rename(tableName1,tableName2);
	}

}