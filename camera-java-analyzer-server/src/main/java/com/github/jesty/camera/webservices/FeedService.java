package com.github.jesty.camera.webservices;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.DiskFeedInfoCache;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

@Controller
public class FeedService {

	@Autowired
	private AnalyzerService service;
	
	private Map<String, Integer> status = new HashMap<String, Integer>();

	private String[] urls = {"http://news.centrodiascolto.it/rss.xml", "http://ansa.feedsportal.com/c/34225/f/621689/index.rss", "http://www.repubblica.it/rss/homepage/rss2.0.xml", "http://www.ilfattoquotidiano.it/feed/", "http://www.ilgiornale.it/feed.xml"};

	private FeedFetcher feedFetcher;
	
	@RequestMapping("/feed/start")
	public void init() throws MalformedURLException, IOException, FeedException, FetcherException {
		if(feedFetcher == null){
			String homeFolder = selectHomeFolder();
			FeedFetcherCache feedInfoCache = new DiskFeedInfoCache(homeFolder);
			feedFetcher = new HttpURLFeedFetcher(feedInfoCache);
			feedFetcher.addFetcherEventListener(new FeedFetcherListener(service, status));
			for (String url : urls) {
				status.put(url, 0);
			}
		}
	}

	private String selectHomeFolder() {
		String openshiftHomePath = "/var/lib/openshift/537683bb4382ec2efc00035c/app-root/data";
		File openshiftHome = new File(openshiftHomePath);
		String homeFolder;
		if(openshiftHome.exists()){
			homeFolder = openshiftHomePath;
		} else {
			homeFolder = System.getProperty("user.home");
		}
		return homeFolder;
	}

	@RequestMapping("/feed/status")
	public @ResponseBody Map<String, Integer> status()  {
		return status;
	}

	@Scheduled(fixedDelay=300000)
	@RequestMapping("/feed/throwsread")
	public @ResponseBody void scheduledRead() throws IllegalArgumentException, MalformedURLException, IOException, FeedException, FetcherException{
		if(feedFetcher != null){
			for (String url : urls) {
				feedFetcher.retrieveFeed(new URL(url));
			}
		}
	}


}
