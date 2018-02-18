package pl.coderslab.warsztat2.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pl.coderslab.warsztat2.model.UsersGroup;

public class UsersGroupApp {

	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/krks02_warsztat2?useSSL=false",
				"root", "coderslab")) {
			// getGroupById(1, conn);
			getAllGroups(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void saveGroup(Connection conn) throws SQLException {
		UsersGroup group = new UsersGroup("krk02");
		group.saveGroup(conn);
		System.out.println("Groupa zapisana!");
	}

	private static void getGroupById(int id, Connection conn) throws SQLException {
		UsersGroup group = UsersGroup.getGroupById(id, conn);
		System.out.println(group);
	}

	private static void getAllGroups(Connection conn) throws SQLException {
		UsersGroup[] groups = UsersGroup.getAllGroups(conn);
		for (int i = 0; i < groups.length; i++) {
			System.out.println(groups[i]);
		}
	}

	private static void deleteGroup(int id, Connection conn) throws SQLException {
		UsersGroup group = UsersGroup.getGroupById(id, conn);
		System.out.println(group);
		group.deleteGroup(conn);
	}
}
