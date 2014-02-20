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
			String prodName=null,prodURL=null,prodPrice=null,prodStore=null;
			
			//rs = stmt.executeQuery("SELECT * FROM test;");
			
			rs=stmt.executeQuery("SHOW TABLES;");
			rs.next();
			String setSearchProductList =((String)rs.getObject(1));
			while(rs.next()){
				System.out.println(setSearchProductList+ ": Successful from Database!");
				setSearchProductList =((String)rs.getObject(1));
			}
			String statement=new String();
			int prodCount=0;
			while(stringInput!=null){
				String [] prod=stringInput.split("::::");
				//stmt.executeUpdate("INSERT INTO Ebay_Products (ID,Product_Name,Product_URL,Product_Price,Store_Name) VALUES (0,\""+prod[1]+"\",\""+prod[3]+"\","+prod[5]+",\""+prod[7]+"\");");
				
				if(prod.length<8){
					stringInput = input.readLine();
				}else{
					if(prod[1].equals("")){
						prodName="Neocloud";
						System.out.println("ProdName NULL");
					}else{
						prodName=prodNameCheck(prod[1]);
					}
					
					if(prod[3].equals("")){
						prodURL="www.bestprice.com";
						System.out.println("ProdURL NULL");
					}else{
						prodURL=prodURLCheck(prod[3]);
						//System.out.println(prodURL);
					}
					
					if(prod[5].equals("")||prod[5].equals("EMPTY")){
						prodPrice="$0.00";
						System.out.println("ProdPrice NULL");
					}else{
						prodPrice=prodPriceCheck(prod[5]);
						//System.out.println(prodPrice);
					}
					
					if(prod[7].equals("")){
						prodStore="BestPrice";
						System.out.println("ProdStore NULL");
					}else{
						prodStore=prod[7];
					}
					statement="INSERT INTO ebay VALUES (\'"+prodName+"\',\'"+prodPrice+"\',\'"+prodURL+"\');";
					System.out.println(statement);
					prodCount++;
					stmt.executeUpdate(statement);
					stringInput = input.readLine();	
					
				}
				
				
			}
			
			System.out.println("Count: "+prodCount);
			
			rs.close();
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
	
	static String prodNameCheck(String data){
		//Split for "="
		String temp="";
		String newdata=data;
		String [] check1=data.split("=");
		int len=0;
		if(check1.length>1){
			while(len<check1.length){
				temp=temp+check1[len];
				len++;
			}
			newdata=temp;
			temp="";
			len=0;
		}
		
		check1=newdata.split("\"");
		if(check1.length>1){
			while(len<check1.length){
				temp=temp+check1[len];
				len++;
			}
			newdata=temp;
			temp="";
			len=0;
		}
		
		check1=newdata.split("'");
		len=0;
		if(check1.length>1){
			while(len<check1.length){
				temp=temp+check1[len];
				len++;
			}
			newdata=temp;
			temp="";
			len=0;
		}
		
		return newdata;
	}
	
	static String prodURLCheck(String data){
		String newdata=data,temp="";
		int len=0;
		String[] check=newdata.split("=");
		
		if(check.length>1){
			while(len<check.length){
				temp=temp+check[len];
				len++;
			}
			newdata=temp;
			temp="";
			len=0;
		}
		
		check=newdata.split("\"");
		if(check.length>1){
			while(len<check.length){
				temp=temp+check[len];
				len++;
			}
			newdata=temp;
			temp="";
			len=0;
		}
		return newdata;
	}
	
	static String prodPriceCheck(String data){
		String newdata=data,temp="";
		int len=0;
		
		String[] check=newdata.split("US");
		if(check.length>1){
			while(len<check.length){
				temp=temp+check[len];
				len++;
			}
			newdata=temp;
			temp="";
			len=0;
		}
		
		check=newdata.split("$");
		if(check.length>1){
			while(len<check.length){
				temp=temp+check[len];
				len++;
			}
			newdata=temp;
			temp="";
			len=0;
		}
		
		check=newdata.split(" ");
		if(check.length>1){
			while(len<check.length){
				temp=temp+check[len];
				len++;
			}
			newdata=temp;
			temp="";
			len=0;
		}
		
		return newdata;
	}
}
