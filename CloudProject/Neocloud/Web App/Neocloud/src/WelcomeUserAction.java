import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class WelcomeUserAction{
 
	private String username;
	private String password;
	
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
 
	// all struts logic here
	public String execute() {
        WelcomeUserAction wobj = new WelcomeUserAction();
        wobj.setUsername(username);
        wobj.setPassword(password);
       
        // database code to match user name and password 
        if(wobj.getUsername().length()==0 || wobj.getPassword().length()==0)
        {
        	return "FAILURE";
        }
        
        DBConnector myConnector = new DBConnector();
		Connection myConnection = myConnector.getDbConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		try 
		{

			String sqlStmt = "SELECT passwrd FROM users WHERE userName = \""
					+ getUsername() + "\";";
			System.out.println(sqlStmt);
			stmt = myConnection.createStatement();

			if (stmt.execute(sqlStmt)) 
			{
				rs = stmt.getResultSet();
				if(!rs.first())
					return "FAILURE";
				String dbPass = (String)rs.getObject(1);
				if(!  getPassword().equals(dbPass))
				{
					return "FAILURE";
				}
				return "SUCCESS";
			}
			else 
				return "FAILURE";
			
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

