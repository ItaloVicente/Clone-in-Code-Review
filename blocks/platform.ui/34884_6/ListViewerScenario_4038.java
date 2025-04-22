package org.eclipse.jface.tests.databinding.scenarios;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.examples.databinding.model.Adventure;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;


public class LabelControlScenario extends ScenariosTestCase {

    private Adventure adventure;

    private Label label;

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        label = new Label(getComposite(), SWT.NONE);
        adventure = SampleData.WINTER_HOLIDAY;
    }

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
        label.dispose();
        label = null;
    }

    public void testScenario01() {
        getDbc().bindValue(SWTObservables.observeText(label), BeansObservables.observeValue(adventure, "name"));

        assertEquals(adventure.getName(), label.getText());
        adventure.setName("France");
        assertEquals("France", label.getText());
        adventure.setName("Climb Everest");
        spinEventLoop(0);
        assertEquals("Climb Everest", label.getText());
    }
}
