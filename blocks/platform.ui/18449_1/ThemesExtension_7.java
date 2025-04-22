
package org.eclipse.ui.internal.themes;

import java.util.ResourceBundle;

public class ThemeElementDefinition {
	private boolean overridden;

	private boolean addedByCss;

	private String overriddenLabel;

	public boolean isOverridden() {
		return overridden;
	}

	protected void setOverridden(boolean overridden) {
		this.overridden = overridden;
	}

	public boolean isAddedByCss() {
		return addedByCss;
	}

	public void setAddedByCss(boolean addedByCss) {
		this.addedByCss = addedByCss;
	}

	protected String getOverriddenLabel() {
		if (overriddenLabel == null) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(Theme.class.getName());
			overriddenLabel = resourceBundle.getString("Overridden.by.css.label"); //$NON-NLS-1$
		}
		return overriddenLabel;
	}
}
