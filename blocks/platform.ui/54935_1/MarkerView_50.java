package org.eclipse.ui.views.markers.internal;


import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

public class MarkerTreeContentProvider implements ITreeContentProvider {

	TreeViewer viewer;

	boolean hierarchalMode = true;

	private MarkerAdapter adapter;

	MarkerTreeContentProvider() {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof MarkerNode) {
			return ((MarkerNode) parentElement).getChildren();
		}
		return Util.EMPTY_MARKER_ARRAY;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof MarkerNode) {
			return ((MarkerNode) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof MarkerNode) {
			return ((MarkerNode) element).getChildren().length > 0;
		}
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return adapter.getElements();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TreeViewer) viewer;
		adapter = (MarkerAdapter) newInput;

	}
}
