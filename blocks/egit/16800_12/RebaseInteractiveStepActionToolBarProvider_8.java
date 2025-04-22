package org.eclipse.egit.ui.internal.rebase;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jgit.lib.RebaseTodoLine;

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

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RebaseInteractivePlan) {
			RebaseInteractivePlan plan = (RebaseInteractivePlan) parentElement;
			List<RebaseInteractivePlan.PlanElement> linesToDisplay = new LinkedList<RebaseInteractivePlan.PlanElement>();
			for (RebaseInteractivePlan.PlanElement line : plan.getList()) {
				if (line.isComment())
					continue;
				linesToDisplay.add(line);
			}
			return linesToDisplay.toArray();
		}
		if (parentElement instanceof RebaseTodoLine) {
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
