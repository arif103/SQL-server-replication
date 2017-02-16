package one.commands;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import one.Driver;
import one.ProjectException;

/**
 * the pattern tries to match with the command and executes if it matches
 */
public class Read implements ICommand {
	private Pattern pattern = Pattern.compile("\\s*read\\s+'(.*)'\\s*;",
			Pattern.CASE_INSENSITIVE);
	private String fileName;
	/**
	 * the matcher returns true if matches and executes the program
	 * returns false if it does not match 
	 */
	@Override
	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if (matcher.matches()) {
			fileName = matcher.group(1);
			return true;
		}
		return false;
	}
/** 
 * this method is responsible for the execution if the pattern matches.
 */
@Override
	public void execute() throws ProjectException {

		try (Scanner sc = new Scanner(new File(fileName))) {

			new Driver().read(sc,false);
		} catch (IOException e) {
			throw new ProjectException("File not found.", e);

		} catch (NoSuchElementException e) {
			return;
		}
	}

}
