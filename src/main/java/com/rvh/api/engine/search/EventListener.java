package com.rvh.api.engine.search;

public abstract class EventListener {

    protected SearchResult searchResult;
    public abstract void complete(SearchResult searchResult);

}
