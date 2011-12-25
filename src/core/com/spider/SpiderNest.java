package com.spider;

import com.impl.AbstractHost;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-26
 * Time: 19:34:14
 * To change this template use File | Settings | File Templates.
 */
public class SpiderNest {
    private List<AbstractHost> hosts = new LinkedList<AbstractHost>();//主机列表
    private int spiderCount = 1;//蜘蛛数量（线程数量）

    public int getSpiderCount() {
        return spiderCount;
    }

    public void setSpiderCount(int spiderCount) {
        this.spiderCount = spiderCount;
    }

    public List<AbstractHost> getHosts() {
        return hosts;
    }

    public void setHosts(List<AbstractHost> hosts) {
        this.hosts = hosts;
    }    

    //===================begin构造方法======================
    public SpiderNest(){
        
    }

    public SpiderNest(List<AbstractHost> hosts){
        this.hosts = hosts;
    }

    public SpiderNest(int spiderCount){
        this.spiderCount = spiderCount;
    }

    public SpiderNest(List<AbstractHost> hosts, int spiderCount){
        this.hosts = hosts;
        this.spiderCount = spiderCount;
    }
    //===================end构造方法======================

    //释放蜘蛛群
    public void start(){
        for(int i = 0;i < spiderCount;i++){
            new Thread(){
                public void run(){
                    while(true){
                        AbstractHost host;
                        synchronized(hosts){
                            if(hosts.size() == 0){
                                break;
                            }
                            host = hosts.remove(0);
                            host.processHost();
                        }
                    }
                }
            }.start();
        }
    }
}
