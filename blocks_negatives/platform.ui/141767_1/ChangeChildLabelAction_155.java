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
import org.eclipse.jface.tests.viewers.TestModelChange;

public class AddSiblingAction extends TestSelectionAction {

	int fEventKind;

	public AddSiblingAction(String label, TestBrowser browser) {
		this(label, browser, TestModelChange.INSERT);
	}

	public AddSiblingAction(String label, TestBrowser browser, int eventKind) {
		super(label, browser);
		fEventKind = eventKind;
	}

	@Override
	public void run(TestElement element) {
		element.getContainer().addChild(fEventKind);
	}
}
