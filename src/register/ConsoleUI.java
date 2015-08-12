package register;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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
	private RegisterLoader fileLoader = new FileRegisterLoader();
	private RegisterLoader databaseLoader = new DatabaseRegisterLoader();
	private SaveOption saveDestination;

	/**
	 * Menu options.
	 */
	private enum Option {
		PRINT, ADD, UPDATE, REMOVE, FIND, SAVE, EXIT
	};
	
	private enum SaveOption {
		DATABASE, FILE, NONE
	};

	/**
	 * Constructor of register ConsoleUI class.
	 * 
	 * @param register
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public ConsoleUI() {
		if (databaseLoader.load() == null) {
			if (fileLoader.load() == null) {
				this.register = chooseRegister();
				// addSomePersons
			} else {
				if (confirmLoadFile()) {
					this.register = fileLoader.load();
					System.out.println("Register was loaded from File.");
					saveDestination = SaveOption.FILE;
				} else {
					this.register = chooseRegister();
					saveDestination = SaveOption.NONE;
				}

			}
		} else {
			if (confirmLoadDatabase()) {
				this.register = databaseLoader.load();
				System.out.println("Register was loaded from Database.");
				saveDestination = SaveOption.DATABASE;
			} else {
				if (fileLoader.load() != null && confirmLoadFile()) {
					this.register = fileLoader.load();
					System.out.println("Register was loaded from File.");
					saveDestination = SaveOption.FILE;
				} else {
					this.register = chooseRegister();
					saveDestination = SaveOption.NONE;
				}
			}
		}
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
			case SAVE:
				chooseSaveDestination();
				break;
			case EXIT:
				save(saveDestination);
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
	
	private void save(SaveOption option){
		if(option == SaveOption.DATABASE){
			saveToDatabase(register);
		} else if (option == SaveOption.FILE){
			saveToFile(register);
		} else {
			return;
		}
	}
	
	private void saveToDatabase(Register register) {
		try {
			databaseLoader.save(register);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Something gone wrong, register has not been saved to Database.");
		}
	}

	private void saveToFile(Register register) {
		try {
			fileLoader.save(register);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Something gone wrong, register has not been saved to File.");
		}
	}

	private boolean confirmLoadDatabase() {
		int index = 0;
		System.out.println("There was found database with register, do you want to load it ?\n1. Yes\n2. No");

		do {
			try {
				index = Integer.parseInt(readLine());
			} catch (NumberFormatException e) {
				System.err.println("Wrong format, please start again.");
			}
			switch (index) {
			case 1:
				this.register = databaseLoader.load();
				return true;
			case 2:
				return false;
			default:
				System.err.println("Wrong index, please choose again.");
			}
		} while (index < 1 && index > 2);
		return false;
	}

	private boolean confirmLoadFile() {
		int index = 0;
		System.out.println("There was found file with register, do you want to load it ?\n1. Yes\n2. No");
		do {
			try {
				index = Integer.parseInt(readLine());
			} catch (NumberFormatException e) {
				System.err.println("Wrong format or index, please start again.");
			}
			switch (index) {
			case 1:
				this.register = databaseLoader.load();
				return true;
			case 2:
				return false;
			}
		} while (index < 1 && index > 2);
		return false;
	}

	private void chooseSaveDestination() {
		int index = 0;
		System.out
				.println("Where do you want to save register ?\n1. Database\n2. File\n3. I dont want to save register");
		do {
			try {
				index = Integer.parseInt(readLine());
			} catch (NumberFormatException e) {
				System.err.println("Wrong format, please start again.");
			}
			switch(index){
			case 1:
				saveDestination = SaveOption.DATABASE;
				return;
			case 2:
				saveDestination = SaveOption.FILE;
				return;
			case 3:
				saveDestination = SaveOption.NONE;
				return;
			}
		} while (index < 1 && index > 3);
	}

	private Register chooseRegister() {
		int index = 0;
		System.out.println(
				"Choose type of register:\n------------------------\n1. ListRegister\n2. ArrayRegister (without sorting)");
		try {
			index = Integer.parseInt(readLine());
		} catch (NumberFormatException e) {
			System.err.println("Wrong format, please start again.");
		}
		switch (index) {
		case 1:
			register = new ListRegister();
			break;
		case 2:
			register = new ArrayRegister(20);
			break;
		default:
			System.err.println("Wrong index, please start again.");
			System.exit(-1);
		}
		return register;
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
		// StringBuilder builder = new StringBuilder();

		int length = register.getCount();
		int nameLength = register.getMaxLengthName();
		int numberLength = register.getMaxLengthNumber();
		out.format("%1$2s. %4$s %2$-" + nameLength + "s %4$s %3$-" + numberLength + "s %n", "No", "Name",
				"Phone Number", "|");
		for (int i = 0; i < nameLength + numberLength + 9; i++) {
			out.format("%s", "-");
		}
		out.format("%n");
		for (int i = 0; i < length; i++) {

			out.format("%1$2s. %4$s %2$-" + nameLength + "s %4$s %3$-" + numberLength + "s %n", i + 1,
					register.getPerson(i).getName(), register.getPerson(i).getPhoneNumber(), "|");
		}
		// out.close();
		// System.out.println(builder);
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

		try {
			index = Integer.parseInt(readLine());
		} catch (NumberFormatException e) { // if input is not number, return to
											// menu
			System.err.println("Wrong format");
		}
		String name;
		String phoneNumber;
		switch (index) {
		case 1:
			System.out.println("Enter new name: ");
			name = readLine();
			if (register.findPersonByBoth(name, person.getPhoneNumber()) != null) {
				System.err.println("Person with this NAME and NUMBER is already registered!");
				return;
			}
			person.setName(name);
			break;
		case 2:
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
			break;

		case 3:
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
			break;

		case 9:
			return;
		default:
			System.err.println("Wrong index, choose only 1,2,3 or 9");
			break;
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
	 * 
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
			System.err.println("Wrong index");
			return 0;
		}
		return index;
	}

	private void addSomePersons() {
		register.addPerson(new Person("Janko Hrasko", "0900123456"));
		register.addPerson(new Person("Marienka", "+421915151885"));
		register.addPerson(new Person("Haluska", "00421948119407"));
		register.addPerson(new Person("Peter Pan", "+421 915 111222"));
		register.addPerson(new Person("Dobra vila", "00 421 915 144 144"));
		register.addPerson(new Person("Super Extra Dlhe Meno Na Otestovanie Sirky Tabulky", "00 421 915 144 144"));
	}

}
