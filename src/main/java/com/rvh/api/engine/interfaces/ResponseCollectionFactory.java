package com.rvh.api.engine.interfaces;

import com.rvh.api.engine.adapter.ResponseCollection;

public interface ResponseCollectionFactory {

    ResponseCollection<?> getCollection(String model) ;

}
