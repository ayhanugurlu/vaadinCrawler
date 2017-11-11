package com.au.example.service;

import java.io.IOException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;


import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;


/**
 * Created by ayhanugurlu on 1/4/17.
 */
@Service
public class CrawlerImpl implements ICrawler {

    /**
     * @param url
     */
    @Async
    public Future<String> scan(String url,String tag,String tagAttribute) {
        String result = "";


    	 try {

             Connection connection = Jsoup.connect(url); // connect url with jsoup

             Document doc = connection.get();   //download document from connection
             Elements elements = doc.select(tag); //get links in document


             for(Element element : elements){
                 System.out.println(element);
             }


             
             /*add each url to the list*/

             /*
             for (Element link : linksOnPage) {

                 String hrefStr = link.absUrl(tagAttribute);
                 if (!hrefStr.isEmpty()) {

                     result+= hrefStr+'\n';
                     System.out.println(hrefStr);
                 }
             }
             */

    	  } catch (IOException ex) {
              ex.printStackTrace();
          }
        return new AsyncResult<>(result);

    }
}