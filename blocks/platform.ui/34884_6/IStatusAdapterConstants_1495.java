
package org.eclipse.ui.statushandlers;

import java.util.Map;

import org.eclipse.ui.application.WorkbenchAdvisor;

public abstract class AbstractStatusHandler {

	private Map params;

	private String id;

	public abstract void handle(StatusAdapter statusAdapter, int style);

	public Map getParams() {
		return params;
	}

	public Object getParam(Object key) {
		if (params != null) {
			return params.get(key);
		}

		return null;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean supportsNotification(int type){
		return false;
	}
}
