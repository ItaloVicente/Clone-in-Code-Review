package org.eclipse.egit.gitflow.ui.internal.dialogs;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

class FlatBranchesListContentProvider implements ITreeContentProvider {
	@Override
	public void inputChanged(Viewer viewer, Object oldInput,
			Object newInput) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			return ((List) inputElement).toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return new Object[0];
	}
}
