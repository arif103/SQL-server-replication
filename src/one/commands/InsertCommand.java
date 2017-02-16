package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.ProjectException;

import two.TableCollection;


public class InsertCommand implements ICommand {
	private Pattern pattern = Pattern
			.compile("\\s*insert\\s*+\\((.*)\\)\\s*+into\\s+(\\S+)\\s*;",
					Pattern.CASE_INSENSITIVE);
	private String valueList;
	private String tableName;
	/* this method is responsible for matching */
	/* (non-Javadoc)
	 * @see one.commands.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if (matcher.matches()) {
			valueList = matcher.group(1);
			tableName = matcher.group(2);
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
		hm.insert(valueList, tableName);

	}

}