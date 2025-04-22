package org.eclipse.jface.tests.databinding.scenarios;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.examples.databinding.model.Adventure;
import org.eclipse.jface.examples.databinding.model.Catalog;
import org.eclipse.jface.examples.databinding.model.Lodging;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;


public class ComboViewerScenario extends ScenariosTestCase {

	private Catalog catalog;

	private Combo combo;

	private ComboViewer comboViewer;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		combo = new Combo(getComposite(), SWT.READ_ONLY | SWT.DROP_DOWN);
		comboViewer = new ComboViewer(combo);
		catalog = SampleData.CATALOG_2005; // Lodging source
	}

	@Override
	protected void tearDown() throws Exception {
		combo.dispose();
		combo = null;
		comboViewer = null;
		super.tearDown();
	}

	public void testScenario01() {
		IObservableList lodgings = BeansObservables.observeList(realm, catalog,
				"lodgings");
		ViewerSupport.bind(comboViewer, lodgings, BeanProperties.value(
				Lodging.class, "name"));

		for (int i = 0; i < catalog.getLodgings().length; i++) {
			assertEquals(catalog.getLodgings()[i], comboViewer.getElementAt(i));
		}
		String[] lodgingStrings = new String[catalog.getLodgings().length];
		for (int i = 0; i < catalog.getLodgings().length; i++) {
			lodgingStrings[i] = catalog.getLodgings()[i].getName();
		}
		assertArrayEquals(lodgingStrings, combo.getItems());

		assertEquals(null, ((IStructuredSelection) comboViewer.getSelection())
				.getFirstElement());

		final Adventure adventure = SampleData.WINTER_HOLIDAY;
		IObservableValue selection = ViewersObservables
				.observeSingleSelection(comboViewer);
		getDbc().bindValue(selection,
				BeansObservables.observeValue(adventure, "defaultLodging"));

		assertEquals(((IStructuredSelection) comboViewer.getSelection())
				.getFirstElement(), adventure.getDefaultLodging());

		adventure.setDefaultLodging(SampleData.CAMP_GROUND);
		assertEquals(adventure.getDefaultLodging(), SampleData.CAMP_GROUND);
		assertEquals(((IStructuredSelection) comboViewer.getSelection())
				.getFirstElement(), adventure.getDefaultLodging());

		comboViewer.getCombo().select(3);
		assertEquals(((IStructuredSelection) comboViewer.getSelection())
				.getFirstElement(), adventure.getDefaultLodging());

		adventure.setDefaultLodging(SampleData.YOUTH_HOSTEL);
		spinEventLoop(0);
		assertEquals(((IStructuredSelection) comboViewer.getSelection())
				.getFirstElement(), adventure.getDefaultLodging());
	}

}
