package org.eclipse.ui.tests.navigator.jst;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class WebJavaLabelProvider implements ILabelProvider {

	@Override
	public Image getImage(Object element) {
		if(element instanceof ICompressedNode)
			return ((ICompressedNode)element).getImage();

		return null;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof ICompressedNode)
			return ((ICompressedNode)element).getLabel();
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

}
