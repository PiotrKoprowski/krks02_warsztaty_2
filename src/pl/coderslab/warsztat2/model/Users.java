package pl.coderslab.warsztat2.model;

import org.mindrot.jbcrypt.BCrypt;

public class Users {
	private int id = 0;
	private String username = "";
	private String email = "";
	private String password = "";
	private int person_group_id = 0;
	
	public Users() {
	}

	public Users(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		setPassword(password);
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
	
	public boolean isPasswordCorrect(final String candidate){
		return BCrypt.checkpw(candidate, this.password);
	}

	public int getPerson_group_id() {
		return person_group_id;
	}

	public void setPerson_group_id(int person_group_id) {
		this.person_group_id = person_group_id;
	}

	public int getId() {
		return id;
	}
	
	
}
