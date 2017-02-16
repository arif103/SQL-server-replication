package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.ProjectException;
import two.TableCollection;


public class UpdateCommand implements ICommand {
	private Pattern pattern = Pattern.compile(
			"\\s*update\\s+(\\S+)\\s+set\\s+(\\S+)\\s*=\\s*(\\S+)(?:\\s+where\\s(.*))?\\s*;",
			Pattern.CASE_INSENSITIVE);
	private String tableName;
	private String fieldName;
	private String value;
	private String booleanExpression;
	/* this method is responsible for matching */
	/* (non-Javadoc)
	 * @see one.commands.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if (matcher.matches()) {
			tableName = matcher.group(1);
			fieldName = matcher.group(2);
			value = matcher.group(3);
			booleanExpression = matcher.group(4);
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
		System.out.println("This is a correct update command.");
		TableCollection hm=TableCollection.get();
		hm.update(tableName,fieldName,value,booleanExpression);
	}

}