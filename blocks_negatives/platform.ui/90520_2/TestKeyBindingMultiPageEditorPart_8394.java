/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.tests.multipageeditor;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

/**
 * @since 3.5
 *
 */
public class PartPageListener implements IPartListener2, IPageChangedListener {

	public int pageChangeCount;
	public PageChangedEvent currentChangeEvent;

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {

	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		pageChangeCount++;
		currentChangeEvent = event;
	}

}
