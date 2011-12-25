package com.impl;

import com.spider.DataService;
import com.spider.KeywordService;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-30
 * Time: 15:35:57
 * To change this template use File | Settings | File Templates.
 */
public class Tmp {
    public static void doSomething(){
        String cityName = "";
        String siteName = "";
        for(int i = 0;i < KeywordService.keywords.size();i++ ){
            try{
                Map.Entry<String, String> entry = KeywordService.keywords.get(i);
                cityName = entry.getKey();
                siteName = entry.getValue();
                String[] keyArray = siteName.split(",");
                int cityid = 0;
                cityid = DataService.getCityId(cityName);
                if(cityid <= 0){
                    System.out.println("城市找不到:" + cityName);
                    break;
                }
                for(String s:keyArray){
                    System.out.println("当前：" + cityName + "---------" + s + "-----" + (i+1));
                    int n = DataService.save3(cityid, s);
                    if(n <= 0){
                        System.out.println(cityName + "---" + s + "失败");
                    }
                }
            }catch(Exception e){
                System.out.println("错误行:" + (i+1));
                e.printStackTrace();
            }
        }
        System.out.println("搞定!");
    }
}
