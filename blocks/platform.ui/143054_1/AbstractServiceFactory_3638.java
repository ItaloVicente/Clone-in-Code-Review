package org.eclipse.ui.quickaccess;

import org.eclipse.jface.resource.ImageDescriptor;

public abstract class QuickAccessElement {

	protected static final String separator = " - "; //$NON-NLS-1$

	public abstract String getLabel();

	public abstract ImageDescriptor getImageDescriptor();

	public abstract String getId();

	public abstract void execute();

	public String getSortLabel() {
		return getLabel();
	}

	public String getMatchLabel() {
		return getLabel();
	}
}
