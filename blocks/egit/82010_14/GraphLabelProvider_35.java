package org.eclipse.egit.ui.internal.history;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

class GraphContentProvider implements IStructuredContentProvider {
	private SWTCommit[] list;

	@Override
	public void inputChanged(final Viewer newViewer, final Object oldInput,
			final Object newInput) {
		list = (SWTCommit[]) newInput;
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		return list;
	}

	@Override
	public void dispose() {
	}
}
