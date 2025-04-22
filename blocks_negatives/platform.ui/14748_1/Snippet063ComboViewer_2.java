/*******************************************************************************
 * Copyright (c) 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Tom Hochstein (Freescale) - Bug 393703 - NotHandledException selecting inactive command under 'Previous Choices' in Quick access
 ******************************************************************************/

package org.eclipse.ui.internal.quickaccess;

import java.util.List;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;

class PreviousPicksProvider extends QuickAccessProvider {

	private List<QuickAccessElement> previousPicksList;

	PreviousPicksProvider(List<QuickAccessElement> previousPicksList) {
		this.previousPicksList = previousPicksList;
	}

	public QuickAccessElement getElementForId(String id) {
		return null;
	}

	public QuickAccessElement[] getElements() {
		return previousPicksList.toArray(new QuickAccessElement[previousPicksList.size()]);
	}

	public QuickAccessElement[] getElementsSorted() {
		return getElements();
	}

	public String getId() {
	}

	public ImageDescriptor getImageDescriptor() {
		return WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_OBJ_NODE);
	}

	public String getName() {
		return QuickAccessMessages.QuickAccess_Previous;
	}

	protected void doReset() {
	}

	public boolean isAlwaysPresent() {
		return true;
	}
}
