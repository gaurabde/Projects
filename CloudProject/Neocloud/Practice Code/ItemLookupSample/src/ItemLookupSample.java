
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ItemLookupSample {
    /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private static final String AWS_ACCESS_KEY_ID = "";

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_SECRET_KEY = "";


    private static final String ENDPOINT = "webservices.amazon.com";

   
    private static final String ITEM_ID = "0545010225";

    public static void main(String[] args) {
        /*
         * Set up the signed requests helper 
         */
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        String requestUrl = null;
        String title = null;

        /* The helper can sign requests in two forms - map form and string form */
        
        /*
         * Here is an example in map form, where the request parameters are stored in a map.
         */
        System.out.println("Map form example:");
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2009-03-31");
        params.put("Operation", "ItemSearch");
        params.put("Keywords", "Alienware");
        params.put("SearchIndex", "All");
        params.put("Condition","New");
        params.put("ResponseGroup","ItemAttributes,OfferSummary");
        params.put("AssociateTag","PutYourAssociateTagHere");

        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        title = fetchTitle(requestUrl);
        //System.out.println("Signed Title is \"" + title + "\"");
        System.out.println();

    }

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    private static String fetchTitle(String requestUrl) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            NodeList titleNodes = doc.getElementsByTagName("Title");
            NodeList priceNodes = doc.getElementsByTagName("FormattedPrice");
            NodeList urlNodes = doc.getElementsByTagName("DetailPageURL");
            Node title = titleNodes.item(0);
            Node lowestPrice = priceNodes.item(1);
            Node url = urlNodes.item(0);
            System.out.println(title.getTextContent());
            System.out.println(lowestPrice.getTextContent());
            System.out.println(url.getTextContent());
            
            title = titleNodes.item(1);
            lowestPrice = priceNodes.item(3);
            url = urlNodes.item(1);
            System.out.println(title.getTextContent());
            System.out.println(lowestPrice.getTextContent());
            System.out.println(url.getTextContent());
            
            return title.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
