import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class AmazonSearch 
{
	private static final String AWS_ACCESS_KEY_ID = "";
	private static final String AWS_SECRET_KEY = "";
	private static final String ENDPOINT = "webservices.amazon.com";
	
	ArrayList<ResultItem> runAmazonSearch (String keyword)
	{
		ArrayList<ResultItem> amazonResults;
		
		AmazonSignedRequestsHelper helper;
        try {
            helper = AmazonSignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        String requestUrl = null;
        
        /*
         * Request parameters are stored in a map.
         */
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2009-03-31");
        params.put("Operation", "ItemSearch");
        params.put("Keywords", keyword);
        params.put("SearchIndex", "All");
        params.put("Condition","New");
        params.put("ResponseGroup","ItemAttributes,OfferSummary");
        params.put("AssociateTag","PutYourAssociateTagHere");

        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        amazonResults = fetchAndParse(requestUrl);
        return amazonResults;
	}
	
	
	private static ArrayList<ResultItem> fetchAndParse(String requestUrl) {
        try {
        	ArrayList<ResultItem> amazonResults = new ArrayList<ResultItem>();
        	
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            NodeList titleNodes = doc.getElementsByTagName("Title");
            NodeList priceNodes = doc.getElementsByTagName("FormattedPrice");
            NodeList urlNodes = doc.getElementsByTagName("DetailPageURL");
            if(titleNodes.getLength()>=2 && priceNodes.getLength()>=2 && urlNodes.getLength()>=2)
            {
            	int i=0;
            	while(i<=3){
            	Node title = titleNodes.item(i);
                Node lowestPrice = priceNodes.item(i);
                Node url = urlNodes.item(i);
                ResultItem temp = new ResultItem(title.getTextContent(), lowestPrice.getTextContent(), url.getTextContent(), requestUrl);
                amazonResults.add(temp);
                System.out.println(title.getTextContent());
                System.out.println(lowestPrice.getTextContent());
                System.out.println(url.getTextContent());
                i++;
            	} 
               
               
            }
            
//            title = titleNodes.item(1);
//            lowestPrice = priceNodes.item(3);
//            url = urlNodes.item(1);
//            System.out.println(title.getTextContent());
//            System.out.println(lowestPrice.getTextContent());
//            System.out.println(url.getTextContent());
            
            return amazonResults;
        } catch (Exception e) 
        {
        	return null;            
        }
    }
}

















