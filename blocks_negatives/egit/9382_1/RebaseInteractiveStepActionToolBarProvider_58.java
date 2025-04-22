/*******************************************************************************
 * Copyright (c) 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *    Tobias Pfeifer (SAP AG) - initial implementation
 *******************************************************************************/
package org.eclipse.egit.ui.internal.rebase;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jgit.lib.RebaseTodoLine;

/**
 * Content provider feeding rebase interactive plan
 */
public enum RebaseInteractivePlanContentProvider implements ITreeContentProvider {

	/** Singleton instance */
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
		return null;
	}

	public boolean hasChildren(Object element) {
		return (element instanceof RebaseInteractivePlan);
	}
}
