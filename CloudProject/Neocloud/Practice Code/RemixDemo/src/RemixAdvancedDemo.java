import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mattwilliamsnyc.service.remix.*;

public class RemixAdvancedDemo {

    public static void main(String[] args) {
        Remix remix = new Remix("");

        List<String> storeFilters   = new ArrayList<String>();
        List<String> productFilters = new ArrayList<String>();
        
        //storeFilters.add("area(11201,10)");
        productFilters.add("search=canon%20d5200%20camera");

        try {
            ProductsResponse response = remix.getProducts(productFilters);

            if(!response.isError()) {
                for(Product product : response.list()) {
                    System.out.println(product.getName() + " : " + product.getSalePrice());
                    System.out.println(product.getUrl());
                    System.out.println(product.getAffiliateUrl());   
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
            }
        } catch(RemixException e) {
            e.printStackTrace();
        }
    }
}
