package com.rvh.api.engine.search;

import java.util.List;

public class SearchRequest extends SearchResult  {

    private String useEngine;
    private String searchType;
    private List<Criterion> searchCriteria;

    private SearchRequest() {
        //force usage of parameterized constructor
    }

    public SearchRequest(String useEngine, String searchType, List<Criterion> searchCriteria) {
        this.useEngine = useEngine;
        this.searchCriteria = searchCriteria;
        this.searchType = searchType;
    }

    public String getUseEngine() {
        return useEngine;
    }

    public List<Criterion> getSearchCriteria() {
        return searchCriteria;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchRequest{");
        sb.append("useEngine='").append(useEngine).append('\'');
        sb.append(", searchType='").append(searchType).append('\'');
        sb.append(", searchCriteria=").append(searchCriteria);
        sb.append('}');
        return sb.toString();
    }
}
