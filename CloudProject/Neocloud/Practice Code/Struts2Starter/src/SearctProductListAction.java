
public class SearctProductListAction {
	private String searchProduct;
	private String searchProductList;
	private String productPrice;
	
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
		setSearchProductList(product+ "1  , "+ product+"2  " );
		setProductPrice("$30");
		System.out.println(getSearchProduct());
		return "SUCCESS";
 
	}
}
