package pl.coderslab.warsztat2.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pl.coderslab.warsztat2.model.Exercise;

public class ExerciseApp {

	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/krks02_warsztat2?useSSL=false",
				"root", "coderslab")) {

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void saveExercise(Connection conn) throws SQLException {
		Exercise exercise = new Exercise("zad_1", "wykonaj zadanie");
		exercise.saveExercise(conn);
		System.out.println("Zadanie zapisane!");
	}

	private static void getExerciseById(int id, Connection conn) throws SQLException {
		Exercise exercise = Exercise.getExerciseById(id, conn);
		System.out.println(exercise);
	}

	private static void getAllExercises(Connection conn) throws SQLException {
		Exercise[] exercises = Exercise.getAllExercises(conn);
		for (int i = 0; i < exercises.length; i++) {
			System.out.println(exercises[i]);
		}
	}

	private void deleteExercise(int id, Connection conn) throws SQLException {
		Exercise exercise = Exercise.getExerciseById(id, conn);
		System.out.println(exercise);
		exercise.deleteExercise(conn);
	}
}
