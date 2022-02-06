package com.webcrawler.Model;

import java.net.URL;

/**ValueLink class stores an information about URL and its depth level*/
public class ValueLink {
    /**URL of website*/
    private URL url;
    /**Depth level of website*/
    private Integer level;
    public ValueLink(URL customURL,Integer customLevel)
    {
        url=customURL;
        level=customLevel;
    }

    public URL getUrl() {
        return url;
    }

    public Integer getLevel() {
        return level;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
