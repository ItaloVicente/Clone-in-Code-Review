/*******************************************************************************
 * Copyright (c) 2004, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.intro;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.intro.IntroDescriptor;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.tests.api.IWorkbenchPartTest;
import org.eclipse.ui.tests.api.MockPart;

/**
 * @since 3.0
 */
public class NoIntroPartTest extends IWorkbenchPartTest {

    private IntroDescriptor oldDesc;

    /**
     * @param testName
     */
    public NoIntroPartTest(String testName) {
        super(testName);
    }

    @Override
	protected MockPart openPart(IWorkbenchPage page) throws Throwable {
        return (MockPart) page.getWorkbenchWindow().getWorkbench()
                .getIntroManager().showIntro(page.getWorkbenchWindow(), false);
    }

    @Override
	protected void closePart(IWorkbenchPage page, MockPart part)
            throws Throwable {
        assertTrue(page.getWorkbenchWindow().getWorkbench().getIntroManager()
                .closeIntro((IIntroPart) part));
    }

    @Override
	public void testOpenAndClose() throws Throwable {
        MockPart part = openPart(fPage);
        assertNull(part);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        oldDesc = Workbench.getInstance().getIntroDescriptor();
        Workbench.getInstance().setIntroDescriptor(null);
    }

    @Override
	protected void doTearDown() throws Exception {
        super.doTearDown();
        Workbench.getInstance().setIntroDescriptor(oldDesc);
    }

}
