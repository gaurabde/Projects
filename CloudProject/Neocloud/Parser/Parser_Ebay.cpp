#include<iostream>
#include<fstream>
#include<sstream>
#include<unistd.h>
#include<sys/stat.h>
#include<sys/types.h>
#include<stdio.h>
#include<curl/curl.h>
#include<tr1/unordered_map>
#include<algorithm>
#include<functional>
#include<cctype>
#include<locale>

using namespace std;

//Method Declarations

void parseProductsName(string, tr1::unordered_map<int,string>&);             //parses products name from web page in first argument and
                                                                             //stores location,name in the hash table passed in second argument

void parseProductsPrice(string, tr1::unordered_map<int,string>&);            //parses products price from web page in first argument and
                                                                             //stores location,price in the hash table passed in second argument

void associateProductNameAndPrice(tr1::unordered_map<int,string>& , tr1::unordered_map<int,string>&, tr1::unordered_map<string,string>&);
                                                                             //associates the product names and prices parsed and stored in
                                                                             //hash tables in the first argument and second argument and stores
                                                                             //the association in the hash table in the third argument

string getHTMLSource(string);                                                //Gets the HTML page of the URL and saves in the argument string

size_t curl_to_string(void *ptr, size_t size, size_t nmemb, void *data);     //Fetches the url page

                                                                             // trim from start
static inline std::string &ltrim(std::string &s) {
        s.erase(s.begin(), std::find_if(s.begin(), s.end(), std::not1(std::ptr_fun<int, int>(std::isspace))));
        return s;
}

                                                                             // trim from end
static inline std::string &rtrim(std::string &s) {
        s.erase(std::find_if(s.rbegin(), s.rend(), std::not1(std::ptr_fun<int, int>(std::isspace))).base(), s.end());
        return s;
}

                                                                             // trim from both ends
static inline std::string &trim(std::string &s) {
        return ltrim(rtrim(s));
}

int main (int argc, char *argv[])                                            //main method
{
   if((new string(argv[1]))->compare(*(new string("fetch"))) == 0)           //fetch mode. Fetches the pages for a list of url's and parses
     {
       ifstream ifs;
       ofstream ofs;
       //string pathToReadFile = "crawledData_EBay/linkdb/links";
       string pathToReadFile = "link";
       string pathToWriteFile = "results/";
       mkdir(pathToWriteFile.c_str(), S_IRWXU);

       ifs.open(pathToReadFile.c_str(), std::fstream::in);
       ofs.open((pathToWriteFile + "productDetails_Ebay").c_str(), std::fstream::out | std::fstream::app);

       char line[1024];
       int count = 0;

       while( ifs.good() )
         {
           ifs.getline(line, 1024);                      //reads a block of 1025 characters in loop
           string lineS = line;
           istringstream ss(lineS);
           string token;

           while( (ss >> token) )
             {
              if( (token.size() > 7) && (token.substr(0, 7)=="http://") )  //fetch mode for links by identifying http:// start
               {
                 string page = getHTMLSource(token);                       //gets the page of URL
                 tr1::unordered_map<int,string> productList;
        	 parseProductsName(page,productList);                      //parses name
	         tr1::unordered_map<int,string> productPrice;
        	 parseProductsPrice(page,productPrice);                    //parses price
	         tr1::unordered_map<string, string> association;
        	 associateProductNameAndPrice(productList, productPrice, association);          //associates name and price
	         tr1::unordered_map<string, string>::iterator stritr = association.begin();
        	 tr1::unordered_map<string, string>::iterator enditr = association.end();

	         while(stritr != enditr)
        	   {
                     //outputs to file the parsed details
	             ofs<<"Product Name::::"<<stritr->first<<"::::Product Price::::"<<stritr->second<<"::::URL::::"<<token<<"::::Store::::Ebay"<<endl;
	             stritr++;
	           }
                 count = count + 1;
                 cout<<"LINK NO. "<<count<<endl;       //number of links parsed
               }
              else if(token == "fromUrl:")                                 //fetch mode for links by identifying fromUrl
                {
                  if(ss >> token)
                   {
                     string page = getHTMLSource(token);                   //gets the page of URL
	             tr1::unordered_map<int,string> productList;
               	     parseProductsName(page,productList);                  //parses name
	             tr1::unordered_map<int,string> productPrice;
	             parseProductsPrice(page,productPrice);                //parses price
	             tr1::unordered_map<string, string> association;
	             associateProductNameAndPrice(productList, productPrice, association);     //associates name and price
	             tr1::unordered_map<string, string>::iterator stritr = association.begin();
	             tr1::unordered_map<string, string>::iterator enditr = association.end();

                     while(stritr != enditr)
                       {
                         //outputs to file the parsed details
                         ofs<<"Product Name::::"<<stritr->first<<"::::Product Price::::"<<stritr->second<<"::::URL::::"<<token<<"::::Store::::Ebay"<<endl;
                         stritr++;
                       }
                     count = count + 1;
                     cout<<"LINK NO. "<<count<<endl;    //number of links parsed
                   }
                }
             }
         }
         /*                                             //Debug code
         string page = getHTMLSource("http://www.ebay.com/sch/Jeans-/11483/i.html?");
         tr1::unordered_map<int,string> productList;
	 parseProductsName(page,productList);
	 tr1::unordered_map<int,string> productPrice;
	 parseProductsPrice(page,productPrice);
	 tr1::unordered_map<string, string> association;
	 associateProductNameAndPrice(productList, productPrice, association);
	 tr1::unordered_map<string, string>::iterator stritr = association.begin();
	 tr1::unordered_map<string, string>::iterator enditr = association.end();

	 while(stritr != enditr)
	   {
             cout<<"Product Name: "<<stritr->first<<" Product Price: "<<stritr->second<<endl;
             stritr++;
           }
         */
         ofs.close();
         return 0;
     }
     else if((new string(argv[1]))->compare(*(new string("nofetch"))) == 0)     //no fetch mode, parses crawled pages
       {
          ifstream ifs;
          ofstream ofs;
          string pathToReadFile = "crawledData_EBay/seg/dump";                  //crawled pages
          string pathToWriteFile = "results/";
          mkdir(pathToWriteFile.c_str(), S_IRWXU);

          ifs.open(pathToReadFile.c_str(), std::fstream::in);
          ofs.open((pathToWriteFile + "productDetails_EBay").c_str(), std::fstream::out | std::fstream::app);

          int count = 0;

          int read=0;
          ifs.seekg (0, ifs.end);
          int length = ifs.tellg();
          ifs.seekg (0, ifs.beg);

          while( ifs.good() && read < length)
           {
             char * buffer = new char [102400];
             ifs.read(buffer, 102400);
             string page = buffer;

             tr1::unordered_map<int,string> productList;
             parseProductsName(page,productList);                                //parses product name
             tr1::unordered_map<int,string> productPrice;
             parseProductsPrice(page,productPrice);                              //parses product price
             tr1::unordered_map<string, string> association;
             associateProductNameAndPrice(productList, productPrice, association);    //associates name and price
             tr1::unordered_map<string, string>::iterator stritr = association.begin();
             tr1::unordered_map<string, string>::iterator enditr = association.end();

             while(stritr != enditr)
               {
                 //outputs to file name and price
                 ofs<<"Product Name::::"<<stritr->first<<"::::Product Price::::"<<stritr->second<<"::::Store::::Ebay"<<endl;
                 stritr++;
               }

             read = read + 102400;
           }

          ofs.close();
          return 0;
       }
}


