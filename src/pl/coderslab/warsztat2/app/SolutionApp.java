package pl.coderslab.warsztat2.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pl.coderslab.warsztat2.model.Solution;

public class SolutionApp {
	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/krks02_warsztat2?useSSL=false",
				"root", "coderslab")) {

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void saveSolution(Connection conn) throws SQLException {
		Solution solution = new Solution(null, null, "jakotako", 1, 1);
		solution.saveSolution(conn);
		solution.setSolutionDescription("bardzo dobrze!");
		System.out.println("finished");
	}

	private static void getSolutionById(int id, Connection conn) throws SQLException {
		Solution solution = Solution.getSolutionById(id, conn);
		System.out.println(solution);
	}

	private static void getAllSolutions(Connection conn) throws SQLException {
		Solution[] solutions = Solution.getAllSolutions(conn);
		for (int i = 0; i < solutions.length; i++) {
			System.out.println(solutions[i]);
		}
	}

	private static void deleteSolution(int id, Connection conn) throws SQLException {
		Solution solution = Solution.getSolutionById(id, conn);
		System.out.println(solution);
		solution.delete(conn);
		System.out.println("Rozwiązanie usunięte!");
	}

	private static void getAllByUserId(int user_id, Connection conn) throws SQLException {
		Solution[] solutions = Solution.getSolutionsByUsersId(user_id, conn);
		for (int i = 0; i < solutions.length; i++) {
			System.out.println(solutions[i]);
		}
	}
}
