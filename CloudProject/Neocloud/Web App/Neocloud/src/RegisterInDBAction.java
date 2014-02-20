import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class RegisterInDBAction {
	private String username;
	private String password;
	private String email;

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
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String execute() {
		if (username.length() == 0 | password.length() == 0) {
			return "FAILURE";
		}

		DBConnector myConnector = new DBConnector();
		Connection myConnection = myConnector.getDbConnection();
		Statement stmt = null;
		ResultSet rs = null;

		try 
		{

			String sqlStmt = "SELECT * FROM users WHERE userName = \""
					+ getUsername() + "\";";
			System.out.println(sqlStmt);
			stmt = myConnection.createStatement();

			if (stmt.execute(sqlStmt)) 
			{
				rs = stmt.getResultSet();
				if(rs.first())
					return "FAILURE";
			}

			stmt = myConnection.createStatement();
			sqlStmt = "INSERT INTO users VALUES (\"" + getUsername() + "\", \""
					+ getEmail() + "\", \"" + getPassword() + "\");";
			System.out.println(sqlStmt);
			stmt.execute(sqlStmt);
			return "SUCCESS";
			
		} 
		catch (SQLException ex) 
		{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return "FAILURE";
		}

	}
}
