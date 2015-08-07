package register;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Vladislav Mino 7.8.2015
 */
public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		System.out.println("Choose type of register:\n------------------------\n1. ListRegister\n2. ArrayRegister (without sorting)");
		int index = 0;
		ListRegister listRegister;
		ArrayRegister arrayRegister;
		ConsoleUI ui = null;
		try {
			index = Integer.parseInt(readLine());
		} catch (NumberFormatException e) {
			System.err.println("Wrong format, please start again.");
		}
		if (index == 1) {
			listRegister = new ListRegister();
			ui = new ConsoleUI(listRegister);
			//can be deleted
			listRegister.addPerson(new Person("Janko Hrasko", "0900123456"));
			listRegister.addPerson(new Person("Marienka", "+421915151885"));
			listRegister.addPerson(new Person("Haluska", "00421948119407"));
			listRegister.addPerson(new Person("Peter Pan", "+421 915 111222"));
			listRegister.addPerson(new Person("Dobra vila", "00 421 915 144 144"));
			listRegister
					.addPerson(new Person("Super Extra Dlhe Meno Na Otestovanie Sirky Tabulky", "00 421 915 144 144"));
			//can be deleted
		} else if (index == 2) {
			arrayRegister = new ArrayRegister(20);
			ui = new ConsoleUI(arrayRegister);
			//can be deleted
			arrayRegister.addPerson(new Person("Janko Hrasko", "0900123456")); // can be deleted
			arrayRegister.addPerson(new Person("Marienka", "+421915151885"));
			arrayRegister.addPerson(new Person("Haluska", "00421948119407"));
			arrayRegister.addPerson(new Person("Peter Pan", "+421 915 111222"));
			arrayRegister.addPerson(new Person("Dobra vila", "00 421 915 144 144")); // can be deleted
			arrayRegister
					.addPerson(new Person("Super Extra Dlhe Meno Na Otestovanie Sirky Tabulky", "00 421 915 144 144"));
			//can be deleted
		} else {
			System.err.println("Wrong index, please start again.");
			System.exit(0);
		}

		ui.run();
	}

	private static String readLine() {
		// In JDK 6.0 and above Console class can be used
		// return System.console().readLine();
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

}
