/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.e4.ui.tests.workbench;

import javax.inject.Inject;
import org.eclipse.swt.widgets.Composite;

public class LayoutView {


	@Inject
	public LayoutView(Composite parent) {
		parent.getShell().layout(true, true);
	}
}
