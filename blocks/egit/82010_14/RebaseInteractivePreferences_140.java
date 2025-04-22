package org.eclipse.egit.ui.internal.rebase;

import java.util.Collections;
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

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RebaseInteractivePlan) {
			RebaseInteractivePlan plan = (RebaseInteractivePlan) parentElement;
			List<RebaseInteractivePlan.PlanElement> linesToDisplay = new LinkedList<>();
			for (RebaseInteractivePlan.PlanElement line : plan.getList()) {
				if (line.isComment())
					continue;
				linesToDisplay.add(line);
			}

			if (RebaseInteractivePreferences.isOrderReversed())
				Collections.reverse(linesToDisplay);

			return linesToDisplay.toArray();
		}

		if (parentElement instanceof RebaseTodoLine) {
			return new Object[0];
		}

		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return (element instanceof RebaseInteractivePlan);
	}
}
