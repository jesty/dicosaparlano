package com.github.jesty.camera.webservices;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherEvent;
import com.sun.syndication.fetcher.FetcherListener;

public  final class FeedFetcherListener implements FetcherListener {

	private static final String TEST_MODE = "false";

	private final AnalyzerService service;

	private final Map<String, Integer> status;

	/**
	 * To avoid analyzes when FeedFetcher cache mechanism fails
	 */
	private static Set<String> fallBackCache = new HashSet<String>();

	public FeedFetcherListener(AnalyzerService service, Map<String, Integer> status) {
		this.service = service;
		this.status = status;
	}

	@Override
	public void fetcherEvent(FetcherEvent fetcherEvent) {
		final String eventType = fetcherEvent.getEventType();
		if (FetcherEvent.EVENT_TYPE_FEED_POLLED.equals(eventType)) {
			System.err.println("\tEVENT: Feed Polled. URL = " + fetcherEvent.getUrlString());
		} else if (FetcherEvent.EVENT_TYPE_FEED_RETRIEVED.equals(eventType)) {
			System.err.println("\tEVENT: Feed Retrieved. URL = " + fetcherEvent.getUrlString());
			try {
				analyze(fetcherEvent.getFeed());
			} catch (Exception e) {
				System.err.println("Error analyzing " + fetcherEvent.getFeed().getLink());
			}
		} else if (FetcherEvent.EVENT_TYPE_FEED_UNCHANGED.equals(eventType)) {
			System.err.println("\tEVENT: Feed Unchanged. URL = " + fetcherEvent.getUrlString());
		}
	}

	/**
	 * @param feed
	 */
	@SuppressWarnings("unchecked")
	private void analyze(SyndFeed feed) {
		List<SyndEntryImpl> entries = feed.getEntries();
		for (SyndEntryImpl entry : entries) {
			String link = feed.getLink();
			if(fallBackCache.add(link)){
				Integer integer = status.get(link);
				if(integer != null){
					status.put(feed.getLink(), ++integer);
				}
				String uri = entry.getLink();
				service.analyze(uri, TEST_MODE);
			}
			//avoid big size
			if(fallBackCache.size() > 1000){
				fallBackCache.clear();
			}
		}
	}


}