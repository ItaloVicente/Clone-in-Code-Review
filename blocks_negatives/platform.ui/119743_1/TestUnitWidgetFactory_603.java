/*******************************************************************************
 * Copyright (c) 2019 Marcus Hoepfner and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Marcus Hoepfner - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.tests.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jface.widgets.TreeFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.junit.Test;

public class TestUnitTreeFactory extends AbstractFactoryTest {

	@Test
	public void createsTree() {
		Tree tree = TreeFactory.newTree(SWT.CHECK).create(shell);
		assertEquals(shell, tree.getParent());
		assertEquals(SWT.CHECK, tree.getStyle() & SWT.CHECK);
	}

	@Test
	public void createsTreeWithAllProperties() {
		final SelectionEvent[] raisedEvents = new SelectionEvent[3];

				.create(shell);

		tree.notifyListeners(SWT.Selection, new Event());
		tree.notifyListeners(SWT.Expand, new Event());
		tree.notifyListeners(SWT.Collapse, new Event());
		assertEquals(1, tree.getListeners(SWT.Selection).length);
		assertNotNull(raisedEvents[0]);

		assertEquals(2, tree.getListeners(SWT.Expand).length);
		assertNotNull(raisedEvents[1]);
		assertEquals(2, tree.getListeners(SWT.Collapse).length);
		assertNotNull(raisedEvents[2]);
		assertTrue(tree.getHeaderVisible());
		assertTrue(tree.getLinesVisible());
		assertEquals(42, tree.getItemCount());
	}
}
