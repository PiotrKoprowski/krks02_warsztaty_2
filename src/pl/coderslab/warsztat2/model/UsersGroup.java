package pl.coderslab.warsztat2.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersGroup {
	private int id = 0;
	private String name = null;

	public UsersGroup() {
	}

	public UsersGroup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void saveGroup(Connection conn) throws SQLException {
		if (this.id == 0) {
			final String[] generatedKeys = { "id" };
			final String sql = "INSERT INTO usersGroup(id, name) " + "VALUES(default, ?);";
			PreparedStatement ps = conn.prepareStatement(sql, generatedKeys);
			ps.setString(1, this.name);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
			rs.close();
			ps.close();
		} else {
			final String sql = "UPDATE usersGroup SET name = ? " + "WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.name);
			ps.setInt(2, this.id);
			ps.executeUpdate();
			ps.close();
		}
	}

	public static UsersGroup getGroupById(int id, Connection conn) throws SQLException {

		UsersGroup usersGroup = null;
		if (id > 0) {
			final String sql = "SELECT id, name " + "FROM usersGroup WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				usersGroup = new UsersGroup();
				usersGroup.id = rs.getInt("id");
				usersGroup.name = rs.getString("name");
			}
			rs.close();
			ps.close();
		}
		return usersGroup;
	}

	public static UsersGroup[] getAllGroups(Connection conn) throws SQLException {
		ArrayList<UsersGroup> usersGroup = new ArrayList<UsersGroup>();
		final String sql = "SELECT * FROM usersGroup;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			UsersGroup group = new UsersGroup();
			group.id = rs.getInt("id");
			group.name = rs.getString("name");
			usersGroup.add(group);
		}
		ps.close();
		rs.close();
		UsersGroup[] arrGroups = new UsersGroup[usersGroup.size()];
		arrGroups = usersGroup.toArray(arrGroups);
		return arrGroups;
	}

	public void deleteGroup(Connection conn) throws SQLException {
		if (this.id != 0) {
			final String sql = "DELETE FROM usersGroup WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			this.id = 0;
		}
	}

	@Override
	public String toString() {
		return "UsersGroup [id=" + id + ", name=" + name + "]";
	}

}
