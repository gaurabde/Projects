import java.util.ArrayList;

public class SearctProductListAction {
	private String searchProduct;
	private String searchProductList;
	private String productPrice;

	private String ebayResultList;
	private String ebayProductPrice;
	public String ebayProductURL;
	private String amazonResultList;
	private String amazonProductPrice;
	private String amazonProductURL;
	private String bestBuyResultList;
	private String bestBuyProductPrice;
	private String bestBuyProductURL;
	private String walmartResultList;
	private String walmartProductPrice;
	private String walmartProductURL;
	private ArrayList<ResultItem> ebayResults;
	ArrayList<ResultItem> amazonResults;
	ArrayList<ResultItem> bestBuyResults;
	ArrayList<ResultItem> walmartResults;

	public ArrayList<ResultItem> getAmazonResults() {
		return amazonResults;
	}

	public void setAmazonResults(ArrayList<ResultItem> amazonResults) {
		this.amazonResults = amazonResults;
	}

	public ArrayList<ResultItem> getBestBuyResults() {
		return bestBuyResults;
	}

	public void setBestBuyResults(ArrayList<ResultItem> bestBuyResults) {
		this.bestBuyResults = bestBuyResults;
	}

	public ArrayList<ResultItem> getWalmartResults() {
		return walmartResults;
	}

	public void setWalmartResults(ArrayList<ResultItem> walmartResults) {
		this.walmartResults = walmartResults;
	}

	public ArrayList<ResultItem> getEbayResults() {
		return ebayResults;
	}

	public void setEbayResults(ArrayList<ResultItem> ebayResults) {
		this.ebayResults = ebayResults;
	}

	// Ebay
	public String getEbayResultList() {
		return ebayResultList;
	}

	public void setEbayResultList(String ebayResultList) {
		this.ebayResultList = ebayResultList;
	}

	public String getEbayProductPrice() {
		return ebayProductPrice;
	}

	public void setEbayProductPrice(String ebayProductPrice) {
		this.ebayProductPrice = "$" + ebayProductPrice.toString();
	}

	public String getEbayProductURL() {
		return ebayProductURL;
	}

	public void setEbayProductURL(String ebayProductURL) {
		this.ebayProductURL = ebayProductURL;
	}

	// Ebay

	// Amazon
	public String getAmazonResultList() {
		return amazonResultList;
	}

	public void setAmazonResultList(String amazonResultList) {
		this.amazonResultList = amazonResultList;
	}

	public String getAmazonProductPrice() {
		return amazonProductPrice;
	}

	public void setAmazonProductPrice(String amazonProductPrice) {
		this.amazonProductPrice = amazonProductPrice;
	}

	public String getAmazonProductURL() {
		return amazonProductURL;
	}

	public void setAmazonProductURL(String amazonProductURL) {
		this.amazonProductURL = amazonProductURL;
	}

	// Amazon

	// BestBuy
	public String getBestBuyResultList() {
		return bestBuyResultList;
	}

	public void setBestBuyResultList(String bestBuyResultList) {
		this.bestBuyResultList = bestBuyResultList;
	}

	public String getBestBuyProductPrice() {
		return bestBuyProductPrice;
	}

	public void setBestBuyProductPrice(String bestBuyProductPrice) {
		this.bestBuyProductPrice = bestBuyProductPrice;
	}

	public String getBestBuyProductURL() {
		return bestBuyProductURL;
	}

	public void setBestBuyProductURL(String bestBuyProductURL) {
		this.bestBuyProductURL = bestBuyProductURL;
	}

	// BestBuy

	// Walmart
	public String getWalmartResultList() {
		return walmartResultList;
	}

	public void setWalmartResultList(String walmartResultList) {
		this.walmartResultList = walmartResultList;
	}

	public String getWalmartProductPrice() {
		return walmartProductPrice;
	}

	public void setWalmartProductPrice(String walmartProductPrice) {
		this.walmartProductPrice = walmartProductPrice;
	}

	public String getWalmartProductURL() {
		return walmartProductURL;
	}

