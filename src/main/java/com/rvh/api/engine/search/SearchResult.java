package com.rvh.api.engine.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private List<EventListener> listeners = new ArrayList<>();
    private int state;
    private StringBuilder rawResult;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(EventListener listener){
        listeners.add(listener);
    }

    public void notifyAllObservers(){
        for (EventListener listener : listeners) {
            listener.complete(this);
        }
    }

    public StringBuilder getRawResult() {
        return rawResult;
    }

    public void setRawResult(StringBuilder rawResult) {
        this.rawResult = rawResult;
    }
}
