package register;

import java.io.Serializable;
import java.util.Arrays;

/**
 * register.Person register.
 */
public class ArrayRegister implements Register, Serializable{
	/**
	 * 
	 */

	/** register.Person array. */
	private Person[] persons;

	/** Number of persons in this register. */
	private int count;

	/**
	 * Constructor creates an empty register with maximum size specified.
	 * 
	 * @param size
	 *            maximum size of the register
	 */
	public ArrayRegister(int size) {
		persons = new Person[size];
		count = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.Register#getCount()
	 */
	@Override
	public int getCount() {
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.Register#getSize()
	 */
	@Override
	public int getSize() {
		return persons.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.Register#getPerson(int)
	 */
	@Override
	public Person getPerson(int index) {
		return persons[index];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.Register#addPerson(register.Person)
	 */
	@Override
	public void addPerson(Person person) {
		persons[count] = person;
		count++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.Register#getMaxLengthName()
	 */
	@Override
	public int getMaxLengthName() {
		int maxLength = 0;
		maxLength = persons[0].getName().length();
		for (int i = 1; i < count; i++) {
			int len = persons[i].getName().length();
			if (len > maxLength) {
				maxLength = len;
			}
		}
		return maxLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.Register#getMaxLengthNumber()
	 */
	@Override
	public int getMaxLengthNumber() {
		int maxLength = 0;
		maxLength = persons[0].getPhoneNumber().length();
		for (int i = 1; i < count; i++) {
			int len = persons[i].getName().length();
			if (len > maxLength) {
				maxLength = len;
			}
		}
		return maxLength;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.Register#findPersonByName(java.lang.String)
	 */
	@Override
	public Person findPersonByName(String name) {
		for (int i = 0; i < count; i++) {
			if (this.persons[i].getName().equals(name)) {
				return persons[i];
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.Register#findPersonByPhoneNumber(java.lang.String)
	 */
	@Override
	public Person findPersonByPhoneNumber(String phoneNumber) {
		for (int i = 0; i < count; i++) {
			if (this.persons[i].getPhoneNumber().equals(phoneNumber)) {
				return persons[i];
			}
		}
		return null;
	}

	public Person findPersonByBoth(String name, String phoneNumber) {
		for (int i = 0; i < count; i++) {
			if (this.persons[i].getName().equals(name)) {
				if (this.persons[i].getPhoneNumber().equals(phoneNumber)) {
					return persons[i];
				}
			}
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see register.Register#removePerson(register.Person)
	 */
	@Override
	public void removePerson(Person person) {
		int index = 0;
		for (int i = 0; i < count; i++) {
			if (this.persons[i].equals(person)) {
				persons[i] = null;
				index = i;
			}
		}
		for (int i = index; i < count - 1; i++) {
			persons[i] = persons[i + 1];
		}
		count--;
	}

	public void updateList() {
		Arrays.sort(persons, 0, count-1);
	}
}
