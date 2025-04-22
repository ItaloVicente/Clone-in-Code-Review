/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.tests.internal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.tests.harness.util.UITestCase;

/**
 * Test for Bug 41931.
 *
 * @since 3.0
 */
public class Bug41931Test extends UITestCase {

    /**
     * Constructs a new instance of this test case.
     *
     * @param testName
     *            The name of the test
     */
    public Bug41931Test(String testName) {
        super(testName);
    }

    /**
     * Tests that the <code>bringToTop(IWorkbenchPart)</code> correctly
     * updates the activation list.
     *
     * @throws CoreException
     *             If the test project cannot be created or opened.
     */
    public void testBringToTop() throws CoreException {
        IWorkbenchWindow window = openTestWindow();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        testProject.create(null);
        testProject.open(null);

        InputStream contents = new ByteArrayInputStream(new byte[0]);
        fileA.create(contents, true, null);
        fileB.create(contents, true, null);
        fileC.create(contents, true, null);

        WorkbenchPage page = (WorkbenchPage) window.getActivePage();
        IEditorPart editorA = IDE.openEditor(page, fileA, true);
        IEditorPart editorB = IDE.openEditor(page, fileB, true);
        IEditorPart editorC = IDE.openEditor(page, fileC, true);

        IEditorPart[] expectedResults = { editorA, editorB, editorC };
        IWorkbenchPartReference[] actualResults = page.getSortedParts();
        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(
                    "Pre-test order is not correct.", expectedResults[i].getTitle(), actualResults[i].getPart(false).getTitle()); //$NON-NLS-1$
        }

        page.bringToTop(editorB);

        expectedResults = new IEditorPart[] { editorA, editorC, editorB };
        actualResults = page.getSortedParts();
        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(
                    "bringToTop() does not change sorted part order.", expectedResults[i].getTitle(), actualResults[i].getPart(false).getTitle()); //$NON-NLS-1$
        }
    }
}
