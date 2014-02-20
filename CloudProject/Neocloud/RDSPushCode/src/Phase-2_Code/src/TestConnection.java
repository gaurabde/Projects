import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;



public class TestConnection {
	public static void main(String[] args) throws IOException{
		System.out.println("Starting test");
		DBConnector db =new DBConnector();
		
		Connection conn= db.getDbConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String filePath="/home/gaurabde/Documents/Cloud/workSpace/RDSPuchCode/ParserOuputData/productDetails_EBay";
			BufferedReader input = new BufferedReader(new FileReader(filePath));
			System.out.println("loading");
			int i=0,j=1;
			String stringInput = input.readLine();
			stmt = conn.createStatement();
			System.out.println("create statement");
			
			while(stringInput!=null){
				String [] prod=stringInput.split("::::");
				//stmt.executeUpdate("INSERT INTO Ebay_Products (ID,Product_Name,Product_URL,Product_Price,Store_Name) VALUES (0,\""+prod[1]+"\",\""+prod[3]+"\","+prod[5]+",\""+prod[7]+"\");");
				System.out.println(""+prod[1]+"\",\""+prod[3]+"\","+prod[5]+",\""+prod[7]+"\"");
				if(i>10){
					i=0;
					System.out.println();
				}else{
					i++;
					
				}
				j++;
				stringInput = input.readLine();
			}

			//rs.next();
			//String searchProductList= ((String)rs.getObject(1));
			//System.out.println(searchProductList+ "Successful from Database!");
		
		}
		catch (SQLException ex){
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
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
