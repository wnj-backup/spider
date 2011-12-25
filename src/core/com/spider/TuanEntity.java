package com.spider;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-26
 * Time: 13:12:38
 * To change this template use File | Settings | File Templates.
 */
public class TuanEntity {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJumpAddr() {
        return jumpAddr;
    }

    public void setJumpAddr(String jumpAddr) {
        this.jumpAddr = jumpAddr;
    }

    public String getRealAddr() {
        return realAddr;
    }

    public void setRealAddr(String realAddr) {
        this.realAddr = realAddr;
    }

    public String getApiAddr() {
        return apiAddr;
    }

    public void setApiAddr(String apiAddr) {
        this.apiAddr = apiAddr;
    }

    private int id;
    private String name;
    private String jumpAddr;
    private String realAddr;
    private String apiAddr;    
}
