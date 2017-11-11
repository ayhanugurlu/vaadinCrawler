package com.au.example.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ayhanugurlu on 2/2/17.
 */
@Service
public class PageSourceGetterImpl implements IPageSourceGetter {
    @Override
    public String getPageSource(String urlString) {
        String result = null;
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);



            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
             result = sb.toString();

            System.out.println(result);

        }catch (IOException e){
            e.printStackTrace();
        }



        return result;
    }
}
