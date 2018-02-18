package pl.coderslab.warsztat2.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class Users {
	private int id = 0;
	private String username = "";
	private String email = "";
	private String password = "";
	private int personGroupId = 0;

	public Users() {
	}

	public Users(String username, String email, String password, int personGroupId) {
		super();
		this.username = username;
		this.email = email;
		setPassword(password);
		setPersonGroupId(personGroupId);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public boolean isPasswordCorrect(final String candidate) {
		return BCrypt.checkpw(candidate, this.password);
	}

	public int getPersonGroupId() {
		return personGroupId;
	}

	public void setPersonGroupId(int personGroupId) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/krks02_warsztat2?useSSL=false",
				"root", "coderslab")) {
			if (UsersGroup.getGroupById(personGroupId, conn) == null) {
				this.personGroupId = -1;
			} else {
				this.personGroupId = personGroupId;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void save(Connection conn) throws SQLException {
		if (this.id == 0) {
			final String[] generatedKeys = { "id" };
			final String sql = "INSERT INTO users(id, username, email, password, person_group_id) "
					+ "VALUES(default, ?, ?, ?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql, generatedKeys);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password);
			ps.setInt(4, this.personGroupId);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}

			rs.close();
			ps.close();
		} else {
			final String sql = "UPDATE users SET username = ?, email = ?, password = ?, person_group_id = ? "
					+ "WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password);
			ps.setInt(4, this.personGroupId);
			ps.setInt(5, this.id);
			ps.executeUpdate();
			ps.close();
		}
	}

	public static Users getById(int id, Connection conn) throws SQLException {

		Users user = null;
		if (id > 0) {
			final String sql = "SELECT id, username, email, password, person_group_id " + "FROM users WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new Users();
				user.id = rs.getInt("id");
				user.email = rs.getString("email");
				user.password = rs.getString("password");
				user.personGroupId = rs.getInt("person_group_id");
			}
			rs.close();
			ps.close();
		}
		return user;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", personGroupId=" + personGroupId + "]";
	}

	public static Users[] getAll(Connection conn) throws SQLException {
		ArrayList<Users> users = new ArrayList<Users>();
		final String sql = "SELECT * FROM users;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Users user = new Users();
			user.id = rs.getInt("id");
			user.email = rs.getString("email");
			user.password = rs.getString("password");
			user.personGroupId = rs.getInt("person_group_id");
			users.add(user);
		}
		ps.close();
		rs.close();
		Users[] arrUsers = new Users[users.size()];
		arrUsers = users.toArray(arrUsers);
		return arrUsers;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			final String sql = "DELETE FROM users WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			ps.close();
			this.id = 0;
		}
	}

	public static Users[] getAllUsersByGroupId(int user_id, Connection conn) throws SQLException {
		ArrayList<Users> users = new ArrayList<Users>();
		final String sql = "SELECT * FROM users WHERE person_group_id = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Users user = new Users();
			user.id = rs.getInt("id");
			user.email = rs.getString("email");
			user.password = rs.getString("password");
			user.personGroupId = rs.getInt("person_group_id");
			users.add(user);
		}
		ps.close();
		rs.close();
		Users[] arrUsers = new Users[users.size()];
		arrUsers = users.toArray(arrUsers);
		return arrUsers;
	}

}
