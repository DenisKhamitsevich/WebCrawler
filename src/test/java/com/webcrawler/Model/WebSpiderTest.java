package com.webcrawler.Model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class WebSpiderTest {

    @Test
    void getResult() throws MalformedURLException {
        ArrayList<String> terms =new ArrayList<>();
        terms.add("work");
        URL url=new URL("https://stackoverflow.com/company");
        WebSpider spider=new WebSpider(url,terms,1,8);
        HashMap<URL, LinkedHashMap<String,Integer>> actual=spider.getResult();
        LinkedHashMap<String,Integer> inner=new LinkedHashMap<>();
        inner.put("work",15);
        inner.put(";",15);
        HashMap<URL, LinkedHashMap<String,Integer>> expected=new HashMap<>();
        expected.put(url,inner);
        Assert.assertEquals(expected,actual);
    }
}