void parseProductsName(string page, tr1::unordered_map<int,string> &productList)
{

    int lineNumber = 1;
    int i = 0;
    int size = page.size();

    while(i < size)
      {
          while(i < size && page[i] != '<')                //ignore characters until a '<'
            {
              if(page[i] == '\n')
                lineNumber++;
              i++;
            }

          i++;
          string link = "<";
          if(i < size && page[i] == 'a')                  //if it is an anchor tag
            {
                //copy in line until end of anchor tag
                while(i+3< size && (page[i] != '<' || page[i+1] != '/' || page[i+2] != 'a' || page[i+3] != '>' ) )
                  {
                    link = link + page[i];
                    if(page[i] == '\n')
                      lineNumber++;
                    i++;
                  }

                size_t pos = link.find("itemprop=\"name\"");        //if the anchor tag has attribute itemprop=name
                if (pos != string::npos)
                   {
                      pos = link.find_last_of(">");
                      if (pos != string::npos)
                         {
                           stringstream links(link);
                           string token;
                           links >> token;
                           links >> token;
                           string url = "";
                           if(token.substr(0,4) == "href")         //link for the product
                             url = token.substr(4);

                           int index = pos + 1;
                           string productItem = link.substr(index);        //product's name
                           productItem = productItem + "::::URL::::" + url;
                           productItem = trim(productItem);
                           //store location and product name|Link in hash table
                           pair<tr1::unordered_map<int,string>::iterator, bool> res = productList.insert(make_pair<int,string> (lineNumber, productItem));
                           if(res.second == false)
                             {
                              //cout<<"At line number "<<lineNumber<<" entry already exist "<<endl;
                              //cout<<"name is: "<<productItem<<endl;
                             }
                         }
                      else
                         {
                           cout<<"No product here "<<endl;
                         }
                   }

                i = i+3;
            }
      }//end while


    /*   Debug Code
    tr1::unordered_map<int,string>::iterator stritr = productList.begin();
    tr1::unordered_map<int,string>::iterator enditr = productList.end();
    while(stritr != enditr)
      {
        cout<<"Line number is: "<<stritr->first<<" Product is: "<<stritr->second<<endl;
        stritr++;
      }
    */

}

