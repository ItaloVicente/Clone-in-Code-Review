package org.eclipse.jface.tests.databinding.scenarios;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.examples.databinding.model.Adventure;
import org.eclipse.jface.examples.databinding.model.AggregateObservableValue;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;


public class CustomScenarios extends ScenariosTestCase {

    @Override
	protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testScenario01() {


        Adventure adventure = SampleData.WINTER_HOLIDAY;
        Text text = new Text(getComposite(), SWT.BORDER);

        IObservableValue descriptionObservable = BeansObservables.observeValue(adventure, "description");
        IObservableValue nameObservable = BeansObservables.observeValue(adventure, "name");
        AggregateObservableValue customObservable_comma = new AggregateObservableValue(new IObservableValue[] {
                descriptionObservable, nameObservable }, ",");

        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify), customObservable_comma);
        assertEquals(adventure.getDescription() + "," + adventure.getName(), text.getText());

        text.setText("newDescription,newName");
        assertEquals("newDescription", adventure.getDescription());
        assertEquals("newName", adventure.getName());

        adventure.setDescription("newDescription_0");
        adventure.setName("newName_0");
        assertEquals("newDescription_0,newName_0", text.getText());

        text.setText("newDescription_1");
        assertEquals("newDescription_1", adventure.getDescription());
        assertEquals(null, adventure.getName());


    }

}
