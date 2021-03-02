package com.rvh.api.engine.interfaces;

import com.rvh.api.engine.adapter.ResponseAdapter;
import com.rvh.api.engine.model.BaseModel;

public interface ResponseAdapterFactory {

     <T extends BaseModel> ResponseAdapter<T> getAdapter(String model) ;

}
