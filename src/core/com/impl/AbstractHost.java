package com.impl;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-26
 * Time: 19:39:24
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractHost {
    protected final String hostAddr;
    protected final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);

    public AbstractHost(String hostAddr){
        this.hostAddr = hostAddr;

        webClient.setCssEnabled(false);
        webClient.setJavaScriptEnabled(false);
        webClient.setPopupBlockerEnabled(false);
        webClient.setThrowExceptionOnScriptError(false);
        webClient.setActiveXNative(false);
        webClient.setAppletEnabled(false);
        webClient.setPrintContentOnFailingStatusCode(false);
        webClient.setThrowExceptionOnFailingStatusCode(false);
        webClient.setRedirectEnabled(true);
        webClient.setTimeout(20000);
        webClient.setRefreshHandler(new RefreshHandler(){public void handleRefresh(com.gargoylesoftware.htmlunit.Page page, java.net.URL url, int i) throws java.io.IOException{/***/}});
    }

    public abstract void processHost();
    protected abstract void matchPage(SgmlPage page);
}
