package one.commands;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import two.TableCollection;
/**
 * 
 * Responsible to exit out of the program
 *	
 */

public class Exit implements ICommand {
	private Pattern pattern = Pattern
			.compile("\\s*(exit)\\s*;", Pattern.CASE_INSENSITIVE);
	/* this method is responsible for matching */
	/* (non-Javadoc)
	 * @see one.commands.ICommand#matches(java.lang.String)
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		
		return matcher.matches();
	}
	/* this method is responsible for execution of the command */
	/* (non-Javadoc)
	 * @see one.commands.ICommand#execute()
	 */
	@Override
	public void execute() {
	
		PrintWriter writer;
		try {
			writer = new PrintWriter("data.xml");
			writer.write(TableCollection.get().toXMLString());
			writer.close();
		} catch (FileNotFoundException e) {
			
			System.out.println("File not found");
		}
		System.exit(0);
	}

}
