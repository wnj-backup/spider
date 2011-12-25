package com.spider;

import com.impl.AbstractHost;
import com.impl.ApiHost;
import com.impl.Tmp;
import com.impl.Tuan800Host;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-26
 * Time: 13:04:36
 * To change this template use File | Settings | File Templates.
 */
public class MainService {
    public static void main(String[] args) throws Exception{
        Properties dbProps = new Properties();
        dbProps.load(MainService.class.getResourceAsStream("/datasource.properties"));
        PropertyConfigurator.configure(dbProps);

        KeywordService.load();

        //Tmp.doSomething();

        List<AbstractHost> hosts = new LinkedList<AbstractHost>();
        hosts.add(new ApiHost());
        //hosts.add(new Tuan800Host());

        SpiderNest sn = new SpiderNest(hosts);
        sn.start();
    }
}
