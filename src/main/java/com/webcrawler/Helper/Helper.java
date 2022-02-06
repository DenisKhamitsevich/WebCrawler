package com.webcrawler.Helper;

import java.io.*;
import java.net.URL;
import java.util.*;

/**Class, that have methods to convert HashMap into TreeMap, serialize HashMap into the CSV file and
 * serialize TreeMap into the CSV file*/
public class Helper {

    /**Converts HashMap into TreeMap and returns it */
    public static TreeMap<LinkedHashMap<String, Integer>, URL> sortedMap(HashMap<URL, LinkedHashMap<String, Integer>> value) {
        MapComparator Mapcomparator = new MapComparator();
        TreeMap<LinkedHashMap<String, Integer>, URL> tree = new TreeMap<>(Mapcomparator);
        for (Map.Entry<URL, LinkedHashMap<String, Integer>> entry : value.entrySet()) {
            tree.put(entry.getValue(), entry.getKey());
        }
        TreeMap<LinkedHashMap<String, Integer>, URL> result=new TreeMap<>(Mapcomparator);
        int amount=0;
        for(Map.Entry<LinkedHashMap<String, Integer>, URL> entry:tree.entrySet())
        {
            if(amount>=10)
                break;
            result.put(entry.getKey(),entry.getValue());
            amount++;
        }
        return result;
    }
    /**Serializes HashMap into the CSV file*/
    public static void SerializeMap(HashMap<URL, LinkedHashMap<String, Integer>> value,String path) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"AllStats.csv"), "UTF-8"));
        for (Map.Entry<URL, LinkedHashMap<String, Integer>> entry : value.entrySet()) {
            StringBuffer line = new StringBuffer();
            line.append(entry.getKey().toString());
            if(entry.getValue()!=null)
            {
                for (Map.Entry<String, Integer> secondEntry : entry.getValue().entrySet())
                {
                    String key=secondEntry.getKey();
                    if(key==";")
                        key="total";
                    line.append(";");
                    line.append(key);
                    line.append(";");
                    line.append(secondEntry.getValue());
                }
            }
            else
            {
                line.append(";");
                line.append("ConnectionError");
            }
            bw.write(line.toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }
    /**Serializes TreeMap into the CSV file*/
    public static void SerializeTreeMap(TreeMap<LinkedHashMap<String, Integer>, URL> value,String path) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+"SortedStats.csv"), "UTF-8"));
        for (Map.Entry<LinkedHashMap<String, Integer>,URL> entry : value.entrySet()) {
            StringBuffer line = new StringBuffer();
            line.append(entry.getValue().toString());
            if(entry.getKey()!=null)
            {
                for (Map.Entry<String, Integer> secondEntry : entry.getKey().entrySet())
                {
                    String key=secondEntry.getKey();
                    if(key==";")
                        key="total";
                    line.append(";");
                    line.append(key);
                    line.append(";");
                    line.append(secondEntry.getValue());
                }
            }
            else
            {
                line.append(";");
                line.append("ConnectionError");
            }


            bw.write(line.toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }


}

/**Custom comparator for LinkedHashMap, which is comparing two LinkedHashMaps according to the total amount
 * of terms in them*/
class MapComparator implements Comparator<LinkedHashMap<String,Integer>>
{

    @Override
    public int compare(LinkedHashMap<String, Integer> o1, LinkedHashMap<String, Integer> o2) {
        int firstTotal=0;
        int secondTotal=0;
        if(o1!=null)
            firstTotal=o1.get(";");
        if(o2!=null)
            secondTotal=o2.get(";");
        if(firstTotal<secondTotal)
            return 1;
        else
            return -1;

    }
}

