package com.rvh.api.engine.adapter.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Use this resolver for resolving URI's using database. The URI should be pre-fixed with DB:\\
 */
public class DatabaseURIResolver implements URIResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DataSource datasource;
    private String sqlStatement;

    /**
     * Instantiates a new Database uri resolver.
     *
     * @param datasource   the datasource
     * @param sqlStatement the sql statement which should return a single record and single column
     */
    public DatabaseURIResolver(DataSource datasource, String sqlStatement) {
        this.datasource = datasource;
        this.sqlStatement = sqlStatement;
    }

    @Override
    public Source resolve(String href, String base) {
        if (href!= null && !href.isEmpty() && datasource!=null) {
                try (Connection connection = datasource.getConnection();
                     PreparedStatement query = connection.prepareStatement(sqlStatement)) {
                     query.setString(1, href);
                     try (ResultSet rs = query.executeQuery()) {
                         if(rs.next()){
                             StreamSource source = new StreamSource(rs.getAsciiStream(1));
                             logger.debug("{}", source);
                             return source;
                         }
                     }
                } catch (SQLException e) {
                    logger.debug(e.getMessage(), e);
                }
        }
        return null;
    }

    /**
     * Gets datasource.
     *
     * @return the datasource
     */
    public DataSource getDatasource() {
        return datasource;
    }

    /**
     * Sets datasource.
     *
     * @param datasource the datasource
     */
    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    /**
     * Gets sql statement.
     *
     * @return the sql statement
     */
    public String getSqlStatement() {
        return sqlStatement;
    }

    /**
     * Sets sql statement which should return single row, single column.
     *
     * @param sqlStatement the sql statement
     */
    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }
}
