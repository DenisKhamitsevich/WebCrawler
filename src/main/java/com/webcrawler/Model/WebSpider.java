package com.webcrawler.Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**WebSpider class collects statistics about the amount of terms on the web pages*/
public class WebSpider {
    /**Stores information about the links, that needs to be viewed by URLThread*/
    private Queue<ValueLink> links;
    /**Stores information about the links, that needs to be viewed by DataThread*/
    private ConcurrentLinkedQueue<URL> dataLinks;
    /**Stores information about websites and the amount of every predefined term on it*/
    private HashMap<URL,LinkedHashMap<String,Integer>> result;
    /**Stores predefined terms*/
    private ArrayList<String> terms;
    /**Max amount of pages, that can be visited*/
    private int pageLimit;
    /**Max level of web page, that can be viewed*/
    private int levelLimit;
    /**Amount of pages, that have been processed by URLThread*/
    private int pageCount=1;
    /**Amount of pages, that have been processed by DataThread*/
    private int information=0;
    /**Amount of terminated DataThreads*/
    private int amount=0;
    /**Start time or the time, that passed from the start of search (based on calling method)*/
    private long time;

    /**Creates a WebSpider object and starts the terms search*/
    public WebSpider(URL url,ArrayList<String> defaultTerms,int customPageLimit,int customLevelLimit) {
        links=new ArrayDeque<>();
        result=new HashMap<>();
        terms=new ArrayList<>(defaultTerms);
        dataLinks=new ConcurrentLinkedQueue();
        pageLimit=customPageLimit;
        levelLimit=customLevelLimit;
        links.add(new ValueLink(url,0));
        dataLinks.add(url);
        time=System.currentTimeMillis();
        for (int i = 0; i <5 ; i++) {
            new URLThread().start();
        }
        for (int i = 0; i <50 ; i++) {
            new DataThread().start();
        }
    }
    /**Helper method for getResult()*/
    private synchronized void getAmount(int item)
    {
        if(item==1)
            notifyAll();
        else
        {
            if(amount<50) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**Returns Hashmap, that contains statistics about the amount of terms on web pages*/
    public HashMap<URL,LinkedHashMap<String,Integer>> getResult() {
        getAmount(0);
        time=System.currentTimeMillis()-time;
        return result;
    }
    /**Returns time in milliseconds, that passed since the beginning of the search*/
    public double getTime()
    {
        return (time/1000d);
    }
    /**URLThread searches for URLs on the page, which is stored in the variable "links", and saves
     * all the found URLs in the "dataLinks" variable*/
    class URLThread extends Thread{
        @Override
        public void run() {
            boolean ind=true;
            while(ind)
            {
                ValueLink entry=null;
                synchronized (links)
                {
                    if(links.peek()!=null)
                        entry=links.poll();
                }
                if(entry!=null)
                {
                    try {
                        int level=entry.getLevel();
                        URL url=entry.getUrl();
                        Document document = Jsoup.connect(url.toString()).get();
                        Elements pageLinks=document.select("a[href]");
                        for(Element element:pageLinks)
                        {
                            if(level==levelLimit)
                            {
                                synchronized ((Integer)pageCount)
                                {
                                    pageCount=pageLimit;
                                }
                            }

                            if(pageCount< pageLimit)
                            {
                                int tempLevel=level+1;
                                String textURL=element.attr("abs:href");
                                if(!textURL.contains("(")&&!textURL.contains(")")&&(textURL!=""))
                                {
                                    URL discoveredURL=new URL(textURL);
                                    if(!result.containsKey(discoveredURL))
                                    {
                                        links.add(new ValueLink(discoveredURL,tempLevel));
                                        dataLinks.add(discoveredURL);
                                        result.put(discoveredURL,null);
                                        synchronized ((Integer)pageCount)
                                        {
                                            pageCount++;

                                        }
                                    }
                                }
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(pageCount>=pageLimit)
                    ind=false;
            }

        }
    }
    /**DataThread searches for the predefined terms on the page, which is stored in the variable "dataLinks",
     * and saves all the statistics in the "result" variable*/
    class DataThread extends Thread{
        @Override
        public void run() {
            boolean ind=true;
            while(ind)
            {
                URL entry=null;
                synchronized (dataLinks)
                {
                    if(dataLinks.peek()!=null)
                        entry=dataLinks.poll();
                }
                if(entry!=null)
                {
                    try {
                        Document document = Jsoup.connect(entry.toString()).get();
                        String textValue=document.body().text();
                        LinkedHashMap<String,Integer> statistics=new LinkedHashMap<>();
                        int total=0;
                        for(String item:terms)
                        {
                            int index=0,res=0;
                            while(textValue.toLowerCase().indexOf(item.toLowerCase(),index)!=-1)
                            {
                                index=textValue.toLowerCase().indexOf(item.toLowerCase(),index)+1;
                                res++;
                            }
                            total=total+res;
                            statistics.put(item,res);
                        }
                        statistics.put(";",total);
                        result.put(entry,statistics);


                    } catch (Exception e) {
                        e.printStackTrace();


                    }
                    finally {
                        synchronized ((Integer)information)
                        {
                            information++;
                        }

                    }
                }
                else
                {
                    if((information>=pageLimit)||(pageCount>=pageLimit))
                        ind=false;
                }

            }
            synchronized ((Integer)(amount))
            {
                amount++;
                if(amount==50)
                    getAmount(1);
            }


        }
    }

}




