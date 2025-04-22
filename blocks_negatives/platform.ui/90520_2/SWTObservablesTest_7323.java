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

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.examples.databinding.model.Account;
import org.eclipse.jface.examples.databinding.model.Adventure;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.jface.examples.databinding.model.Transportation;
import org.eclipse.jface.tests.databinding.BindingTestSuite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ibm.icu.text.NumberFormat;

/**
 * To run the tests in this class, right-click and select "Run As JUnit Plug-in
 * Test". This will also start an Eclipse instance. To clean up the launch
 * configuration, open up its "Main" tab and select "[No Application] - Headless
 * Mode" as the application to run.
 */

public class TextControlScenario extends ScenariosTestCase {

    private Text text;

    private Adventure adventure;

    private Transportation transportation;

    Account account;

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        adventure = SampleData.WINTER_HOLIDAY;
        transportation = SampleData.EXECUTIVE_JET;
        account = SampleData.PRESIDENT;
        text = new Text(getComposite(), SWT.BORDER);
    }

    @Override
	protected void tearDown() throws Exception {
        text.dispose();
        text = null;
        super.tearDown();
    }

    public void testScenario01() {
        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
                BeansObservables.observeValue(adventure, "name"));

        assertEquals(adventure.getName(), text.getText());
        text.setText("England");
        text.notifyListeners(SWT.FocusOut, null);
        assertEquals("England", adventure.getName());
        adventure.setName("France");
        assertEquals("France", text.getText());
        adventure.setName("Germany");
        spinEventLoop(0);
        assertEquals("Germany", text.getText());
    }

    public void testScenario02() {

        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
                BeansObservables.observeValue(transportation, "price"));

        NumberFormat numberFormat = NumberFormat.getInstance();

        assertEquals(numberFormat.format(transportation.getPrice()), text.getText());
        text.setText("9876.54");
        text.notifyListeners(SWT.FocusOut, null);
        assertEquals(9876.54, transportation.getPrice(), 0);

        transportation.setPrice(1234.56);
        assertEquals(numberFormat.format(transportation.getPrice()), text.getText());
    }



    /**
     * Adventure has defaultLodging and Lodging has name getDbc().bind(text,new
     * Property(adventure,defaultLodging.name),null); // Verify the GUI is
     * showing the model value
     * assertEquals(text.getText(),adventure.getDefaultLodging().getName()); }
     */
    public void testScenario06() {
    }

    public void testScenario07() {
    }

    public void testScenario08() {

        if (BindingTestSuite.failingTestsDisabled(this)) {
            return;
        }


        DataBindingContext dbc = getDbc();

        dbc.bindValue(SWTObservables.observeText(text, SWT.Modify),
				BeansObservables.observeValue(adventure, "maxNumberOfPeople"),
				new CustomBeanUpdateValueStrategy(), null);

        text.setText("4");
        assertEquals(4, adventure.getMaxNumberOfPeople());
        text.setText("999");
        assertEquals(4, adventure.getMaxNumberOfPeople());
        dbc.dispose();
    }

    public void testScenario09() {
        Label label = new Label(getComposite(), SWT.NONE);
        getDbc().bindValue(SWTObservables.observeText(text, SWT.FocusOut), SWTObservables.observeText(label));

        text.setText("Frog");
        assertTrue(label.getText().length() == 0);
        text.notifyListeners(SWT.FocusOut, null);
        assertEquals(label.getText(), "Frog");

    }

    public void testScenario10() {
        Label label = new Label(getComposite(), SWT.NONE);
        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify), SWTObservables.observeText(label));

        String newTextValue = "Frog";
        for (int i = 0; i < newTextValue.length(); i++) {
            text.setText(newTextValue.substring(0, i + 1));
            assertEquals(text.getText(), label.getText());
        }
        text.notifyListeners(SWT.FocusOut, null);
        assertEquals(text.getText(), label.getText());
    }

}
