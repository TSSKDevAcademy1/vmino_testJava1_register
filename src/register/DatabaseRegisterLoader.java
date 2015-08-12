package register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRegisterLoader implements RegisterLoader {

	public static final String URL = "jdbc:mysql://localhost/register";
	public static final String USER = "root";
	public static final String PASSWORD = "root";

	@Override
	public void save(Register register) {
		String SET = "SET SQL_SAFE_UPDATES = 0";
		String DELETE = "DELETE FROM person";
		String QUERY = "INSERT INTO person (id, name, phoneNumber) VALUES (?, ?, ?)";
		int count = register.getCount();
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement del = con.createStatement();
				PreparedStatement stmt = con.prepareStatement(QUERY);) {
			del.execute(SET);
			del.executeUpdate(DELETE);
			for (int i = 0; i < count; i++) {
				stmt.setInt(1, i);
				stmt.setString(2, register.getPerson(i).getName());
				stmt.setString(3, register.getPerson(i).getPhoneNumber());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception");
			e.printStackTrace();
			return;
		}

	}

	@Override
	public Register load() {
		String QUERY = "SELECT id, name, phoneNumber FROM person";
		Register register = new ListRegister();
		String name;
		String phoneNumber;
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = con.createStatement();) {
			ResultSet rs = stmt.executeQuery(QUERY);
			while (rs.next()) {
				name = rs.getString(2);
				phoneNumber = rs.getString(3);
				register.addPerson(new Person(name, phoneNumber));
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception");
			return null;
		}

		return register;
	}

	public void createTable() {
		String QUERY = "CREATE TABLE person (id INT PRIMARY KEY, name VARCHAR(32) NOT NULL, phoneNumber VARCHAR(18) NOT NULL)";
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = con.createStatement();) {
			stmt.executeUpdate(QUERY);
		} catch (SQLException e) {
			System.err.println("SQL Exception");
		}

	}
}
