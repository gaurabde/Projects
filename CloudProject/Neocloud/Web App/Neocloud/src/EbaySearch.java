import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.Amount;
import com.ebay.services.finding.FindItemsByKeywordsRequest;
import com.ebay.services.finding.FindItemsByKeywordsResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.SearchItem;

public class EbaySearch 
{
	
	public ArrayList<ResultItem> runEbaySearch (String keyword)
	{
		DBConnector myConnector = new DBConnector();
		Connection myConnection = myConnector.getDbConnection();
		Statement stmt = null;
		ResultSet rs = null;
		int rs_count=0;
		
		//System.out.println("searching for: "+keyword);
		ArrayList<ResultItem> jsp_result = new ArrayList<ResultItem>();
		
		try 
		{

			String sqlStmt = "SELECT * FROM ebay WHERE name LIKE \"%"
					+ keyword + "%\";";
			System.out.println(sqlStmt);
			stmt = myConnection.createStatement();
			

			if (stmt.execute(sqlStmt)) 
			{
				rs = stmt.getResultSet();
				while(rs.next() && rs_count < 4)
				{
					String temp_name = (String)rs.getObject(1);
					String temp_price = (String)rs.getObject(2);
					String temp_url = (String)rs.getObject(3);
					ResultItem temp = new ResultItem(temp_name, temp_price, temp_url);
		        	jsp_result.add(temp);
		        	rs_count++;
				}
			}

			
		} 
		catch (SQLException ex) 
		{
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
		
		// initialize service end-point configuration
		ClientConfig config = new ClientConfig();
    	// endpoint address can be overwritten here, by default, production address is used,
    	// to enable sandbox endpoint, just uncomment the following line
    	//config.setEndPointAddress("http://svcs.sandbox.ebay.com/services/search/FindingService/v1");
    	config.setApplicationId("");

    	//create a service client
        FindingServicePortType serviceClient = FindingServiceClientFactory.getServiceClient(config);
        
        //create request object
        FindItemsByKeywordsRequest request = new FindItemsByKeywordsRequest();
        //set request parameters
        request.setKeywords(keyword);
        PaginationInput pi = new PaginationInput();
        pi.setEntriesPerPage(4);
        request.setPaginationInput(pi);
        
        //call service
        FindItemsByKeywordsResponse result = serviceClient.findItemsByKeywords(request);
        
        //output result
       // System.out.println("Ack = "+result.getAck());
      //  System.out.println("Find " + result.getSearchResult().getCount() + " items." );
        List<SearchItem> items = result.getSearchResult().getItem();
        //System.out.println("Items fetched are ----------->"+items.size()+"**********");
        Amount a;
        
        for(SearchItem item : items) 
        {	
        	if(rs_count > 3)
        		break;
        	rs_count++;
        	
        	//System.out.println(item.getTitle()+" ... "+item.getViewItemURL());
        	a = item.getSellingStatus().getConvertedCurrentPrice();
        	//System.out.println(a.getValue());
        	
        	ResultItem temp = new ResultItem(item.getTitle()," $"+ String.valueOf(a.getValue()), item.getViewItemURL(), result.getItemSearchURL());
        	jsp_result.add(temp);
        }
        
       /* for(ResultItem rs: jsp_result){
        
        System.out.println("RS vals are $$$$$$$$$$ :"+rs.name);
        }*/
		return jsp_result;
	}

}
