package one;

import java.io.IOException;
import java.util.Scanner;

import org.xml.sax.SAXException;

import one.commands.*;
import three.SAXCommands;

/**
 * Driver class responsible for running the program and testing all the commands.
 */
public class Driver {
	private ICommand[] commands = new ICommand[] { new SelectCommand(),
			new Exit(), new Read(), new BackupCommand(),
			new DefineIndexCommand(), new DefineTableCommand(),
			new DeleteCommand(), new DropTableCommand(), new InsertCommand(),
			new IntersectCommand(), new JoinCommand(), new MinusCommand(),
			new OrderCommand(), new PrintCommand(), new ProjectCommand(),
			new RenameCommand(), new RestoreCommand(), new UnionCommand(),
			new UpdateCommand()
	

	};

	
	

	
	/**
	 * Read method is initiated from driver.run() method or the read class depending on the boolean prompt
	 * returns Incorrect command if the command does not match
	 */
	public void read(Scanner sc, boolean prompt) {
		OUTSIDE: while (true) {
			if (prompt) {
				System.out.println("Enter command: ");
			}
			String input = sc.nextLine();
			while (!input.contains(";")) {
				input = input + " " + sc.nextLine();
			}

			for (ICommand command : commands) {
				if (command.matches(input)) {
					try {
						command.execute();
						continue OUTSIDE;
					} catch (ProjectException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			System.out.println("Incorrect command");

		}
	}

	/**
	 * The main method that runs the driver
	 * 
	 * 
	 */
	public static void main(String[] args) {
		SAXCommands s1= new SAXCommands();
		try {
			s1.saxReader();
			
		} catch (SAXException e) {
			
			System.out.println("Corrupt file");
			
		} catch (IOException e) {
		
			
		}
		new Driver().read(new Scanner(System.in),true);
	}
}
