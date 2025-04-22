package org.eclipse.jface.tests.databinding.scenarios;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.examples.databinding.model.Adventure;
import org.eclipse.jface.examples.databinding.model.PriceModelObject;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Spinner;


public class CustomConverterScenarios extends ScenariosTestCase {

    private Adventure skiTrip;

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        skiTrip = SampleData.WINTER_HOLIDAY;
    }

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testScenario01() {
        DataBindingContext dbc = getDbc();
        Spinner spinner_dollars = new Spinner(getComposite(), SWT.NONE);
        spinner_dollars.setMaximum(10000);
        Spinner spinner_cents = new Spinner(getComposite(), SWT.NONE);

        PriceModelObject priceModel = new PriceModelObject();

        dbc.bindValue(BeansObservables.observeValue(priceModel, "price"), BeansObservables.observeValue(skiTrip,
                "price"));

        dbc.bindValue(SWTObservables.observeSelection(spinner_dollars),
                BeansObservables.observeValue(priceModel, "dollars"));

        dbc.bindValue(SWTObservables.observeSelection(spinner_cents),
                BeansObservables.observeValue(priceModel, "cents"));

        assertEquals(spinner_dollars.getSelection(), new Double(skiTrip.getPrice()).intValue());
        Double doublePrice = new Double(skiTrip.getPrice());
        double cents = 100 * doublePrice.doubleValue() - 100 * doublePrice.intValue();
        assertEquals(spinner_cents.getSelection(), (int) cents);

        spinner_dollars.setSelection(50);
        double expectedPrice = 50 + cents / 100;
        assertEquals(expectedPrice, skiTrip.getPrice(), 0.01);

        spinner_cents.setSelection(27);
        assertEquals(50.27, skiTrip.getPrice(), 0.01);

        skiTrip.setPrice(60.99);
        assertEquals(60, spinner_dollars.getSelection());
        assertEquals(99, spinner_cents.getSelection());

    }

}
