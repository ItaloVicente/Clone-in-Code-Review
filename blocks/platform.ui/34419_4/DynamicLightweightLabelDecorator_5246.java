package org.eclipse.ui.dynamic;

import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class DynamicLabelDecorator implements ILabelDecorator {

	public DynamicLabelDecorator() {
		super();
	}

	public Image decorateImage(Image image, Object element) {
		return null;
	}

	public String decorateText(String text, Object element) {
		return text + " F1 ";
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
