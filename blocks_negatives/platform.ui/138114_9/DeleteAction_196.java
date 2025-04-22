/*******************************************************************************
 * Copyright (c) 2000, 2014 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestElement;

public class CreateModelAction extends TestBrowserAction {
	int fLevel;

	int fChildCount;

	public CreateModelAction(String label, TestBrowser browser, int level,
			int childCount) {
		super(label, browser);
		fLevel = level;
		fChildCount = childCount;
	}

	@Override
	public void run() {
		getBrowser().setInput(null);
		getBrowser().setInput(TestElement.createModel(fLevel, fChildCount));
	}
}
