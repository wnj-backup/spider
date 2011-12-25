package com.impl;

import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.spider.DataService;
import com.spider.KeywordService;
import com.spider.TuanEntity;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-27
 * Time: 10:33:45
 * To change this template use File | Settings | File Templates.
 */
public class Tuan800Host extends AbstractHost {
    //http://www.tuan800.com/out/jump-to/353

    private static final Logger log = Logger.getLogger(Tuan800Host.class);    
    public Tuan800Host(){
        super("http://www.tuan800.com/out/jump-to/");
    }

    private String src = "";
    private String siteName = "";
    private String nid = "";

    public void processHost(){
        for(int i = 0;i < KeywordService.keywords.size();i++){
            try{
                Map.Entry<String, String> entry = KeywordService.keywords.get(i);
                siteName = entry.getKey();
                nid = entry.getValue();
                log.info("当前站点：" + siteName + "-----------------" + i);
                System.out.println("当前站点：" + siteName + "-----------------" + i);
                SgmlPage page = webClient.getPage(hostAddr + nid);
                src = page.getUrl().toString();
                matchPage(page);
            }catch(Exception e){
                TuanEntity tuan = new TuanEntity();
                tuan.setName(siteName);
                tuan.setJumpAddr(hostAddr + nid);
                DataService.save(tuan);                
                log.info("站点（" + siteName + ")发生异常!");                
                System.out.println("站点（" + siteName + ")发生异常!");
                e.printStackTrace();
            }
        }
        log.info("完成" + hostAddr);
        System.out.println("完成" + hostAddr);
    }

    protected void matchPage(SgmlPage page2){
        if(!(page2 instanceof HtmlPage)){
            return;
        }
        
        HtmlPage page = ((HtmlPage)page2);
        
        String siteTitle = page.getTitleText();
        if(siteTitle.equals("tuan800.com - 跳转")){
            List<?> jumpUrl = page.getByXPath("/html/frameset/frame[1]");
            try{
                src = ((HtmlElement)jumpUrl.get(0)).getAttribute("src").split("&")[1].replaceAll("url=", "");
                page = webClient.getPage(src);
                src = page.getUrl().toString();
                matchPage(page);
            }catch(Exception e){
                TuanEntity tuan = new TuanEntity();
                tuan.setName(siteName);
                tuan.setJumpAddr(hostAddr + nid);
                DataService.save(tuan);
                log.info("跳转页面获取新地址的时候错误了" + src);
                System.out.println("跳转页面获取新地址的时候错误了" + src);
                e.printStackTrace();
            }
        }else{
            List<?> api = page.getByXPath("//a[contains(translate(text(),\"API\",\"api\"),\"api\")]");
            TuanEntity tuan = new TuanEntity();
            tuan.setName(siteName);
            tuan.setJumpAddr(hostAddr + nid);
            tuan.setRealAddr(src);
            for(int i = api.size() - 1;i >= 0;i--){
                try{
                    String s = ((HtmlElement)api.get(i)).getAttribute("href");
                    String apiaddr = tuan.getRealAddr();
                    if(s.indexOf("http") == -1){
                        if(s.substring(0,1).equals("/")){
                            apiaddr = apiaddr.replaceAll("([http|hppts]://[^/]*).*", "$1" + s);
                        }else{
                            apiaddr = apiaddr.replaceAll("([http|hppts]://.*/).*", "$1" + s);
                        }
                    }
                    tuan.setApiAddr(apiaddr);
                }catch(Exception e){
                    DataService.save(tuan);
                    log.info("获取api的时候错误了" + src);
                    System.out.println("获取api的时候错误了" + src);
                }
                break;
            }
            DataService.save(tuan);
        }
    }
}
