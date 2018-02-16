package pl.coderslab.warsztat2.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pl.coderslab.warsztat2.model.Users;

public class UsersApp {

	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/krks02_warsztat2?useSSL=false",
				"root", "coderslab")) {
			testGetById(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// test();

	}

	private static void testSave(Connection conn) throws SQLException {
		Users user = new Users("rafal", "rafal@mail.com", "rafal");
		user.save(conn);
		Users user2 = new Users("rafal2", "rafal2@mail.com", "rafal");
		user2.save(conn);
		user2.setEmail("rafal1222@mail.pl");
		user2.save(conn);
		System.out.println("finished");

	}

	static void test() {
		Users user = new Users("rafal", "rafal@gmail.com", "rafal");
		System.out.println(user.getPassword());
		System.out.println(user.isPasswordCorrect("rafa"));
		System.out.println(user.isPasswordCorrect("rafal"));
	}
	
	private static void testGetById(Connection conn) throws SQLException {
		Users user = Users.getById(1, conn);
		System.out.println(user);
	

	}

}
