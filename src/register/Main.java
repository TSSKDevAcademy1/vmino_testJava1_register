package register;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

/**
 * Created by Vladislav Mino 7.8.2015
 */
public class Main {

	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		int index = 0;
		Register register = null;
		ConsoleUI ui = null;
		File file = new File("out.bin");
		if (file.exists()) {
			try(FileInputStream in = new FileInputStream("out.bin");
			ObjectInputStream s = new ObjectInputStream(in);){
				register = (Register) s.readObject();
				s.close();
			}
		} else {
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
		}
		ui = new ConsoleUI(register);

//		// can be deleted
//		register.addPerson(new Person("Janko Hrasko", "0900123456"));
//		register.addPerson(new Person("Marienka", "+421915151885"));
//		register.addPerson(new Person("Haluska", "00421948119407"));
//		register.addPerson(new Person("Peter Pan", "+421 915 111222"));
//		register.addPerson(new Person("Dobra vila", "00 421 915 144 144"));
//		register.addPerson(new Person("Super Extra Dlhe Meno Na Otestovanie Sirky Tabulky", "00 421 915 144 144"));
//		// can be deleted

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
