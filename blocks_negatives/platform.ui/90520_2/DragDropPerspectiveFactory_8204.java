/*******************************************************************************
 * Copyright (c) 2004, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.dnd;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.internal.dnd.TestDropLocation;
import org.eclipse.ui.tests.autotests.AbstractTestLogger;


/**
 * This class is used as test entries for 'Detached Window' tests (i.e. where the drop target is
 * in a detached window. It's 'doSetup' augments the base behaviour by 'detaching' a stack containing
 * two 'mock' views and separately detaching an individual view, making them appropriate drop targets
 * for these tests.
 * <p>
 * In some cases the sources and targets may overlap with non-detached tests so in order to avoid
 * name clashes in the tests we add a suffix, "(Detached)", to the test's 'name' when the target is
 * 'Detached'.
 * <p>
 * @since 3.1
 *
 */
public class DetachedWindowDragTest	extends DragTest {

	public DetachedWindowDragTest(TestDragSource dragSource,
			TestDropLocation dropTarget, AbstractTestLogger log) {
		super(dragSource, dropTarget, log, " - detached");
	}

    @Override
	public void doSetUp() throws Exception {
    	super.doSetUp();

    	page.showView(DragDropPerspectiveFactory.dropViewId2);
    	page.showView(DragDropPerspectiveFactory.dropViewId1);
    	page.showView(DragDropPerspectiveFactory.dropViewId3);


    	IViewPart viewPart = page.showView(DragDropPerspectiveFactory.dropViewId1);
        DragOperations.drag(viewPart, new DetachedDropTarget(), true);

    	viewPart = page.showView(DragDropPerspectiveFactory.dropViewId3);
        DragOperations.drag(viewPart, new DetachedDropTarget(), false);
    }
}
