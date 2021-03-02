package com.rvh.api.engine.adapter;

import com.rvh.api.engine.adapter.resolver.DatabaseURIResolver;
import com.rvh.api.engine.model.BaseModel;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

public class ResponseAdapter<T extends BaseModel> {

    private final T t;
    private final XStream xstream = new XStream(new StaxDriver());
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ResponseCollection<T> responseCollection;
    private DatabaseURIResolver databaseURIResolver;

    public ResponseAdapter(T type) {
        responseCollection = new ResponseCollection<>();
        t = type;
    }

    public ResponseCollection<T> transform(InputStream xslt, InputStream input) throws TransformerException {

        StreamSource streamSource = new StreamSource(input);
        Writer buffer = new StringWriter();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "db");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setURIResolver(databaseURIResolver);

        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer =transformerFactory.newTransformer(new StreamSource(xslt));

        transformer.transform(streamSource, new StreamResult(buffer));

        xstream.addImplicitCollection(ResponseCollection.class, "modelObjects");
        xstream.processAnnotations(ResponseCollection.class);
        xstream.processAnnotations(t.getClass());
        xstream.allowTypesByWildcard(new String[] {
                "com.rvh.api.com.rvh.api.engine.model.**"
        });


        try {
            responseCollection = (ResponseCollection<T>) xstream.fromXML(buffer.toString());
        } catch (ClassCastException e) {
            logger.error("Unable to cast {}", e.getMessage(), e);
            return null;
        }

        logger.debug("Result XML after transformation: {}", buffer);

        return responseCollection;

    }

    public ResponseCollection<T> transform(InputStream xslt, StringBuilder input) throws TransformerException {

        byte[] bytes = input.toString().getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return transform(xslt, inputStream);
    }

    public DatabaseURIResolver getDatabaseURIResolver() {
        return databaseURIResolver;
    }

    public void setDatabaseURIResolver(DatabaseURIResolver resolver) {
        databaseURIResolver = resolver;
    }
}
