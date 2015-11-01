package mail.client;

public class UsrPass {
	private final String username; 
	private final String password; 
	public UsrPass(String usr, String pwd) { 
		this.username = usr;
		this.password = pwd;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
