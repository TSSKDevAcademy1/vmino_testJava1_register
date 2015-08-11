package register;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ListRegister implements Register, Iterable<Person>, Serializable {

	/**
	 * 
	 */
	private List<Person> persons = new ArrayList<Person>();

	@Override
	public Iterator<Person> iterator() {
		return persons.iterator();
	}

	@Override
	public int getCount() {
//		int count = 0;
//		Iterator<Person> iterator = persons.iterator();
//		while (iterator.hasNext()) {
//			iterator.next();
//			count++;
//		}
		return persons.size();
	}

	@Override
	public int getSize() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Person getPerson(int index) {
		return persons.get(index);
	}

	@Override
	public void addPerson(Person person) {
		persons.add(person);

		updateList();

	}

	@Override
	public int getMaxLengthName() {
		int maxLength = 0;
		int length;
		Iterator<Person> iterator = persons.iterator();
		while (iterator.hasNext()) {
			length = iterator.next().getName().length();
			if (length > maxLength) {
				maxLength = length;
			}
		}
		return maxLength;
	}

	@Override
	public int getMaxLengthNumber() {
		int maxLength = 0;
		int length;
		Iterator<Person> iterator = persons.iterator();
		while (iterator.hasNext()) {
			length = iterator.next().getPhoneNumber().length();
			if (length > maxLength) {
				maxLength = length;
			}
		}
		return maxLength;
	}

	@Override
	public Person findPersonByName(String name) {
		Iterator<Person> iterator = persons.iterator();
		while (iterator.hasNext()) {
			Person p = iterator.next();
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	@Override
	public Person findPersonByPhoneNumber(String phoneNumber) {
		Iterator<Person> iterator = persons.iterator();
		while (iterator.hasNext()) {
			Person p = iterator.next();
			if (p.getPhoneNumber().equals(phoneNumber)) {
				return p;
			}
		}
		return null;
	}

	@Override
	public Person findPersonByBoth(String name, String phoneNumber) {
		Iterator<Person> iterator = persons.iterator();
		while (iterator.hasNext()) {
			Person p = iterator.next();
			if (p.getName().equals(name)) {
				if (p.getPhoneNumber().equals(phoneNumber)) {
					return p;
				}
			}
		}
		return null;
	}

	@Override
	public void removePerson(Person person) {
		persons.remove(person);
		updateList();
	}

	public void updateList() {
		Collections.sort(persons);
	}
}
