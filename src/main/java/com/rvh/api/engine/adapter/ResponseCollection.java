package com.rvh.api.engine.adapter;

import com.rvh.api.engine.model.BaseModel;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("result")
public class ResponseCollection<T extends BaseModel> {

    private List<T> modelObjects = new ArrayList<>();

    public List<T> getModelObjects() {
        return modelObjects;
    }

    public void setModelObjects(List<T> modelObjects) {
        this.modelObjects = modelObjects;
    }

    @Override
    public String toString() {
        return "ResponseCollection{" +
                "modelObjects=" + modelObjects +
                '}';
    }
}
