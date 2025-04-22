package org.eclipse.ui.internal.views.log;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class LogViewContentProvider implements ITreeContentProvider {
	private LogView logView;

	public LogViewContentProvider(LogView logView) {
		this.logView = logView;
	}


	@Override
	public Object[] getChildren(Object element) {
		return ((AbstractEntry) element).getChildren(element);
	}

	@Override
	public Object[] getElements(Object element) {
		return logView.getElements();
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof LogSession) {
			return null;
		}
		return ((AbstractEntry) element).getParent(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		return ((AbstractEntry) element).getChildren(element).length > 0;
	}

	public boolean isDeleted(Object element) {
		return false;
	}
}
