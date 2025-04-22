package org.eclipse.jface.tests.databinding.scenarios;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.examples.databinding.model.Account;
import org.eclipse.jface.examples.databinding.model.Adventure;
import org.eclipse.jface.examples.databinding.model.Catalog;
import org.eclipse.jface.examples.databinding.model.Lodging;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;

public class ComboScenarios extends ScenariosTestCase {

	protected ComboViewer cviewer = null;

	protected Combo combo = null;

	protected Catalog catalog = null;

	ILabelProvider lodgingLabelProvider = new LabelProvider() {
		@Override
		public String getText(Object element) {
			return ((Lodging) element).getName();
		}
	};

	ILabelProvider accountLabelProvider = new LabelProvider() {
		@Override
		public String getText(Object element) {
			return ((Account) element).getCountry();
		}
	};

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getComposite().setLayout(new FillLayout());

		combo = new Combo(getComposite(), SWT.READ_ONLY | SWT.DROP_DOWN);
		cviewer = new ComboViewer(combo);

		catalog = SampleData.CATALOG_2005; // Lodging source
	}

	@Override
	protected void tearDown() throws Exception {
		combo.dispose();
		combo = null;
		cviewer = null;
		super.tearDown();
	}

	protected Object getViewerSelection() {
		return ((IStructuredSelection) cviewer.getSelection())
				.getFirstElement();
	}

	protected List getViewerContent(ComboViewer cviewer) {
		Object[] elements = ((IStructuredContentProvider) cviewer
				.getContentProvider()).getElements(null);
		if (elements != null)
			return Arrays.asList(elements);
		return null;
	}

	protected List getComboContent() {
		String[] elements = combo.getItems();
		if (elements != null)
			return Arrays.asList(elements);
		return null;
	}

	protected List getColumn(Object[] list, String feature) {
		List result = new ArrayList();
		if (list == null || list.length == 0)
			return result;
		String getterName = "get"
				+ feature.substring(0, 1).toUpperCase(Locale.ENGLISH)
				+ feature.substring(1);
		try {
			Method getter = list[0].getClass().getMethod(getterName,
					new Class[0]);
			try {
				for (int i = 0; i < list.length; i++) {
					result.add(getter.invoke(list[i], new Object[0]));
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
		return result;
	}

	public void test_ROCombo_Scenario03_vanilla() {
		IObservableList lodgings = BeansObservables.observeList(Realm
				.getDefault(), catalog, "lodgings");
		ViewerSupport.bind(cviewer, lodgings, BeanProperties.value(
				Lodging.class, "name"));

		Adventure skiAdventure = SampleData.WINTER_HOLIDAY; // selection will

		assertArrayEquals(catalog.getLodgings(), getViewerContent(cviewer)
				.toArray());

		assertEquals(getColumn(catalog.getLodgings(), "name"),
				getComboContent());

		getDbc().bindValue(ViewersObservables.observeSingleSelection(cviewer),
				BeansObservables.observeValue(skiAdventure, "defaultLodging"));

		assertEquals(getViewerSelection(), skiAdventure.getDefaultLodging());

		for (int i = 0; i < catalog.getLodgings().length; i++) {
			Object selection = catalog.getLodgings()[i];
			cviewer.setSelection(new StructuredSelection(selection));
			assertEquals(selection, skiAdventure.getDefaultLodging());
			assertEquals(getViewerSelection(), skiAdventure.getDefaultLodging());
		}

	}

	public void test_ROCombo_Scenario03_collectionBindings() {
		IObservableList lodgings = BeansObservables.observeList(Realm
				.getDefault(), catalog, "lodgings");
		ViewerSupport.bind(cviewer, lodgings, BeanProperties.value(
				Lodging.class, "name"));

		assertArrayEquals(catalog.getLodgings(), getViewerContent(cviewer)
				.toArray());

		assertEquals(getColumn(catalog.getLodgings(), "name"),
				getComboContent());


		Lodging lodging = SampleData.FACTORY.createLodging();
		lodging.setName("End Lodging");
		catalog.addLodging(lodging);
		int index = getComboContent().size() - 1;
		assertEquals(getViewerContent(cviewer).get(index), lodging);

		catalog.removeLodging(catalog.getLodgings()[0]);
		assertEquals(getColumn(catalog.getLodgings(), "name"),
				getComboContent());

		catalog.removeLodging(catalog.getLodgings()[2]);
		assertEquals(getColumn(catalog.getLodgings(), "name"),
				getComboContent());

		for (int i = 0; i < catalog.getLodgings().length; i++) {
			Lodging l = catalog.getLodgings()[i];
			l.setName("Changed: " + l.getName());
		}
		spinEventLoop(0); // force Async. efforts
		assertEquals(getColumn(catalog.getLodgings(), "name"),
				getComboContent());

		Lodging l = catalog.getLodgings()[0];
		assertEquals(combo.getItem(0), l.getName());
		l.setName(null);
		assertEquals("", combo.getItem(0));

		while (catalog.getLodgings().length > 0) {
			catalog.removeLodging(catalog.getLodgings()[0]);
			assertEquals(getColumn(catalog.getLodgings(), "name"),
					getComboContent());
		}
	}

	public void test_ROCombo_multipleBindings() {
		Adventure skiAdventure = SampleData.WINTER_HOLIDAY; // for selection

		IObservableList lodgings = BeansObservables.observeList(Realm
				.getDefault(), catalog, "lodgings");
		ViewerSupport.bind(cviewer, lodgings, BeanProperties.value(
				Lodging.class, "name"));

		assertArrayEquals(catalog.getLodgings(), getViewerContent(cviewer)
				.toArray());

		assertEquals(getColumn(catalog.getLodgings(), "name"),
				getComboContent());

		ComboViewer otherViewer = new ComboViewer(getComposite(), SWT.NONE);
		ViewerSupport.bind(otherViewer, lodgings, BeanProperties.value(
				Lodging.class, "name"));

		assertArrayEquals(catalog.getLodgings(), getViewerContent(otherViewer)
				.toArray());

		IObservableValue selection = ViewersObservables
				.observeSingleSelection(cviewer);
		getDbc().bindValue(selection,
				BeansObservables.observeValue(skiAdventure, "defaultLodging"));

		IObservableValue otherSelection = ViewersObservables
				.observeSingleSelection(otherViewer);
		getDbc().bindValue(otherSelection,
				BeansObservables.observeValue(skiAdventure, "defaultLodging"));

		Lodging lodging = catalog.getLodgings()[0];

		cviewer.setSelection(new StructuredSelection(lodging));
		assertEquals(((IStructuredSelection) cviewer.getSelection())
				.getFirstElement(), ((IStructuredSelection) otherViewer
				.getSelection()).getFirstElement());

		catalog.removeLodging(lodging);
		assertEquals(getViewerContent(cviewer), getViewerContent(otherViewer));

	}

	public void test_ROCombo_SWTCCombo() {

		IObservableList list = new WritableList();
		for (int i = 0; i < catalog.getAccounts().length; i++)
			list.add(catalog.getAccounts()[i].getCountry());

		CCombo ccombo = new CCombo(getComposite(), SWT.READ_ONLY
				| SWT.DROP_DOWN);

		getDbc().bindList(SWTObservables.observeItems(ccombo), list);
		assertEquals(Arrays.asList(ccombo.getItems()), list);

		Account account = catalog.getAccounts()[0];

		IObservableValue comboSelection = SWTObservables
				.observeSelection(ccombo);
		getDbc().bindValue(comboSelection,
				BeansObservables.observeValue(account, "country"));

		String selection = (String) list.get(2);
		ccombo.setText(selection); // this should drive the selection
		assertEquals(account.getCountry(), selection);

	}

	public void test_WCombo_SWTCCombo() {

		IObservableList list = new WritableList();
		for (int i = 0; i < catalog.getAccounts().length; i++)
			list.add(catalog.getAccounts()[i].getCountry());

		CCombo ccombo = new CCombo(getComposite(), SWT.READ_ONLY
				| SWT.DROP_DOWN);

		getDbc().bindList(SWTObservables.observeItems(ccombo), list);
		assertEquals(Arrays.asList(ccombo.getItems()), list);

		Account account = catalog.getAccounts()[0];

		IObservableValue comboSelection = SWTObservables
				.observeSelection(ccombo);
		getDbc().bindValue(comboSelection,
				BeansObservables.observeValue(account, "country"));

		String selection = (String) list.get(2);
		ccombo.setText(selection); // this should drive the selection
		assertEquals(account.getCountry(), selection);

		selection = (String) list.get(1);
		account.setCountry(selection);
		assertEquals(selection, ccombo.getItem(ccombo.getSelectionIndex()));
		assertEquals(selection, ccombo.getText());

		selection = "country not in list";
		account.setCountry(selection);
		assertEquals(-1, ccombo.getSelectionIndex());
		assertEquals(selection, ccombo.getText());
	}

	public void test_ROCombo_SWTList() {

		IObservableList list = new WritableList();
		for (int i = 0; i < catalog.getAccounts().length; i++)
			list.add(catalog.getAccounts()[i].getCountry());

		org.eclipse.swt.widgets.List swtlist = new org.eclipse.swt.widgets.List(
				getComposite(), SWT.READ_ONLY | SWT.SINGLE);

		getDbc().bindList(SWTObservables.observeItems(swtlist), list);
		assertEquals(Arrays.asList(swtlist.getItems()), list);

		Account account = catalog.getAccounts()[0];

		IObservableValue listSelection = SWTObservables
				.observeSelection(swtlist);
		getDbc().bindValue(listSelection,
				BeansObservables.observeValue(account, "country"));

		String selection = (String) list.get(2);
		swtlist.select(2); // this should drive the selection
		swtlist.notifyListeners(SWT.Selection, null); // Force notification
		assertEquals(account.getCountry(), selection);

	}

}
