package register;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface RegisterLoader {

	void save(Register register);

	Register load();

}