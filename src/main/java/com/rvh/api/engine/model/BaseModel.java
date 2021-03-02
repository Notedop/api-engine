package com.rvh.api.engine.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public abstract class BaseModel {

    @XStreamAlias("name")
    private String name;

    public BaseModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
