package com.webcrawler.Helper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @Test
    void sortedMap() throws MalformedURLException {
        LinkedHashMap<String, Integer> inner=new LinkedHashMap<>();
        inner.put("first",10);
        inner.put("second",0);
        inner.put("third",0);
        inner.put(";",10);
        LinkedHashMap<String, Integer> SecondInner=new LinkedHashMap<>();
        SecondInner.put("first",20);
        SecondInner.put("second",0);
        SecondInner.put("third",0);
        SecondInner.put(";",20);
        URL url=new URL("https://stackoverflow.com/company");
        URL SecondURL=new URL("https://stackoverflow.com/company");
        HashMap<URL, LinkedHashMap<String, Integer>> value=new HashMap<>();
        value.put(url,inner);
        value.put(SecondURL,SecondInner);
        TreeMap<LinkedHashMap<String, Integer>, URL> actual=Helper.sortedMap(value);
        MapComparator mapComparator=new MapComparator();
        TreeMap<LinkedHashMap<String, Integer>, URL> expected=new TreeMap<>(mapComparator);
        expected.put(inner,url);
        expected.put(SecondInner,SecondURL);
        Assert.assertEquals(actual.get(inner),expected.get(inner));
        Assert.assertEquals(actual.get(SecondInner),expected.get(SecondInner));
    }

    @Test
    void SerializeMap() throws IOException {
        LinkedHashMap<String, Integer> inner=new LinkedHashMap<>();
        inner.put("first",10);
        inner.put("second",0);
        inner.put("third",0);
        inner.put(";",10);
        URL url=new URL("https://stackoverflow.com/company");
        HashMap<URL, LinkedHashMap<String, Integer>> value=new HashMap<>();
        value.put(url,inner);
        Helper.SerializeMap(value,"");
        BufferedReader bw=new BufferedReader(new InputStreamReader(new FileInputStream("AllStats.csv")));
        String actual=bw.readLine();
        String expected="https://stackoverflow.com/company;first;10;second;0;third;0;total;10";
        Assert.assertEquals(actual,expected);

    }
    @Test
    void SerializeTreeMap() throws IOException {
        LinkedHashMap<String, Integer> inner=new LinkedHashMap<>();
        inner.put("first",10);
        inner.put("second",0);
        inner.put("third",0);
        inner.put(";",10);
        URL url=new URL("https://stackoverflow.com/company");
        MapComparator mapComparator=new MapComparator();
        TreeMap<LinkedHashMap<String, Integer>, URL> value=new TreeMap<>(mapComparator);
        value.put(inner,url);
        Helper.SerializeTreeMap(value,"");
        BufferedReader bw=new BufferedReader(new InputStreamReader(new FileInputStream("SortedStats.csv")));
        String actual=bw.readLine();
        String expected="https://stackoverflow.com/company;first;10;second;0;third;0;total;10";
        Assert.assertEquals(actual,expected);
    }

}