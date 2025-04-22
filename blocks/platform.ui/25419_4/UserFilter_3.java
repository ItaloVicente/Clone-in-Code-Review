package org.eclipse.ui.internal.navigator.filters;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.navigator.CommonViewer;

public class UserFilter {
	private String regexp = "*.something"; //$NON-NLS-1$
	private boolean enabled = false;
	
	public UserFilter() { }
	
	public UserFilter(String regexp, boolean enable) {
		this.regexp = regexp;
		this.enabled = enable;
	}
	
	public String getRegexp() {
		return this.regexp;
	}
	
	public void setRegexp(String newRegexp) {
		this.regexp = newRegexp;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enable) {
		this.enabled = enable;
	}
}
