package com.webcrawler;

import com.webcrawler.Helper.Helper;
import com.webcrawler.Model.WebSpider;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "StatisticsServlet", value = "/statistics")
public class StatisticsServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response){}
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        URL url=new URL(request.getParameter("url"));
        String termsString=request.getParameter("terms");
        String [] temp=termsString.split(";");
        ArrayList<String> terms=new ArrayList<>(Arrays.asList(temp));
        Integer pageLimit=Integer.parseInt(request.getParameter("pageLimit"));
        Integer levelLimit=Integer.parseInt(request.getParameter("levelLimit"));
        WebSpider spider=new WebSpider(url,terms,pageLimit,levelLimit);
        HashMap<URL, LinkedHashMap<String,Integer>> result=spider.getResult();
        TreeMap<LinkedHashMap<String,Integer>,URL> sortedResult= Helper.sortedMap(result);
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("time", spider.getTime());
        servletContext.setAttribute("result",result);
        servletContext.setAttribute("SortedResult",sortedResult);
        servletContext.setAttribute("termsList",terms);
        Helper.SerializeMap(result,servletContext.getRealPath("/"));
        Helper.SerializeTreeMap(sortedResult,servletContext.getRealPath("/"));
        response.sendRedirect("Information.jsp");
    }

    public void destroy() {
    }
}