package org.eclipse.ui.tests.navigator.jst;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class WebJavaLabelProvider implements ILabelProvider {

	public Image getImage(Object element) {
		if(element instanceof ICompressedNode)
			return ((ICompressedNode)element).getImage(); 
		
		return null;
	}

	public String getText(Object element) {
		if(element instanceof ICompressedNode)
			return ((ICompressedNode)element).getLabel(); 
		return null;
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
