package com.rvh.api.engine;

import com.rvh.api.engine.search.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

class EngineTest {

    private TestEngine testEngine = new TestEngine("TestEngine");

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    Define an testEngine class and implement the abstract Engine methods which
    will be used to test the base functionality of the Engine
     */
    static class TestEngine extends Engine {

        public TestEngine(String name) {
            super(name);
        }

        @Override
        protected boolean process() {
            this.getSearchRequest().setState(1);
            return true;
        }

        @Override
        public Map<String, SearchConfiguration> defineSearchCriteriaConfig() {

            Map<String, SearchConfiguration> searchConfigurations = new HashMap<>();

            // Create a search criteria configuration item named 'Name' which can be used
            // with the AND and STARTS_WITH operator
            List<Operator> operators = Arrays.asList(Operator.AND, Operator.STARTS_WITH);
            ConfigItem nameConfig = new ConfigItem("Name", "name", operators, true, UiControlType.STRING);

            // Create a search criteria configuration item named 'description' which can be used
            // with the AND operator
            operators = Arrays.asList(Operator.AND);
            ConfigItem descriptionConfig = new ConfigItem("Description", "description", operators, true, UiControlType.STRING);

            // Create a search criteria configuration item named 'issue' which can be used
            // with the AND operator
            operators = Arrays.asList(Operator.AND);
            ConfigItem issueConfig = new ConfigItem("Issue", "issue", operators, true, UiControlType.STRING);

            // Define which search criteria items can be combined with each other.
            nameConfig.setAllowedCombinations(Arrays.asList(descriptionConfig.getName(), issueConfig.getName()));
            descriptionConfig.setAllowedCombinations(Arrays.asList(nameConfig.getName()));
            issueConfig.setAllowedCombinations(Arrays.asList(nameConfig.getName()));

            // Add the search configuration items to a configuration object
            SearchConfiguration testSearchConfiguration = new SearchConfiguration("test");
            testSearchConfiguration.addCriterium(nameConfig);
            testSearchConfiguration.addCriterium(descriptionConfig);
            testSearchConfiguration.addCriterium(issueConfig);

            searchConfigurations.put(testSearchConfiguration.getSearchType(), testSearchConfiguration);

            return searchConfigurations;

        }

        @Override
        protected void setAuthenticationRequired() {

        }

    }

    @Test
    void testEngine() {

        List<Criterion> criteria = new ArrayList<>();
        Criterion criterion = new Criterion("name", "teststring", Operator.AND, false);
        Criterion criterion2 = new Criterion("issue", "teststring", Operator.AND, false);

        criteria.add(criterion);
        criteria.add(criterion2);
        testEngine.setSearchRequest(new SearchRequest("TestEngine", "test", criteria));
        testEngine.search();

        logger.debug("SearchResult is {} for searchRequest {} ", testEngine.getSearchResult().getState(), testEngine.getSearchRequest().toString());

    }

    @Test
    void testSearchCriteriaConfigSuccess() {

        List<Criterion> criteria = new ArrayList<>();
        Criterion criterion = new Criterion("name", "teststring", Operator.AND, false);
        Criterion criterion2 = new Criterion("issue", "teststring", Operator.AND, false);

        criteria.add(criterion);
        criteria.add(criterion2);

        testEngine.setSearchRequest(new SearchRequest("TestEngine", "test", criteria));
        SearchResult searchResult = new SearchResult();
        testEngine.setSearchResult(searchResult);
        testEngine.search();
        Assertions.assertEquals(1, searchResult.getState() );

    }

    /**
     * Intentionally provide non-allowed operators.
     * It should return false for test to succeed.
     */
    @Test
    void testSearchCriteriaConfigFailOnOperator() {

        List<Criterion> criteria = new ArrayList<>();
        Criterion criterion = new Criterion("name", "teststring", Operator.EXACT, false);
        Criterion criterion2 = new Criterion("issue", "teststring", Operator.ALL_WORDS, false);

        criteria.add(criterion);
        criteria.add(criterion2);

        testEngine.setSearchRequest(new SearchRequest("TestEngine", "test", criteria));
        SearchResult searchResult = new SearchResult();
        testEngine.setSearchResult(searchResult);
        testEngine.search();

        Assertions.assertEquals(0, searchResult.getState());

    }

    @Test
    void testSearchCriteriaConfigFailOnCombination() {

        List<Criterion> criteria = new ArrayList<>();
        Criterion criterion = new Criterion("description", "teststring", Operator.ALL_WORDS, false);
        Criterion criterion2 = new Criterion("issue", "teststring", Operator.ALL_WORDS, false);

        criteria.add(criterion);
        criteria.add(criterion2);

        testEngine.setSearchRequest(new SearchRequest("TestEngine", "test", criteria));
        SearchResult searchResult = new SearchResult();
        testEngine.setSearchResult(searchResult);
        testEngine.search();

        Assertions.assertEquals( 0, searchResult.getState());

    }

//    @Test
//    void testSearchService() throws TransformerException {
//
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//
//        InputStream xslt = classloader.getResourceAsStream("comicvine/volumes.xslt");
//
//        List<Criterion> criteria = new ArrayList<>();
//        Criterion criterion = new Criterion("query", "The walking dead", Operator.ALL_WORDS, false);
//        Criterion criterion2 = new Criterion("resources", "issue", Operator.ALL_WORDS, false);
//        criteria.add(criterion);
//        criteria.add(criterion2);
//        SearchRequest searchRequest = new SearchRequest("ComicVine", "search", criteria);
//        SearchRequest result1 = searchService.search(searchRequest);
//
//        ResponseCollection<?> collection = getCollection("VOLUME", xslt, result1.getRawResult());
//
//        logger.debug("Result output from XML: {}", collection);
//
//        List<Criterion> criteria2 = new ArrayList<>();
//        Criterion criterion3 = new Criterion("name", "walking dead", Operator.ALL_WORDS, false);
//        criteria2.add(criterion3);
//        SearchRequest searchRequest2 = new SearchRequest("ComicVine", "volumes", criteria2);
//        SearchRequest result2 = searchService.search(searchRequest2);
//        xslt = classloader.getResourceAsStream("comicvine/volumes.xslt");
//        ResponseCollection<?> collection2 = getCollection("VOLUME", xslt, result2.getRawResult());
//
//        logger.debug("Result output from XML: {}", collection2);
//
//    }

//    public ResponseCollection<?> getCollection(String type, InputStream xslt , InputStream xml ) throws TransformerException {
//
//        ResponseAdapter<?> adapter = ResponseAdapterFactory.getAdapter(type);
//        DatabaseURIResolver resolver = new DatabaseURIResolver(dataSource, "select data from adapter_config where HREF = ?");
//        adapter.setDatabaseURIResolver(resolver);
//
//        return adapter.transform(xslt, xml);
//    }
//
//    public ResponseCollection<?> getCollection(String type, InputStream xslt , StringBuilder xml ) throws TransformerException {
//
//        ResponseAdapter<?> adapter = ResponseAdapterFactory.getAdapter(type);
//        DatabaseURIResolver resolver = new DatabaseURIResolver(dataSource, "select data from adapter_config where HREF = ?");
//        adapter.setDatabaseURIResolver(resolver);
//
//        return adapter.transform(xslt, xml);
//    }
}

