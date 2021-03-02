package com.rvh.api.engine;

import com.rvh.api.engine.authentication.AuthenticationDetails;
import com.rvh.api.engine.search.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public abstract class Engine {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final CloseableHttpClient httpClient = HttpClients.createDefault();
    protected SearchResult searchResult;
    protected boolean authenticationRequired;
    private String engineName;
    private SearchRequest searchRequest;
    private Map<String, SearchConfiguration> searchConfigurations;
    private AuthenticationDetails authDetails;

    /*
     * Instantiates the com.rvh.api.engine and defines the configuration
     */
    public Engine(String name) {
        this.engineName = name;
        this.searchConfigurations = defineSearchCriteriaConfig();
        setAuthenticationRequired();
    }

    /*
     * Abstract method which requires to be implemented by the developer
     * This method is run after the search criteria is successfully validated
     * Usually this will do all the API calls
     */
    protected abstract boolean process();

    /*
     * Abstract method which requires to be implemented by the developer
     * This method is run during instantiation of the concrete com.rvh.api.engine
     * and defines the search criteria configurations that are allowed to
     * be used by this com.rvh.api.engine. It allows to configure multiple search configurations
     * configured by a search type.
     */
    protected abstract Map<String, SearchConfiguration> defineSearchCriteriaConfig();

    /*
     * Abstract method which requires to be implemented by the developer
     * This method defines if the com.rvh.api.engine requires to follow an authentication
     * process of some sort.
     */
    protected abstract void setAuthenticationRequired();

    public void search() {

        if (searchResult == null) {
            searchResult = new SearchResult();
        }

        if (searchRequest != null && !searchRequest.getSearchType().isEmpty() && !searchRequest.getSearchCriteria().isEmpty()) {
            logger.debug("Search criteria available: {} for request type {}",
                    searchRequest.getSearchCriteria(), searchRequest.getSearchType());
            if (validateSearchCriteria()) {
                logger.debug("Successfully validated search criteria");
                if (process()) {
                    logger.debug("Successfully executed search");
                    searchResult.setState(1);
                } else {
                    logger.debug("Unable to process search");
                    searchResult.setState(0);
                }
            } else {
                logger.debug("Invalid search criteria");
            }
        } else {
            logger.debug("No search criteria provided");
        }

    }

    public void search(SearchRequest searchRequest) {

        setSearchRequest(searchRequest);
        search();

    }

    public String getName() {
        return engineName;
    }

    public AuthenticationDetails getAuthDetails() {
        return authDetails;
    }

    public void setAuthDetails(AuthenticationDetails authDetails) {
        this.authDetails = authDetails;
    }

    /* Validates the search criteria against the search configuration of the com.rvh.api.engine
     */
    private boolean validateSearchCriteria() {

        boolean result = true;

        SearchConfiguration searchTypeToValidate = searchConfigurations.get(searchRequest.getSearchType());

        if (searchTypeToValidate != null) {
            Map<String, ConfigItem> configMap = searchTypeToValidate.getSearchCriteriaConfigItems();

            for (Criterion criterion : searchRequest.getSearchCriteria()) {
                result = validateCriterion(searchTypeToValidate, configMap, criterion);
            }
        } else {
            logger.debug("Non-existing search type used");
            result = false;
        }


        return result;

    }

    private boolean validateCriterion(SearchConfiguration searchTypeToValidate, Map<String, ConfigItem> configMap, Criterion criterion) {

        boolean result = true;

        if (configMap.containsKey(criterion.getName())) {
            //the name is available, now verify if we are allowed to combine it with any other in the current searchCriteria
            Map<String, ConfigItem> configItems = searchTypeToValidate.getSearchCriteriaConfigItems();
            ConfigItem criteriumConfig = configItems.get(criterion.getName());

            if (!validSearchCriteriaCombinations(criteriumConfig)) {
                logger.debug("Invalid search criteria combinations used");
                result = false;
            }

            if (!validSearchOperatorUsed(criteriumConfig, criterion)) {
                logger.debug("Invalid search operator used");
                result = false;
            }

        } else {
            logger.debug("Non-existing search criteria name used");
            result = false;
        }
        return result;
    }

    private boolean validSearchOperatorUsed(ConfigItem configItem, Criterion criterion) {

        List<Operator> allowedList = configItem.getAllowedOperators();
        return allowedList.contains(criterion.getOperator());

    }

    /**
     * for the current criterium get the allowed combinations
     * for every other search criteria check if is available in
     * the allowed list for the current criterium.
     *
     * @param criteriumConfig
     * @return boolean
     * @
     */
    private boolean validSearchCriteriaCombinations(ConfigItem criteriumConfig) {

        List<String> allowedList = criteriumConfig.getAllowedCombinations();

        //use current configitem and loop through all search criteria and ver
        for (Criterion searchCriterion : searchRequest.getSearchCriteria()) {
            if (!criteriumConfig.getName().equals(searchCriterion.getName())
                    && !allowedList.contains(searchCriterion.getName())) {
                logger.debug("Invalid combination detected: '{}' is not allowed to be combined with '{}' !", criteriumConfig.getName(), searchCriterion.getName());
                return false;
            }
        }
        return true;
    }


    public SearchResult getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public SearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(SearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public Map<String, SearchConfiguration> getSearchConfigurations() {
        return searchConfigurations;
    }
}

