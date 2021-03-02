package com.rvh.api.engine.search;

import java.util.HashMap;
import java.util.Map;

public class SearchConfiguration {

    private String searchType;
    private Map<String, ConfigItem> searchCriteriaConfigItems = new HashMap<>();

    public SearchConfiguration(String searchType) {
        this.searchType = searchType;
    }
    public SearchConfiguration(String searchType, Map<String, ConfigItem> searchCriteriaConfigItems) {
        this.searchType = searchType;
        this.searchCriteriaConfigItems = searchCriteriaConfigItems;
    }

    public void addCriterium(ConfigItem criterium) {
        searchCriteriaConfigItems.put(criterium.getName(), criterium);
    }

    public void removeCriterium(ConfigItem criterium) {
        searchCriteriaConfigItems.remove(criterium.getName());
    }

    public void removeCriterium(String criterium) {
        searchCriteriaConfigItems.remove(criterium);
    }

    public Map<String, ConfigItem> getSearchCriteriaConfigItems() {
        return searchCriteriaConfigItems;
    }

    public void setSearchCriteriaConfigItems(Map<String, ConfigItem> searchCriteriaConfigItems) {
        this.searchCriteriaConfigItems = searchCriteriaConfigItems;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchConfiguration{");
        sb.append("searchType='").append(searchType).append('\'');
        sb.append(", searchCriteriaConfigItems=").append(searchCriteriaConfigItems);
        sb.append('}');
        return sb.toString();
    }
}
