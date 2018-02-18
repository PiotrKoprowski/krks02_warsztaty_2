package pl.coderslab.warsztat2.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
	private int id = 0;
	private String title = null;
	private String description;

	public Exercise() {
	}

	public Exercise(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void saveExercise(Connection conn) throws SQLException {
		if (this.id == 0) {
			final String[] generatedKeys = { "id" };
			final String sql = "INSERT INTO exercise(id, title, description) " + "VALUES(default, ?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql, generatedKeys);
			ps.setString(1, this.title);
			ps.setString(2, this.description);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} else {
			final String sql = "UPDATE exercise SET title = ?, description = ? " + "WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.title);
			ps.setString(2, this.description);
			ps.setInt(3, this.id);
			ps.executeUpdate();
			ps.close();
		}
	}

	public static Exercise getExerciseById(int id, Connection conn) throws SQLException {

		Exercise exercise = null;
		if (id > 0) {
			final String sql = "SELECT id, title, description " + "FROM exercise WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				exercise = new Exercise();
				exercise.id = rs.getInt("id");
				exercise.title = rs.getString("title");
				exercise.description = rs.getString("description");
			}
			rs.close();
			ps.close();
		}
		return exercise;
	}

	public static Exercise[] getAllExercises(Connection conn) throws SQLException {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		final String sql = "SELECT * FROM exercise;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Exercise exercise = new Exercise();
			exercise.id = rs.getInt("id");
			exercise.title = rs.getString("title");
			exercise.description = rs.getString("description");
			exercises.add(exercise);
		}
		ps.close();
		rs.close();
		Exercise[] arrExercises = new Exercise[exercises.size()];
		arrExercises = exercises.toArray(arrExercises);
		return arrExercises;
	}

	public void deleteExercise(Connection conn) throws SQLException {
		if (this.id != 0) {
			final String sql = "DELETE FROM exercise WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			this.id = 0;
		}
	}
	
	@Override
	public String toString() {
		return "UsersGroup [id=" + id + ", title=" + title + ", description=" + description + "]";
	}
}
