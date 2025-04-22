package org.eclipse.jface.tests.databinding.scenarios;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.examples.databinding.model.Account;
import org.eclipse.jface.examples.databinding.model.Catalog;
import org.eclipse.jface.examples.databinding.model.Category;
import org.eclipse.jface.examples.databinding.model.SampleData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class TableScenarios extends ScenariosTestCase {

	private TableViewer tableViewer;

	private Catalog catalog;

	Category category;

	private TableColumn firstNameColumn;

	private TableColumn lastNameColumn;

	private TableColumn stateColumn;

	Image[] images;

	private TableColumn fancyColumn;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getComposite().setLayout(new FillLayout());
		tableViewer = new TableViewer(getComposite());
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setHeaderVisible(true);
		firstNameColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
		firstNameColumn.setWidth(50);
		lastNameColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
		lastNameColumn.setWidth(50);
		stateColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
		stateColumn.setWidth(50);
		fancyColumn = new TableColumn(tableViewer.getTable(), SWT.NONE);
		fancyColumn.setWidth(250);

		catalog = SampleData.CATALOG_2005; // Lodging source
		category = SampleData.WINTER_CATEGORY;

		images = new Image[] {
				getShell().getDisplay().getSystemImage(SWT.ICON_ERROR),
				getShell().getDisplay().getSystemImage(SWT.ICON_WARNING),
				getShell().getDisplay().getSystemImage(SWT.ICON_INFORMATION), };
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		tableViewer.getTable().dispose();
		tableViewer = null;
		firstNameColumn = null;
		lastNameColumn = null;
		stateColumn = null;
	}

	public void testScenario01() {
		Account[] accounts = catalog.getAccounts();

		IObservableList list = new WritableList(new ArrayList(), Account.class);
		list.addAll(Arrays.asList(accounts));

		ViewerSupport.bind(tableViewer, list, BeanProperties.values(
				Account.class,
				new String[] { "firstName", "lastName", "state" }));

		Table table = tableViewer.getTable();

		for (int i = 0; i < accounts.length; i++) {
			Account account = catalog.getAccounts()[i];
			TableItem item = table.getItem(i);

			assertEquals(account.getFirstName(), item.getText(0));
			assertEquals(account.getLastName(), item.getText(1));
			assertEquals(account.getState(), item.getText(2));
		}
	}

	public void testScenario02() throws SecurityException,
			IllegalArgumentException {
	}

	public void testScenario04() {
	}

	public void testScenario03() {
	}

	public void testScenario05() {
	}

	public void testScenario06() {

	}

	public void testScenario07() {
	}

	public void testScenario08_00() {
	}

	public void testScenario08_01() {
	}

	public void testScenario09() {
	}
}
