package org.eclipse.ui.dynamic;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

public class DynamicLightweightLabelDecorator implements
		ILightweightLabelDecorator {

	public DynamicLightweightLabelDecorator() {
		super();
	}

	public void decorate(Object element, IDecoration decoration) {
		decoration.addSuffix("Light");
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}
}