void parseProductsPrice(string page, tr1::unordered_map<int,string> &productPrice)
{

    int lineNumber = 1;
    int i = 0;
    int size = page.size();

    while(i < size)
      {
          while(i < size && page[i] != '<')              //ignore characters until a '<'
            {
              if(page[i] == '\n')
                lineNumber++;
              i++;
            }

          i++;
          string link = "<";

          //if it is a span or div tag
          if(i+3 < size && ( (page[i] == 's' && page[i+1] == 'p' && page[i+2] == 'a' && page[i+3] == 'n') || (page[i] == 'd' && page[i+1] == 'i' && page[i+2] == 'v' ) ))
            {
                //copy while end of tag
                while(i+1< size && (page[i] != '<' || page[i+1] != '/') )
                  {
                    link = link + page[i];
                    if(page[i] == '\n')
                      lineNumber++;
                    i++;
                  }

                size_t pos = link.find("itemprop=\"price\"");       //if the div or span tag has attribute itemprop=price
                if (pos != string::npos)
                   {
                      pos = link.find_last_of(">");
                      if (pos != string::npos)
                         {
                           int index = pos + 1;
                           string priceItem = link.substr(index);
                           priceItem=trim(priceItem);                     //product's price
                           pair<tr1::unordered_map<int,string>::iterator, bool> res = productPrice.insert(make_pair<int,string> (lineNumber, priceItem));
                           if(res.second == false)
                              { //cout<<"At line number "<<lineNumber<<" entry already exist "<<endl;
                              }
                         }
                      else
                         {
                           cout<<"No product here "<<endl;
                         }
                   }

                i = i+2;
            }
      }//end while

    /*    debug code
    tr1::unordered_map<int,string>::iterator stritr = productPrice.begin();
    tr1::unordered_map<int,string>::iterator enditr = productPrice.end();
    while(stritr != enditr)
      {
        stritr++;
      }
    */

}

void associateProductNameAndPrice(tr1::unordered_map<int,string>& productName, tr1::unordered_map<int,string>& productPrice, tr1::unordered_map<string, string>& association)
{
  tr1::unordered_map<int, string>::iterator stritr = productName.begin();
  tr1::unordered_map<int, string>::iterator enditr = productName.end();

  while(stritr != enditr)
    {
       int lineNumber = stritr->first;
       string productName = stritr->second;

       bool foundMatch = false;

       int searchDistance = 100;                //search neighbourhood limit
       int forwardSearch = 0;                   //forward search counter
       int backwardSearch = 0;                  //backward search counter

       while(!foundMatch && forwardSearch<searchDistance)     //find association in forward distance until not found one
         {
           tr1::unordered_map<int, string>::iterator find = productPrice.find(lineNumber+forwardSearch);
           if(find != productPrice.end())
             {
                association.insert(make_pair<string,string> (productName, find->second));    //store association if found
                foundMatch = true;
             }
           else
             forwardSearch++;
         }

       while(!foundMatch && backwardSearch<searchDistance)   //find association in backward distance until not found one
         {
           tr1::unordered_map<int, string>::iterator find = productPrice.find(lineNumber-backwardSearch);
           if(find != productPrice.end())
             {
                association.insert(make_pair<string,string> (productName, find->second));     //store association if found
                foundMatch = true;
             }
           else
             backwardSearch++;
         }


       if(foundMatch == false)
          association.insert(make_pair<string,string> (productName, "EMPTY"));    //store empty association if not found

       stritr++;
    }
}


string getHTMLSource(string url)     //get web page for a url using curl library
{
  CURL *curl;
  CURLcode res;
  string urlN;
  string pagedata;
  ofstream ofs("pages.txt", std::fstream::out | std::fstream::app);

  /* if( url.substr(0, 7) == "http://" )
    {
      int size = url.size();
      urlN = url.substr(7,size-7);
    }
  else if(url.substr(0, 8) == "https://")
    {
      int size = url.size();
      urlN = url.substr(8, size-8);
    }
*/

  curl = curl_easy_init();
  if(curl) {   //set configurations
    curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
    curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);
    curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, curl_to_string);
    curl_easy_setopt(curl, CURLOPT_WRITEDATA, &pagedata);

    res = curl_easy_perform(curl);

    if(res != CURLE_OK)    //failed
        fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
    else
      {                    //return page
        curl_easy_cleanup(curl);
        ofs.write(pagedata.c_str(), pagedata.size());
        ofs.close();
        return pagedata;
      }
  }
    curl_easy_cleanup(curl);
    ofs.close();
    return " ";
}

size_t curl_to_string(void *ptr, size_t size, size_t nmemb, void *data)     //converts character bytes into strings
{
    std::string *str = (std::string *) data;
    char *sptr = (char *) ptr;
    int x;
    for(x = 0; x < size * nmemb; ++x)
    {
        (*str) += sptr[x];
    }

    return size * nmemb;
}
