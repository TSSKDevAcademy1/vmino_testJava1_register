package register;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileRegisterLoader implements RegisterLoader {

	private static final String REGISTER_FILE = "register.bin";

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.RegisterLoader#save(register.Register)
	 */
	@Override
	public void save(Register register) {
		try (ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(REGISTER_FILE));) {
			s.writeObject(register);
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IOException e) {
			System.err.println("IO Exception");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.RegisterLoader#load()
	 */
	@Override
	public Register load() {
		File file = new File(REGISTER_FILE);
		Register register;
		if (file.exists()) {
			try (ObjectInputStream s = new ObjectInputStream(new FileInputStream(file));) {
				register = (Register) s.readObject();
				return register;
			} catch (FileNotFoundException e) {
				System.err.println("File not found");
				return null;
			} catch (IOException e) {
				System.err.println("IO Exception");
				return null;
			} catch (ClassNotFoundException e) {
				System.err.println("Class not found exception");
				return null;
			}
		} else {
			return null;
		}
	}
}
