package com.github.jesty.camera.pipes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.jesty.camera.Action;

public abstract class AbstractPipe implements Pipe {
	
	private List<Action> actions = new LinkedList<Action>();
	
	@Override
	public void execute(Map<String, Object> context) {
		for (Action action : actions) {
			try {
				action.doAction(context);
			} catch (Exception e) {
				System.err.println("cannot finish pipe: " + e.getMessage());
				return;
			}
		}
	}
		
	@Override
	public void addBefore(Action action) {
		actions.add(0, action);
	}
	
	@Override
	public void addAll(List<Action> action) {
		actions.addAll(action);
	}
	
	@Override
	public void add(Action... action) {
		addAll(Arrays.asList(action));
	}
	
	@Override
	public void init(){
		for (Action action : actions) {
			action.init();
		}
	}

}
