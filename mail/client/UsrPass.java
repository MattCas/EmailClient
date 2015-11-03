/**
 *Stores User/Password tuple as 2 <code>Strings</code>.
 */
package mail.client;

public class UsrPass {
	private final String username; 
	private final String password; 
	public UsrPass(String usr, String pwd) { 
		this.username = usr;
		this.password = pwd;
	}
	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
}
