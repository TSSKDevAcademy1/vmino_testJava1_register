package register;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileRegisterLoader implements RegisterLoader {
		private String filename = "textfile";
	@Override
	public void save(Register register) {
		int count = register.getCount();
		try (BufferedWriter r = new BufferedWriter(new FileWriter(filename))) {
			for (int i = 0; i < count; i++) {
				Person person = register.getPerson(i);
				r.append(person.getName());
				r.newLine();
				r.append(person.getPhoneNumber());
				r.newLine();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public Register load() {
		Register register = new ListRegister();
		try (BufferedReader r = new BufferedReader(new FileReader(filename))) {
			String name;
			while((name = r.readLine()) != null){
				String phoneNumber = r.readLine();
				try {
					register.addPerson(new Person(name, phoneNumber));
				} catch (Exception e) {
					System.out.println("Wrong format, do not touch textfile.file !");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return register;
	}

}
