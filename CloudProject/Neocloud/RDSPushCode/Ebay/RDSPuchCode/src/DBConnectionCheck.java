import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnectionCheck {
	public static void main(String args[]) throws SQLException{
		DBConnector db =new DBConnector();
		
		Connection conn= db.getDbConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = conn.createStatement();
			//stmt.executeUpdate("ALTER TABLE ebay MODIFY COLUMN name varchar(255);");
			rs=stmt.executeQuery("select * from ebay;");
			
			stmt.executeUpdate("DELETE from ebay;");
			rs=stmt.executeQuery("select * from ebay;");
			int count=0;
			//rs.next();
			while(rs.next()){
				System.out.println(rs.getObject(1)+"-- "+rs.getObject(2)+"--"+rs.getObject(3));
				count++;
			}
			System.out.println("totalCount: "+count);
		}
		finally {
			// it is a good idea to release
			// resources in a finally{} block
			// in reverse-order of their creation
			// if they are no-longer needed

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) { } // ignore

				rs = null;
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) { } // ignore

				stmt = null;
			}
		}

		
	}

}
