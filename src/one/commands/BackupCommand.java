package one.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class BackupCommand implements ICommand {
	private Pattern pattern = Pattern.compile(
			"\\s*backup\\s+to\\s+'(.*)'\\s*;", Pattern.CASE_INSENSITIVE);
	private String fileLocation;

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
			fileLocation = matcher.group(1);

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
	public void execute() {
		System.out.println("This is a correct backup command.");

	}

}