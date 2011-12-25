package com.spider;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-30
 * Time: 10:38:44
 * To change this template use File | Settings | File Templates.
 */
public class ApiEntity {
    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getTuanid() {
        return tuanid;
    }

    public void setTuanid(int tuanid) {
        this.tuanid = tuanid;
    }

    private int tuanid;
    private String Url;
}
