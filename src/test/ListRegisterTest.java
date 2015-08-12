package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import register.*;

public class ListRegisterTest {
	
	Register register;
	Person person;

	@Before
	public void setUp() throws Exception {
		register = new ListRegister();
		person = new Person("Janko Hrasko", "0948119407");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addPerson() {
		register.addPerson(new Person("Janko Hrasko", "0900123456"));
		assertEquals(register.getCount(), 1);
		register.addPerson(new Person("Janko Hrasko", "0900123456"));
		assertEquals(register.getCount(), 2);
	}
	
	@Test
	public void removePerson() {
		register.addPerson(new Person("Janko Hrasko", "0900123456"));
		register.addPerson(new Person("Janko", "0900000000"));
		register.addPerson(new Person("ABC", "0944444444"));
		register.removePerson(register.getPerson(0));
		assertEquals(register.getCount(), 2);
		register.removePerson(register.findPersonByName("Janko Hrasko"));
		assertEquals(register.getCount(), 1);
		register.removePerson(register.findPersonByPhoneNumber("0900000000"));
		assertEquals(register.getCount(), 0);
	}
	
	@Test
	public void addRemovePerson() {
		register.addPerson(new Person("Janko Hrasko", "0900123456"));
		assertEquals(register.getCount(), 1);
		register.addPerson(new Person("Janko Hrasko", "0900123456"));
		assertEquals(register.getCount(), 2);
		register.removePerson(register.findPersonByName("Janko Hrasko"));
		assertEquals(register.getCount(), 1);
		register.removePerson(register.findPersonByPhoneNumber("0900123456"));
		assertEquals(register.getCount(), 0);
	}
	
	@Test
	public void updatePerson() {
		register.addPerson(new Person("Janko Hrasko", "0900123456"));
		register.addPerson(new Person("Janko", "0900000000"));
		register.addPerson(new Person("ABC", "0944444444"));
		register.getPerson(0).setName("ABCD");
		register.getPerson(0).setPhoneNumber("0911222333");
		assertEquals(register.getPerson(0).getName(), "ABCD");
		assertEquals(register.getPerson(0).getPhoneNumber(), "0911222333");
	}
	
	@Test(expected=RuntimeException.class)
	public void wrongPhoneNumberLength() {
		person.setPhoneNumber("000");
	}
	
	@Test(expected=RuntimeException.class)
	public void wrongPhoneNumber1() {
		person.setPhoneNumber("+911222333");
	}

	@Test(expected=RuntimeException.class)
	public void wrongPhoneNumber2() {
		person.setPhoneNumber("01948119401");
	}	
	
	@Test(expected=RuntimeException.class)
	public void wrongPhoneNumber3() {
		person.setPhoneNumber("00421telefon");
	}	
	
	@Test
	public void rightPhoneNumber() {
		Person person = new Person("Janko Hrasko", "0900123456");
		Throwable ex = null;
		
		try{
			person.setPhoneNumber("0900111222");
		} catch (RuntimeException e){
			ex = e;
		}
		assertFalse(ex instanceof RuntimeException);
		
		ex=null;
		try{
			person.setPhoneNumber("00421911123123");
		} catch (RuntimeException e){
			ex = e;
		}
		assertFalse(ex instanceof RuntimeException);
		
		ex=null;
		try{
			person.setPhoneNumber("+421911123123");
		} catch (RuntimeException e){
			ex = e;
		}
		assertFalse(ex instanceof RuntimeException);
		
		ex=null;
		try{
			person.setPhoneNumber("0900 111 222");
		} catch (RuntimeException e){
			ex = e;
		}
		assertFalse(ex instanceof RuntimeException);
		
		ex=null;
		try{
			person.setPhoneNumber("+491 900 111 222");
		} catch (RuntimeException e){
			ex = e;
		}
		assertFalse(ex instanceof RuntimeException);
		
		ex=null;
		try{
			person.setPhoneNumber("00 491 900 111 222");
		} catch (RuntimeException e){
			ex = e;
		}
		assertFalse(ex instanceof RuntimeException);
	}

}
