import java.util.ArrayList;
import java.util.List;

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
	
	ArrayList<ResultItem> runEbaySearch (String keyword)
	{
		System.out.println("searching for: "+keyword);
		ArrayList<ResultItem> jsp_result = new ArrayList<ResultItem>();
		
		// initialize service end-point configuration
		ClientConfig config = new ClientConfig();
    	// endpoint address can be overwritten here, by default, production address is used,
    	// to enable sandbox endpoint, just uncomment the following line
    	//config.setEndPointAddress("http://svcs.sandbox.ebay.com/services/search/FindingService/v1");
    	config.setApplicationId("AbhijitV-89b3-445e-b001-53c9adb2b8bc");

    	//create a service client
        FindingServicePortType serviceClient = FindingServiceClientFactory.getServiceClient(config);
        
        //create request object
        FindItemsByKeywordsRequest request = new FindItemsByKeywordsRequest();
        //set request parameters
        request.setKeywords(keyword);
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
        for(SearchItem item : items) 
        {
        	System.out.println(item.getTitle()+" ... "+item.getViewItemURL());
        	a = item.getSellingStatus().getConvertedCurrentPrice();
        	System.out.println(a.getValue());
        	
        	ResultItem temp = new ResultItem(item.getTitle(), a.getValue(), item.getViewItemURL());
        	jsp_result.add(temp);
        }
		
		return jsp_result;
	}

}
