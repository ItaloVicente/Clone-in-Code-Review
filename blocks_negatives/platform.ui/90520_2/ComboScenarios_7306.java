/*******************************************************************************
 * Copyright (c) 2005, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Brad Reynolds - bug 116920
 *     Matthew Hall - bug 260329
 *******************************************************************************/
package org.eclipse.jface.tests.databinding.scenarios;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.examples.databinding.model.Adventure;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

/**
 * To run the tests in this class, right-click and select "Run As JUnit Plug-in
 * Test". This will also start an Eclipse instance. To clean up the launch
 * configuration, open up its "Main" tab and select "[No Application] - Headless
 * Mode" as the application to run.
 */

public class ButtonControlScenario extends ScenariosTestCase {

    private Adventure adventure;

    private Button button;

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        button = new Button(getComposite(), SWT.CHECK);
        adventure = SampleData.WINTER_HOLIDAY;
    }

    @Override
	protected void tearDown() throws Exception {
        button.dispose();
        super.tearDown();
    }

    public void testScenario01() {
        getDbc().bindValue(SWTObservables.observeSelection(button),
                BeansObservables.observeValue(adventure, "petsAllowed"));

        assertEquals(button.getSelection(), adventure.isPetsAllowed());
        boolean newBoolean = !adventure.isPetsAllowed();
        adventure.setPetsAllowed(newBoolean);
        assertEquals(newBoolean, adventure.isPetsAllowed());
        assertEquals(button.getSelection(), newBoolean);
        newBoolean = !newBoolean;
        button.setSelection(newBoolean);
        button.notifyListeners(SWT.Selection, null);
        assertEquals(newBoolean, adventure.isPetsAllowed());
        newBoolean = !newBoolean;
        final boolean finalNewBoolean = newBoolean;
        adventure.setPetsAllowed(finalNewBoolean);
        spinEventLoop(0);
        assertEquals(newBoolean, button.getSelection());

    }

    public void testScenario02() {
        button.dispose();
        button = new Button(getComposite(), SWT.TOGGLE);
        getDbc().bindValue(SWTObservables.observeSelection(button),
                BeansObservables.observeValue(adventure, "petsAllowed"));

        assertEquals(button.getSelection(), adventure.isPetsAllowed());
        boolean newBoolean = !adventure.isPetsAllowed();
        adventure.setPetsAllowed(newBoolean);
        assertEquals(newBoolean, adventure.isPetsAllowed());
        assertEquals(button.getSelection(), newBoolean);
        newBoolean = !newBoolean;
        button.setSelection(newBoolean);
        button.notifyListeners(SWT.Selection, null);
        assertEquals(newBoolean, adventure.isPetsAllowed());
    }

    public void testScenario03() {
        button.dispose();
        button = new Button(getComposite(), SWT.RADIO);

        getDbc().bindValue(SWTObservables.observeSelection(button),
                BeansObservables.observeValue(adventure, "petsAllowed"));

        assertEquals(button.getSelection(), adventure.isPetsAllowed());
        boolean newBoolean = !adventure.isPetsAllowed();
        adventure.setPetsAllowed(newBoolean);
        assertEquals(newBoolean, adventure.isPetsAllowed());
        assertEquals(button.getSelection(), newBoolean);
        newBoolean = !newBoolean;
        button.setSelection(newBoolean);
        button.notifyListeners(SWT.Selection, null);
        assertEquals(newBoolean, adventure.isPetsAllowed());
    }
}
