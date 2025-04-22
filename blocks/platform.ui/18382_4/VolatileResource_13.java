package org.eclipse.e4.ui.css.swt.resources;

import org.eclipse.swt.graphics.Resource;

public class VolatileResource <T extends Resource> {	
	private boolean valid;
	
	private T resource;
	
	public VolatileResource(T resource) {
		setResource(resource);
	}
	
	public T getResource() {
		return resource;
	}
	
	public void setResource(T resource) {
		this.resource = resource;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public boolean isValid() {
		return valid;
	}	
}
