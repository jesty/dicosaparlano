package com.github.jesty.camera.pipes;

import java.util.List;
import java.util.Map;

import com.github.jesty.camera.Action;

public interface Pipe {
	
	void execute(Map<String, Object> context);
	
	public void addBefore(Action action);
	
	public void add(Action...action);

	void addAll(List<Action> action);

	void init();

}
