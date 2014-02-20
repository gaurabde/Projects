import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.ArrayList;


public class SearctProductListAction {
	private String searchProduct;
	private String searchProductList;
	private String productPrice;
	
	private String ebayResultList;
	private String ebayProductPrice;
	private String ebayProductURL;
	private String amazonResultList;
	private String amazonProductPrice;
	private String amazonProductURL;
	private String bestBuyResultList;
	private String bestBuyProductPrice;
	private String bestBuyProductURL;
	private String walmartResultList;
	private String walmartProductPrice;
	private String walmartProductURL;
	
	public String getEbayResultList() {
		return ebayResultList;
	}
	public void setEbayResultList(String ebayResultList) {
		this.ebayResultList = ebayResultList;
	}
	
	public String getEbayProductPrice() {
		return ebayProductPrice;
	}
	public void setEbayProductPrice(Double ebayProductPrice) {
		this.ebayProductPrice = ebayProductPrice.toString();
	}
	
	public String getEbayProductURL() {
		return ebayProductURL;
	}
	public void setEbayProductURL(String ebayProductURL) {
		this.ebayProductURL = ebayProductURL;
	}

	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getSearchProduct() {
		return searchProduct;
	}
	public void setSearchProduct(String searchProduct) {
		this.searchProduct = searchProduct;
	}
	public String getSearchProductList() {
		return searchProductList;
	}
	public void setSearchProductList(String searchProductList) {
		this.searchProductList = searchProductList;
	}

	public String execute() 
	{
		
		String product = getSearchProduct();

		//DB Test code
		DBConnector myConnector = new DBConnector();
		Connection myConnection = myConnector.getDbConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = myConnection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM test");
			rs.next();
			setSearchProductList ((String)rs.getObject(1));
			System.out.println(searchProductList+ "Successful from Database!");
			// or alternatively, if you don't know ahead of time that
			// the query will be a SELECT...

			//if (stmt.execute("SELECT foo FROM bar")) {
			//	rs = stmt.getResultSet();
			//}

			// Now do something with the ResultSet ....
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

		//setSearchProductList(product+ "1  , "+ product+"2  " );
		setProductPrice("$30");
		System.out.println(getSearchProduct());
		
		//End of DB Test Code
		
		EbaySearch ebayObj = new EbaySearch();
		ArrayList<ResultItem> ebayResults = ebayObj.runEbaySearch(product);
		if(ebayResults.size()==0)
		{
			ebayResultList = "No Results";
			ebayProductPrice = "-";
			ebayProductURL = "-";
		}
		else
		{
			ebayResultList = ebayResults.get(0).name;
			ebayProductPrice = ebayResults.get(0).price.toString();
			ebayProductURL = ebayResults.get(0).url;
		}
		
		return "SUCCESS";

	}
}



