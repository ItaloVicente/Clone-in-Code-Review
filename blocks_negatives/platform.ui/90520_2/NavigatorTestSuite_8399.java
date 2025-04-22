/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.navigator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PartInitException;

/**
 * Tests the Resource Navigator view.
 */
public class NavigatorTest extends AbstractNavigatorTest {

    public NavigatorTest(String testName) {
        super(testName);
    }

    /**
     * Tests that the Navigator is initially populated with
     * the correct elements from the workspace.
     */
    public void testInitialPopulation() throws CoreException, PartInitException {
        createTestFile();
        showNav();

        ISelectionProvider selProv = navigator.getSite().getSelectionProvider();
        StructuredSelection sel = new StructuredSelection(testFile);
        selProv.setSelection(sel);
        assertEquals(sel.size(), ((IStructuredSelection)selProv.getSelection()).size());
        assertEquals(sel.getFirstElement(), ((IStructuredSelection)selProv.getSelection()).getFirstElement());
    }

    /**
     * Tests that the Navigator updates properly when a file is added to the workbench.
     */
    public void testFileAddition() throws CoreException, PartInitException {
        showNav();

        ISelectionProvider selProv = navigator.getSite().getSelectionProvider();
        StructuredSelection sel = new StructuredSelection(testFile);
        selProv.setSelection(sel);
        assertEquals(sel.size(), ((IStructuredSelection)selProv.getSelection()).size());
        assertEquals(sel.getFirstElement(), ((IStructuredSelection)selProv.getSelection()).getFirstElement());
    }

}
