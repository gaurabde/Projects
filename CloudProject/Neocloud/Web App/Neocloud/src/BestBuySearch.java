import java.util.ArrayList;
import java.util.List;

import com.mattwilliamsnyc.service.remix.*;

public class BestBuySearch 
{
	
	public ArrayList<ResultItem> runBestBuySearch (String keyword)
	{
		String API_KEY = "";
		Remix remix = new Remix(API_KEY);
        List<String> productFilters = new ArrayList<String>();
        ArrayList<ResultItem> bestBuyResults = new ArrayList<ResultItem>();
        
        String [] filters = keyword.split(" ");
        StringBuffer filtered_keyword = new StringBuffer();
        
        for(int i=0; i<filters.length;i++)
        {
        	filtered_keyword.append(filters[i]);
        	filtered_keyword.append("%20");
        }
        
        productFilters.add("search="+filtered_keyword);
        
        String search_url = "http://api.remix.bestbuy.com/v1/products(search="+filtered_keyword+")?apiKey="+API_KEY;

        try {
            ProductsResponse response = remix.getProducts(productFilters);

            if(!response.isError()) {
                for(Product product : response.list()) {
                    System.out.println(product.getName() + " : " + product.getSalePrice());
                    System.out.println(product.getUrl());
                    System.out.println(product.getAffiliateUrl());  
                    ResultItem temp = new ResultItem(product.getName(), String.valueOf(product.getSalePrice()), product.getUrl());
                    bestBuyResults.add(temp);
                    if(bestBuyResults.size()==4)
                    	break;
                }
            } else {
                ErrorDocument error = response.getError();
                if(null != error) {
                    System.out.println(error.getStatus());
                    System.out.println(error.getMessage());
                    System.out.println("Examples:");
                    for(String example : error.getExamples()) {
                        System.out.println(example);
                    }
                }
                return null;
            }
        } catch(RemixException e) {
            e.printStackTrace();
            return null;
        }
        return bestBuyResults;
    }
	}

