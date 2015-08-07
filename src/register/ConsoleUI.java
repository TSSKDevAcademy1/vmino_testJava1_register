package register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Formatter;

/**
 * User interface of the application.
 */
public class ConsoleUI {
	/** register.Register of persons. */
	private Register register;

	/**
	 * In JDK 6 use Console class instead.
	 * 
	 * @see readLine()
	 */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Menu options.
	 */
	private enum Option {
		PRINT, ADD, UPDATE, REMOVE, FIND, EXIT
	};

	/**
	 * Constructor of register ConsoleUI class.
	 * 
	 * @param register
	 */
	public ConsoleUI(Register register) {
		this.register = register;
	}

	/**
	 * Prints menu options.
	 */
	public void run() {
		while (true) {
			switch (showMenu()) {
			case PRINT:
				printRegister();
				break;
			case ADD:
				addToRegister();
				break;
			case UPDATE:
				updateRegister();
				break;
			case REMOVE:
				removeFromRegister();
				break;
			case FIND:
				findInRegister();
				break;
			case EXIT:
				return;
			}
		}
	}

	/**
	 * Reads input from console entered by user.
	 * 
	 * @return
	 */
	private String readLine() {
		// In JDK 6.0 and above Console class can be used
		// return System.console().readLine();
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Prints menu options
	 * 
	 * @return
	 */
	private Option showMenu() {
		System.out.println("Menu.");
		for (Option option : Option.values()) {
			System.out.printf("%d. %s%n", option.ordinal() + 1, option);
		}
		System.out.println("-----------------------------------------------");

		int selection = -1;
		do {
			System.out.println("Option: ");
			try {
				selection = Integer.parseInt(readLine());
			} catch (NumberFormatException e) {
				System.err.println("Wrong format");
			}
		} while (selection <= 0 || selection > Option.values().length);

		return Option.values()[selection - 1];
	}

	/**
	 * Prints list of all persons into table
	 */
	private void printRegister() {
		if (register.getCount() < 1) {
			System.err.println("Register does not contain any persons");
			return;
		}
		StringBuilder builder = new StringBuilder();
		Formatter formatter = new Formatter(builder);
		int length = register.getCount();
		int nameLength = register.getMaxLengthName();
		int numberLength = register.getMaxLengthNumber();
		formatter.format("%1$2s. %4$s %2$-" + nameLength + "s %4$s %3$-" + numberLength + "s %n", "No", "Name",
				"Phone Number", "|");
		for (int i = 0; i < nameLength + numberLength + 9; i++) {
			formatter.format("%s", "-");
		}
		formatter.format("%n");
		for (int i = 0; i < length; i++) {
			formatter.format("%1$2s. %4$s %2$-" + nameLength + "s %4$s %3$-" + numberLength + "s %n", i + 1,
					register.getPerson(i).getName(), register.getPerson(i).getPhoneNumber(), "|");
		}
		formatter.close();
		System.out.println(builder);
	}

	/**
	 * Adds person to register with entered name and phone number.
	 */
	private void addToRegister() {
		System.out.println("Enter Name: ");
		String name = readLine();
		System.out.println("Enter Phone Number: ");
		String phoneNumber = readLine();
		if (register.findPersonByBoth(name, phoneNumber) != null) {
			System.err.println("Person with this NAME and NUMBER is already registered!");
			return;
		}
		try {
			register.addPerson(new Person(name, phoneNumber));
		} catch (RuntimeException e) {
			System.err.println("Wrong number format");
		}

	}

	/**
	 * Updates name and/or phone number of person found by index.
	 */
	private void updateRegister() {
		if (register.getCount() < 1) {
			System.err.println("Register does not contain any persons");
			return;
		}
		System.out.println("Enter index: ");
		int index = readIndex();
		if (index == 0) {
			return;
		}
		Person person = register.getPerson(index - 1);
		System.out.println(person.toString());
		System.out.println("What do you want to update?\n1. Name \n2. Phone Number\n3. Name and PhoneNumber\n9. Back");
		index = readIndex();
		String name;
		String phoneNumber;

		if (index == 0) {
			return;
		} else if (index == 1) {
			System.out.println("Enter new name: ");
			name = readLine();
			if (register.findPersonByBoth(name, person.getPhoneNumber()) != null) {
				System.err.println("Person with this NAME and NUMBER is already registered!");
				return;
			}
			person.setName(name);
			
		} else if (index == 2) {
			System.out.println("Enter new phoneNumber: ");
			phoneNumber = readLine();
			if (register.findPersonByBoth(person.getName(), phoneNumber) != null) {
				System.err.println("Person with this NAME and NUMBER is already registered!");
				return;
			}
			try {
				person.setPhoneNumber(phoneNumber);
			} catch (RuntimeException e) {
				System.err.println("Wrong number format");
			}
			
		} else if (index == 3) {
			System.out.println("Enter new name: ");
			name = readLine();
			System.out.println("Enter new phoneNumber: ");
			phoneNumber = readLine();
			if (register.findPersonByBoth(name, phoneNumber) != null) {
				System.err.println("Person with this NAME and NUMBER is already registered!");
				return;
			}
			person.setName(name);
			try {
				person.setPhoneNumber(phoneNumber);
			} catch (RuntimeException e) {
				System.err.println("Wrong number format");
			}
			
		} else if (index == 9) {
			return;
		}

		else {
			System.err.println("Wrong index, choose only 1,2,3 or 9");
		}
		register.updateList();
	}

	/**
	 * Finds person in register by name or phone number. Prints name and phone
	 * number to console.
	 */
	private void findInRegister() {
		if (register.getCount() < 1) {
			System.err.println("Register does not contain any persons");
			return;
		}
		System.out.println("What are you looking for? Enter name or phone number");
		String str = readLine();
		Person person = register.findPersonByName(str);
		if (person != null) {
			System.out.println(person.toString());
		} else {
			person = register.findPersonByPhoneNumber(str);
			if (person != null) {
				System.out.println(person.toString());
			} else {
				System.out.println("Register does not contain this name or phone number");
			}
		}
	}

	/**
	 * Removes person from register by index
	 */
	private void removeFromRegister() {
		if (register.getCount() < 1) {
			System.err.println("Register does not contain any persons");
			return;
		}
		System.out.println("Enter index: ");
		int index = readIndex();
		if (index == 0) {
			return;
		}
		Person person = register.getPerson(index - 1);
		register.removePerson(person);
	}
	/**
	 * Reads input and looks if input is in right form
	 * @return number representation of index
	 */
	private int readIndex() {
		int index = 0;
		try {
			index = Integer.parseInt(readLine());
		} catch (NumberFormatException e) { // if input is not number, return to
											// menu
			System.err.println("Wrong format");
			return 0;
		}
		if (index > register.getCount() || index < 1) {
			if ((index < 4 && index > 0) || index == 9) {
				// if register has less than 3 persons, user must be able
				// to choose index from 1 to 3 or 9 to go back
				return index;
			}
			System.err.println("Wrong index");
			return 0;
		}
		return index;
	}

}