	public void setWalmartProductURL(String walmartProductURL) {
		this.walmartProductURL = walmartProductURL;
	}

	// Walmart

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

	public String execute() {

		String product = getSearchProduct();
		if (product.length() == 0) {
			return "FAILURE";
		}

		// DB Test code**********************Remove comments from here for
		// DB************************
		/*
		 * DBConnector myConnector = new DBConnector(); Connection myConnection
		 * = myConnector.getDbConnection(); Statement stmt = null; ResultSet rs
		 * = null; try { stmt = myConnection.createStatement(); rs =
		 * stmt.executeQuery("SELECT * FROM test"); rs.next();
		 * setSearchProductList((String) rs.getObject(1));
		 * System.out.println(searchProductList + "Successful from Database!");
		 * // or alternatively, if you don't know ahead of time that // the
		 * query will be a SELECT...
		 * 
		 * // if (stmt.execute("SELECT foo FROM bar")) { // rs =
		 * stmt.getResultSet(); // }
		 * 
		 * // Now do something with the ResultSet .... } catch (SQLException ex)
		 * { // handle any errors System.out.println("SQLException: " +
		 * ex.getMessage()); System.out.println("SQLState: " +
		 * ex.getSQLState()); System.out.println("VendorError: " +
		 * ex.getErrorCode()); } finally { // it is a good idea to release //
		 * resources in a finally{} block // in reverse-order of their creation
		 * // if they are no-longer needed
		 * 
		 * if (rs != null) { try { rs.close(); } catch (SQLException sqlEx) { }
		 * // ignore
		 * 
		 * rs = null; }
		 * 
		 * if (stmt != null) { try { stmt.close(); } catch (SQLException sqlEx)
		 * { } // ignore
		 * 
		 * stmt = null; } }
		 * 
		 * // setSearchProductList(product+ "1  , "+ product+"2  " );
		 * setProductPrice("$30"); System.out.println(getSearchProduct());
		 */

		// End of DB Test Code*******************Till
		// here************************************************

		// Ebay Results fetching and Parsing
		EbaySearch ebayObj = new EbaySearch();
		ebayResults = ebayObj.runEbaySearch(product);

		/*
		 * for(ResultItem rs : ebayResults){ System.out.println(rs.name);
		 * System.out.println(rs.price); System.out.println(rs.url); }
		 */
		if (ebayResults == null || ebayResults.size() == 0) {
			ebayResultList = "No Results";
			ebayProductPrice = "-";
			ebayProductURL = "-";
		} else {
			setEbayResults(ebayResults);
			setEbayResultList(ebayResults.get(0).name);
			setEbayProductPrice(ebayResults.get(0).price);
			setEbayProductURL(ebayResults.get(0).url);
			/*
			 * setEbayResultList(ebayResults.get(1).name);
			 * setEbayProductPrice(ebayResults.get(1).price);
			 * setEbayProductURL(ebayResults.get(1).url);
			 */
		}
		// End of Ebay code

		// Amazon result fetching and parsing
		AmazonSearch amazonObj = new AmazonSearch();
		amazonResults = amazonObj.runAmazonSearch(product);
		if (amazonResults == null || amazonResults.size() == 0) {
			setAmazonResultList("No Results");
			setAmazonProductPrice("-");
			setAmazonProductURL("-");
		} else {
			setAmazonResultList(amazonResults.get(0).name);
			setAmazonProductPrice(amazonResults.get(0).price);
			setAmazonProductURL(amazonResults.get(0).url);
		}
		// End of Amazon Code

		// Best Buy result fetching and parsing
		BestBuySearch bestBuyObj = new BestBuySearch();
		setBestBuyResults(bestBuyObj.runBestBuySearch(product));
		// End of Best Buy Code

		// Walmart result fetching and parsing
		WalmartSearch walmartObj = new WalmartSearch();
		setWalmartResults(walmartObj.runWalmartSearch(product));
		// End of Walmart Code

		return "SUCCESS";

	}
}
