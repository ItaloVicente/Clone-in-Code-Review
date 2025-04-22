package org.eclipse.egit.ui.internal.rebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.egit.ui.internal.rebase.RebaseInteractivePlan.PlanEntry;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public enum RebaseInteractivePlanContentProvider implements ITreeContentProvider {
	INSTANCE;
	private RebaseInteractivePlanContentProvider() {
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	private Object[] reverse(List<PlanEntry> list) {
		List<Object> copy = new ArrayList<Object>(list);
		Collections.reverse(copy);
		return copy.toArray();
	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RebaseInteractivePlan) {
			return reverse(((RebaseInteractivePlan) parentElement).getTodo());
		}
		if (parentElement instanceof PlanEntry) {
			return new Object[0];
		}

		return new Object[0];
	}

	public Object getParent(Object element) {
		if (element instanceof RebaseInteractivePlan) {
			return null;
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		return (element instanceof RebaseInteractivePlan);
	}

}
