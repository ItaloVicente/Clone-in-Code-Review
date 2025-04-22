package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

public final class SkeletonTreeContentProvider implements ICommonContentProvider {

	public static final SkeletonTreeContentProvider INSTANCE = new SkeletonTreeContentProvider();

	private static final Object[] NO_CHILDREN = new Object[0];

	private SkeletonTreeContentProvider() {
		super();
	}

	@Override
	public Object[] getChildren(Object parentElement) {

		return NO_CHILDREN;
	}

	@Override
	public Object getParent(Object element) {

		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {

		return NO_CHILDREN;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public void restoreState(IMemento aMemento) {

	}

	@Override
	public void saveState(IMemento aMemento) {

	}

	@Override
	public void init(ICommonContentExtensionSite aConfig) {

	}

}
