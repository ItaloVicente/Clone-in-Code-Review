package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

public final class SkeletonLabelProvider implements ICommonLabelProvider {

	public static final SkeletonLabelProvider INSTANCE = new SkeletonLabelProvider();

	private SkeletonLabelProvider() {
		super();
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
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

 
	@Override
	public void init(ICommonContentExtensionSite aConfig) {

	}

 
	@Override
	public String getDescription(Object anElement) {
		return null;
	}

	@Override
	public void restoreState(IMemento aMemento) { 
		
	}

	@Override
	public void saveState(IMemento aMemento) { 
		
	}

}
