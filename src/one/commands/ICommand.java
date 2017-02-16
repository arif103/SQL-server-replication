package one.commands;

import one.ProjectException;

/**
 * The Interface ICommand.
 */
public interface ICommand {
	/**
	 * The input from the Scanner is matched with the regular expressions to find out the right command
	 *  @param String that is to be matched
	 * @return true if the pattern matches
	 */
	boolean matches(String input);
	/**
	 * The command is executed when the matcher returns true
	 */
	void execute() throws ProjectException;
}
