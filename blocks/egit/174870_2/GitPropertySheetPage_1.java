package org.eclipse.egit.ui.internal.properties;

import org.eclipse.ui.views.properties.PropertyDescriptor;

public class GitPropertyDescriptor extends PropertyDescriptor {

	public GitPropertyDescriptor(Object id, String label) {
		super(id, label);
	}

	@Override
	public String getDescription() {
		String description = super.getDescription();
		if (description == null) {
			description = getDisplayName();
		}
		return description;
	}
}
