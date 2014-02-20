import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector 
{
	String dbName = "crawled"; 
	String userName = "av"; 
	String password = "deathstar"; 
	String hostname = "avdb.cafdsygpdakm.us-west-2.rds.amazonaws.com";
	String port = "3306";
	String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
	Connection conn = null;
	
	public static void LoadDriver ()
	{
		try 
		{
			// The newInstance() call is a work around for some
			// broken Java implementations

			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) 
		{
			System.out.println("SQLDriverException: " + ex.getMessage());
		}
	}

	public Connection getDbConnection()
	{
		try 
		{
			LoadDriver();
			conn = DriverManager.getConnection(jdbcUrl);
			return conn;
		}
		catch (SQLException ex) 
		{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	public void closeConnection()
	{
		try
		{
			conn.close();
		}
		catch (SQLException ex) 
		{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
