/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui;

/**
 * This adapter class provides default implementations for the methods described
 * by the <code>IPerspectiveListener</code> interface and its extension
 * interfaces.
 * <p>
 * Classes that wish to deal with events which occur as perspectives are added,
 * removed, activated and changed, can extend this class and override only the
 * methods which they are interested in.
 * </p>
 *
 * @see org.eclipse.ui.IPerspectiveListener
 * @see org.eclipse.ui.IPerspectiveListener2
 * @see org.eclipse.ui.IPerspectiveListener3
 * @see org.eclipse.ui.IPerspectiveListener4
 * @since 3.1
 */
public class PerspectiveAdapter implements IPerspectiveListener4 {


	@Override
	public void perspectiveOpened(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}

	@Override
	public void perspectiveClosed(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective,
			IWorkbenchPartReference partRef, String changeId) {
	}

	@Override
	public void perspectiveActivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective, String changeId) {
	}

	@Override
	public void perspectiveDeactivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}

	@Override
	public void perspectiveSavedAs(IWorkbenchPage page,
			IPerspectiveDescriptor oldPerspective,
			IPerspectiveDescriptor newPerspective) {
	}

	/**
	 * {@inheritDoc}
	 * @since 3.2
	 */
	@Override
	public void perspectivePreDeactivate(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
	}
}
