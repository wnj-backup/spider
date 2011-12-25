package com.impl;

import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import com.spider.ApiEntity;
import com.spider.DataService;
import com.spider.KeywordService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-30
 * Time: 9:22:02
 * To change this template use File | Settings | File Templates.
 */
public class ApiHost extends AbstractHost{
    private static final Logger log = Logger.getLogger(ApiHost.class);

    public ApiHost(){
        super("");
    }
    private String url = "";
    private String tuanid = "";

    @Override
    public void processHost() {
        for(int i = 0;i < KeywordService.keywords.size();i++ ){
            try{
                Map.Entry<String, String> entry = KeywordService.keywords.get(i);
                tuanid = entry.getKey();
                url = entry.getValue();
                System.out.println("当前站点id：" + tuanid + "-----------------" + (i+1));
                SgmlPage page = webClient.getPage("http://www.xiangjiaotuan.com/help/api.php");
                url = page.getUrl().toString();
                matchPage(page);
            }catch(Exception e){
                System.out.println("错误地址：" + url + "-----------------" + (i+1));
                e.printStackTrace();
            }
        }
        System.out.println("完成!");
    }

    @Override
    protected void matchPage(SgmlPage page) {
        try{
            ApiEntity api = new ApiEntity();
            //api.setTuanid(Integer.parseInt(tuanid));
            //打开后直接就是xml文件的
            if(page instanceof XmlPage){
                api.setUrl(page.getUrl().toString());
                DataService.save2(api);
            }else if(page instanceof HtmlPage){
                List<?> anchors = ((HtmlPage)page).getByXPath("//a[contains(translate(text(),\"API\",\"api\"),\"api\")]|//A[contains(text(),\"接口\")]");
                //List<?> anchors = ((HtmlPage)page).getByXPath("//*[contains(translate(text(),\"API\",\"api\"),\"api\")]|//A[contains(text(),\"接口\")]");
                String sss = ((HtmlPage)page).asText();
                String bbb = ((HtmlPage)page).asXml();
                List<String> links = new ArrayList<String>();
                for(Object o:anchors){
                    HtmlElement e = (HtmlElement)o;
                    String s = e.getAttribute("href");
                    //String s = e.asText();
                    if(s.indexOf("http") == -1){
                        if(s.substring(0,1).equals("/")){
                            s = url.replaceAll("([http|hppts]://[^/]*).*", "$1" + s);
                        }else{
                            s = url.replaceAll("([http|hppts]://.*/).*", "$1" + s);
                        }
                    }
                    if(!url.equals(s)){//排除当前页面
                        links.add(s);
                    }
                }
                if(links.size() != 0){//找到满足条件的链接
                    for(String link:links){
                        api.setUrl(link);
                        DataService.save2(api);
                    }
                }
//                else{//如果什么都找不到，尝试最坏的打算，api可能存在毛文本中，而不是链接中
//                    List<?> elements = ((HtmlPage)page).getByXPath("//*[contains(translate(text(),\"API\",\"api\"),\"api\")]");
//                    int a;
//                    a=0;
//                }
            }
        }catch(Exception e){
            System.out.println("match的时候错误：" + url);
            e.printStackTrace();
        }
    }
}
