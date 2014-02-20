import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class WalmartSearch 
{
	private static final String WALMART_ACCESS_KEY_ID = "";
	
	ArrayList<ResultItem> runWalmartSearch (String keyword)
	{
		ArrayList<ResultItem> walmartResults;
        String requestUrl = null;
        
        requestUrl = "http://walmartlabs.api.mashery.com/v1/search?query="+keyword+"&format=xml&apiKey="+WALMART_ACCESS_KEY_ID;
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        walmartResults = fetchAndParse(requestUrl);
        return walmartResults;
	}
	
	
	private static ArrayList<ResultItem> fetchAndParse(String requestUrl) {
        try {
        	ArrayList<ResultItem> walmartResults = new ArrayList<ResultItem>();
        	
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            NodeList titleNodes = doc.getElementsByTagName("name");
            NodeList priceNodes = doc.getElementsByTagName("salePrice");
            NodeList urlNodes = doc.getElementsByTagName("productUrl");
            
            int count=0;
            if(titleNodes.getLength()>=4 && priceNodes.getLength()>=4 && urlNodes.getLength()>=4)
            {
            	while(count < 4)
            	{
            		Node title = titleNodes.item(count);
                    Node lowestPrice = priceNodes.item(count);
                    Node url = urlNodes.item(count);
                    System.out.println(title.getTextContent());
                    System.out.println(lowestPrice.getTextContent());
                    System.out.println(url.getTextContent());
                    
                    ResultItem temp = new ResultItem(title.getTextContent(), lowestPrice.getTextContent(), url.getTextContent(), requestUrl);
                    walmartResults.add(temp);
                    count++;
            	}
            	
            }
            
//            title = titleNodes.item(1);
//            lowestPrice = priceNodes.item(3);
//            url = urlNodes.item(1);
//            System.out.println(title.getTextContent());
//            System.out.println(lowestPrice.getTextContent());
//            System.out.println(url.getTextContent());
            
            return walmartResults;
        } catch (Exception e) 
        {
        	return null;            
        }
    }
}

















