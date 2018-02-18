package pl.coderslab.warsztat2.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Solution {
	/*
	 * public static void main(String[] args) { Date created2; created2 = new
	 * Date(); System.out.println(created2); }
	 */
	private int id = 0;
	private Date created;
	private Date updated;
	private String description;
	private int exercise_id;
	private int users_id;

	public Solution() {
	}

	public Solution(Date created, Date updated, String description, int exercise_id, int users_id) {
		setCreated(created);
		setUpdated(updated);
		this.description = description;
		setExercise_id(exercise_id);
		setUsers_id(users_id);
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		created = new Date();
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		updated = new Date();
		this.updated = updated;
	}

	public String getSolutionDescription() {
		return description;
	}

	public void setSolutionDescription(String description) {
		this.description = description;
	}

	public int getExercise_id() {
		return exercise_id;
	}

	public void setExercise_id(int exercise_id) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/krks02_warsztat2?useSSL=false",
				"root", "coderslab")) {
			if (Exercise.getExerciseById(exercise_id, conn) == null) {
				this.exercise_id = -1;
			} else {
				this.exercise_id = exercise_id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/krks02_warsztat2?useSSL=false",
				"root", "coderslab")) {
			if (Users.getById(users_id, conn) == null) {
				this.users_id = -1;
			} else {
				this.users_id = users_id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getSolutionId() {
		return id;
	}

	public void saveSolution(Connection conn) throws SQLException {
		if (this.id == 0) {
			final String[] generatedKeys = { "id" };
			final String sql = "INSERT INTO solution(id, created, updated, description, exercise_id, users_id) "
					+ "VALUES(default, ?, ?, ?, ?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql, generatedKeys);
			ps.setDate(1, (java.sql.Date) this.created);
			ps.setDate(2, (java.sql.Date) this.updated);
			ps.setString(3, this.description);
			ps.setInt(4, this.exercise_id);
			ps.setInt(5, this.users_id);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}

			rs.close();
			ps.close();
		} else {
			final String sql = "UPDATE solution SET updated = ?, description = ?, exercise_id = ?, users_id = ? "
					+ "WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, (java.sql.Date) this.updated);
			ps.setString(2, this.description);
			ps.setInt(3, this.exercise_id);
			ps.setInt(4, this.users_id);
			ps.setInt(5, this.id);
			ps.executeUpdate();
			ps.close();
		}
	}

	public static Solution getSolutionById(int id, Connection conn) throws SQLException {

		Solution solution = null;
		if (id > 0) {
			final String sql = "SELECT id, created, updated, description, exercise_id, users_id "
					+ "FROM solution WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				solution = new Solution();
				solution.id = rs.getInt("id");
				solution.created = rs.getDate("created");
				solution.updated = rs.getDate("updated");
				solution.exercise_id = rs.getInt("exercise_id");
				solution.users_id = rs.getInt("users_id");
			}
			rs.close();
			ps.close();
		}
		return solution;
	}

	public static Solution[] getAllSolutions(Connection conn) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		final String sql = "SELECT * FROM solution;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution solution = new Solution();
			solution.id = rs.getInt("id");
			solution.created = rs.getDate("created");
			solution.updated = rs.getDate("updated");
			solution.exercise_id = rs.getInt("exercise_id");
			solution.users_id = rs.getInt("users_id");
			solutions.add(solution);
		}
		ps.close();
		rs.close();
		Solution[] arrSolutions = new Solution[solutions.size()];
		arrSolutions = solutions.toArray(arrSolutions);
		return arrSolutions;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			final String sql = "DELETE FROM solution WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			ps.close();
			this.id = 0;
		}
	}

	@Override
	public String toString() {
		return "Solution [id=" + id + ", created=" + created + ", updated=" + updated + ", exercise_id=" + exercise_id
				+ ", users_id=" + users_id + "]";
	}

	public static Solution[] getSolutionsByUsersId(int user_id, Connection conn) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		final String sql = "SELECT * FROM solution WHERE users_id = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution solution = new Solution();
			solution.id = rs.getInt("id");
			solution.created = rs.getDate("created");
			solution.updated = rs.getDate("updated");
			solution.exercise_id = rs.getInt("exercise_id");
			solution.users_id = rs.getInt("users_id");
			solutions.add(solution);
		}
		ps.close();
		rs.close();
		Solution[] arrSolutions = new Solution[solutions.size()];
		arrSolutions = solutions.toArray(arrSolutions);
		return arrSolutions;
	}
}
