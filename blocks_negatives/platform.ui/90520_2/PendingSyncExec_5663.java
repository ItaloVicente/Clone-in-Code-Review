/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPart2;

public class PartTester {
    private PartTester() {
    }

    /**
     * Sanity-check the public interface of the editor. This is called on every editor after it
     * is fully initiallized, but before it is actually connected to the editor reference or the
     * layout. Calls as much of the editor's public interface as possible to test for exceptions,
     * and tests the return values for glaring faults. This does not need to be an exhaustive conformance
     * test, as it is called every time an editor is opened and it needs to be efficient.
     * The part should be unmodified when the method exits.
     *
     * @param part
     */
    public static void testEditor(IEditorPart part) throws Exception {
        testWorkbenchPart(part);

        Assert.isTrue(part.getEditorSite() == part.getSite(),
		IEditorInput input = part.getEditorInput();
		Assert.isNotNull(input, "The editor input must be non-null"); //$NON-NLS-1$
		testEditorInput(input);

        part.isDirty();
        part.isSaveAsAllowed();
        part.isSaveOnCloseNeeded();
    }

    public static void testEditorInput(IEditorInput input) throws Exception {
        input.getAdapter(Object.class);


        Assert.isNotNull(input.getName(),
		Assert.isNotNull(input.getToolTipText(),

		IPersistableElement persistableElement = input.getPersistable();
		if (persistableElement != null) {
			Assert
					.isNotNull(persistableElement.getFactoryId(),
        }
    }

    /**
     * Sanity-checks a workbench part. Excercises the public interface and tests for any
     * obviously bogus return values. The part should be unmodified when the method exits.
     *
     * @param part
     * @throws Exception
     */
    private static void testWorkbenchPart(IWorkbenchPart part) throws Exception {
        IPropertyListener testListener = (source, propId) -> {

		};

        part.addPropertyListener(testListener);

        part.removePropertyListener(testListener);

		Assert.isTrue(part.equals(part), "A part must be equal to itself"); //$NON-NLS-1$
		Assert.isTrue(!part.equals(Integer.valueOf(32)),

        Object partAdapter = part.getAdapter(part.getClass());
        Assert.isTrue(partAdapter == null || partAdapter == part,

		Assert.isNotNull(part.getTitle(), "A part's title must be non-null"); //$NON-NLS-1$

		Assert.isNotNull(part.getTitleImage(),

		Assert.isNotNull(part.getTitleToolTip(),

		Assert.isNotNull(part.toString(),

        part.hashCode();

        if (part instanceof IWorkbenchPart2) {
            testWorkbenchPart2((IWorkbenchPart2)part);
        }
    }

    private static void testWorkbenchPart2(IWorkbenchPart2 part)
			throws Exception {
		Assert.isNotNull(part.getContentDescription(),
		Assert.isNotNull(part.getPartName(),
    }

    /**
     * Sanity-check the public interface of a view. This is called on every view after it
     * is fully initiallized, but before it is actually connected to the part reference or the
     * layout. Calls as much of the part's public interface as possible without modifying the part
     * to test for exceptions and check the return values for glaring faults. This does not need
     * to be an exhaustive conformance test, as it is called every time an editor is opened and
     * it needs to be efficient.
     *
     * @param part
     */
    public static void testView(IViewPart part) throws Exception {
       Assert.isTrue(part.getSite() == part.getViewSite(),
       testWorkbenchPart(part);
    }
}
