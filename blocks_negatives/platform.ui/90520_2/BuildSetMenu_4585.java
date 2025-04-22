/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 * IBM - Initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.ide.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.actions.BuildAction;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;

/**
 * This class will perform an incremental build on a working set.
 *
 * @since 3.0
 */
public class BuildSetAction extends Action {
	public static BuildSetAction lastBuilt;

	private IWorkingSet workingSet;

	private IWorkbenchWindow window;

	private IActionBarConfigurer actionBars;

	/**
	 * Creates a new action that builds the provided working set when run
	 */
	public BuildSetAction(IWorkingSet set, IWorkbenchWindow window, IActionBarConfigurer actionBars) {
		super(set == null ? "" : set.getLabel(), AS_RADIO_BUTTON); //$NON-NLS-1$
		this.window = window;
		this.actionBars = actionBars;
		this.workingSet = set;
	}

	/**
	 * Returns the working set that this instance is building
	 */
	public IWorkingSet getWorkingSet() {
		return workingSet;
	}

	@Override
	public void run() {
		actionBars.registerGlobalAction(this);

		window.getWorkbench().getWorkingSetManager().addRecentWorkingSet(workingSet);
		IProject[] projects = BuildUtilities.extractProjects(workingSet.getElements());
		if (projects.length == 0) {
			MessageDialog.openInformation(window.getShell(),
					IDEWorkbenchMessages.BuildSetAction_noBuildTitle,
					IDEWorkbenchMessages.BuildSetAction_noProjects);
			return;
		}
		lastBuilt = this;
		BuildAction build = new BuildAction(window, IncrementalProjectBuilder.INCREMENTAL_BUILD);
		build.selectionChanged(new StructuredSelection(projects));
		build.run();
	}

	@Override
	public void runWithEvent(Event event) {
		if (event.widget instanceof MenuItem) {
			if (!((MenuItem) event.widget).getSelection()) {
				return;
			}
		}
		run();
	}

	/*
	 * For debugging purposes only.
	 */
	@Override
	public String toString() {
	}
}
