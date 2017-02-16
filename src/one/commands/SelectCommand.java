package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.ProjectException;
import two.TableCollection;


public class SelectCommand implements ICommand {
	private Pattern pattern = Pattern.compile(
			"select\\s+(\\S+)(?:\\s+where\\s(.*))?\\s*;", Pattern.CASE_INSENSITIVE);
	private String tableName;
	private String whereClause=null;
	/* this method is responsible for matching */
	/* (non-Javadoc)
	 * @see one.commands.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if (matcher.matches()) {
			tableName = matcher.group(1);
			whereClause = matcher.group(2);
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
		if(whereClause==null)
		{
			System.out.println(hm.toDataString(tableName));
		}
		else{
		System.out.println(hm.select(tableName,whereClause));
		}
		
	}

}