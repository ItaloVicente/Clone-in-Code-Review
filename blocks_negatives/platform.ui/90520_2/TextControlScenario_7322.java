/*******************************************************************************
 * Copyright (c) 2005, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Brad Reynolds - bug 116920
 *     Brad Reynolds - bug 160000
 *     Matthew Hall - bug 260337
 *******************************************************************************/
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

/**
 * To run the tests in this class, right-click and select "Run As JUnit Plug-in
 * Test". This will also start an Eclipse instance. To clean up the launch
 * configuration, open up its "Main" tab and select "[No Application] - Headless
 * Mode" as the application to run.
 */

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
	/**
	 * work for table columns Account[] accounts = catalog.getAccounts();
	 * Account firstAccount = accounts[0];
	 * SampleData.getSWTObservableFactory().setUpdateTime
	 * (DataBindingContext.TIME_EARLY); TableViewerDescription
	 * tableViewerDescription = new TableViewerDescription(tableViewer);
	 * tableViewerDescription.addEditableColumn("lastName");
	 * tableViewerDescription.addColumn("lastName");
	 * getDbc().bind(tableViewerDescription,new Property(catalog, "accounts"),
	 * the last name correctly
	 * assertEquals(tableViewer.getTable().getItem(0).getData(),firstAccount);
	 * assertEquals
	 * (tableViewer.getTable().getItem(0).getText(0),firstAccount.getLastName
	 * ());
	 * assertEquals(tableViewer.getTable().getItem(0).getText(1),firstAccount
	 * tableViewer.editElement(firstAccount, 0); // Set the text property of the
	 * cell editor which is now active over the "firstName" column CellEditor[]
	 * cellEditors = tableViewer.getCellEditors(); TextCellEditor
	 * lastNameCellEditor = (TextCellEditor) cellEditors[0];
	 * key press goes to the model assertEquals(firstAccount.getLastName(),E);
	 * }
	 */
}
