package consoleFindItem;

import java.util.List;

import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.Amount;
import com.ebay.services.finding.FindItemsByKeywordsRequest;
import com.ebay.services.finding.FindItemsByKeywordsResponse;
import com.ebay.services.finding.FindingServicePortType;
import com.ebay.services.finding.PaginationInput;
import com.ebay.services.finding.SearchItem;

public class FindItem {

    // Basic service call flow:
    // 1. Setup client configuration
    // 2. Create service client
    // 3. Create outbound request and setup request parameters
    // 4. Call the operation on the service client and receive inbound response
    // 5. Handle response accordingly
    // Handle exception accordingly if any of the above steps goes wrong.
    public static  void main(String[] args) {
        try {
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
            request.setKeywords("alienware");
            PaginationInput pi = new PaginationInput();
            pi.setEntriesPerPage(2);
            request.setPaginationInput(pi);
            
            //call service
            FindItemsByKeywordsResponse result = serviceClient.findItemsByKeywords(request);
            
            //output result
            System.out.println("Ack = "+result.getAck());
            System.out.println("Find " + result.getSearchResult().getCount() + " items." );
            List<SearchItem> items = result.getSearchResult().getItem();
            Amount a;
            for(SearchItem item : items) {
            	System.out.println(item.getTitle()+" ... "+item.getViewItemURL());
            	a = item.getSellingStatus().getConvertedCurrentPrice();
            	System.out.println(a.getValue());
            }
            
        } catch (Exception ex) {
        	// handle exception if any
            ex.printStackTrace();
        }
    }
}
