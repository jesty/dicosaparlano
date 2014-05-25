package com.github.jesty.camera;

import java.util.Map;

public interface Action {
	
	void doAction(Map<String, Object> context) throws Exception;
	void init();

}
