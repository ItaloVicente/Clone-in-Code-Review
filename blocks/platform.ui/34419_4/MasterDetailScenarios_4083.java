package org.eclipse.jface.tests.databinding.scenarios;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.examples.databinding.model.Adventure;
import org.eclipse.jface.examples.databinding.model.Catalog;
import org.eclipse.jface.examples.databinding.model.Lodging;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;


public class ListViewerScenario extends ScenariosTestCase {

	private Catalog catalog;

	private List list;

	private ListViewer listViewer;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		list = new List(getComposite(), SWT.READ_ONLY | SWT.SINGLE);
		listViewer = new ListViewer(list);
		catalog = SampleData.CATALOG_2005; // Lodging source
	}

	@Override
	protected void tearDown() throws Exception {
		list.dispose();
		list = null;
		listViewer = null;
		super.tearDown();
	}

	public void testScenario01() {
		IObservableList lodgings = BeansObservables.observeList(Realm
				.getDefault(), catalog, "lodgings");
		ViewerSupport.bind(listViewer, lodgings, BeanProperties.value(
				Lodging.class, "name"));

		for (int i = 0; i < catalog.getLodgings().length; i++) {
			assertEquals(catalog.getLodgings()[i], listViewer.getElementAt(i));
		}
		String[] lodgingStrings = new String[catalog.getLodgings().length];
		for (int i = 0; i < catalog.getLodgings().length; i++) {
			lodgingStrings[i] = catalog.getLodgings()[i].getName();
		}
		assertArrayEquals(lodgingStrings, list.getItems());

		assertEquals(null, ((IStructuredSelection) listViewer.getSelection())
				.getFirstElement());

		final Adventure adventure = SampleData.WINTER_HOLIDAY;

		IObservableValue selection = ViewersObservables
				.observeSingleSelection(listViewer);
		getDbc().bindValue(selection,
				BeansObservables.observeValue(adventure, "defaultLodging"));

		assertEquals(((IStructuredSelection) listViewer.getSelection())
				.getFirstElement(), adventure.getDefaultLodging());

		adventure.setDefaultLodging(SampleData.CAMP_GROUND);
		assertEquals(adventure.getDefaultLodging(), SampleData.CAMP_GROUND);
		assertEquals(((IStructuredSelection) listViewer.getSelection())
				.getFirstElement(), adventure.getDefaultLodging());

		listViewer.getList().select(3);
		assertEquals(((IStructuredSelection) listViewer.getSelection())
				.getFirstElement(), adventure.getDefaultLodging());

		adventure.setDefaultLodging(SampleData.YOUTH_HOSTEL);
		spinEventLoop(0);
		assertEquals(((IStructuredSelection) listViewer.getSelection())
				.getFirstElement(), adventure.getDefaultLodging());

	}
}
