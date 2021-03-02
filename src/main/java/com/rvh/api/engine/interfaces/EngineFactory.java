package com.rvh.api.engine.interfaces;

import com.rvh.api.engine.Engine;

public interface EngineFactory {

    Engine getEngine(String engineName);

}
