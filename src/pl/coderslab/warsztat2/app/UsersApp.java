package pl.coderslab.warsztat2.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pl.coderslab.warsztat2.model.Users;
import pl.coderslab.warsztat2.model.UsersGroup;

public class UsersApp {

	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/krks02_warsztat2?useSSL=false",
				"root", "coderslab")) {
			// deleteUser(2, conn);
			// detAllUsers(conn);
			// daveUser(conn);
			getAllUsers(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void saveUser(Connection conn) throws SQLException {
		Users user = new Users("rafal", "rafal@mail.com", "rafal", 1);
		user.save(conn);
		Users user2 = new Users("rafal2", "rafal2@mail.com", "rafal", 1);
		user2.save(conn);
		user2.setEmail("rafal1222@mail.pl");
		user2.save(conn);
		System.out.println("finished");

	}

	static void test() {
		Users user = new Users("rafal", "rafal@gmail.com", "rafal", 1);
		System.out.println(user.getPassword());
		System.out.println(user.isPasswordCorrect("rafa"));
		System.out.println(user.isPasswordCorrect("rafal"));
	}

	private static void getUserById(int id, Connection conn) throws SQLException {
		Users user = Users.getById(id, conn);
		System.out.println(user);
	}

	private static void getAllUsers(Connection conn) throws SQLException {
		Users[] users = Users.getAll(conn);
		for (int i = 0; i < users.length; i++) {
			System.out.println(users[i]);
		}
	}

	private static void deleteUser(int id, Connection conn) throws SQLException {
		Users user = Users.getById(id, conn);
		System.out.println(user);
		user.delete(conn);
		System.out.println("Użytkownik usunięty!");
	}

	private static void getAllUsersByGroupId(int user_id, Connection conn) throws SQLException {
		Users[] users = Users.getAllUsersByGroupId(user_id, conn);
		for (int i = 0; i < users.length; i++) {
			System.out.println(users[i]);
		}
	}
}
