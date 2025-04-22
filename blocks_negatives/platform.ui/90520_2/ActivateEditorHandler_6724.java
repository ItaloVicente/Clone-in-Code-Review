/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.contributions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

/**
 * Moved from org.eclipse.ui.examples.readmetool
 * 
 * @since 3.3
 */
public class ExampleControlContribution extends
		WorkbenchWindowControlContribution {
	@Override
	protected Control createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);

		FillLayout layout = new FillLayout();
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		comp.setLayout(layout);

		Label ccCtrl = new Label(comp, SWT.BORDER | SWT.CENTER);
		ccCtrl.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_DARK_BLUE));
		ccCtrl.setForeground(parent.getDisplay()
				.getSystemColor(SWT.COLOR_WHITE));

		return comp;
	}

	private String getSideName(int side) {
		if (side == SWT.TOP)
		if (side == SWT.BOTTOM)
		if (side == SWT.LEFT)
		if (side == SWT.RIGHT)

	}
}
