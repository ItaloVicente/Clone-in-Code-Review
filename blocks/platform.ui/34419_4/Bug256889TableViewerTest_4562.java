package org.eclipse.jface.tests.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILazyContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class Bug256889TableViewerTest extends ViewerTestCase {

	private static final int ADD_ENTRIES = 100;
	private static final int PREFETCH_TRESHOLD = 50;
	private static final int MAX_ENTRIES = 205;

	private int rowcounter = 0;

	private List model = new ArrayList();
	private Table table;
	private TableViewer tableViewer;

	public Bug256889TableViewerTest(String name) {
		super(name);
		initModel();
	}

	@Override
	protected StructuredViewer createViewer(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.VIRTUAL | SWT.BORDER
				| SWT.MULTI);
		tableViewer.setContentProvider(new ILazyContentProvider() {

			@Override
			public void updateElement(int index) {
				if (index >= 0 && index < tableViewer.getTable().getItemCount()) {
					if (index > getModel().size() - PREFETCH_TRESHOLD
							&& (getModel().size() < MAX_ENTRIES)) {
						int approxRecordCount = addElementsToModel();

						System.out.println("approx. record count: "
								+ approxRecordCount);
						tableViewer.setItemCount(approxRecordCount);
					}
					if (index < getModel().size()) {
						tableViewer.replace(getModel().get(index), index);
					} else {
						System.out.println("invalid index " + index
								+ " model count " + getModel().size());
					}
				} else {
					System.out.println("invalid index " + index
							+ " tableItemCount "
							+ tableViewer.getTable().getItemCount());
				}
			}

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
			}
		});

		String[] columnProperties = new String[] { "Spalte 1",
				"Virtual Tables rock" };
		tableViewer.setColumnProperties(columnProperties);

		table = tableViewer.getTable();

		TableColumn col = new TableColumn(table, SWT.NONE);
		col.setText(columnProperties[0]);
		col.setWidth(200);

		col = new TableColumn(table, SWT.NONE);
		col.setText(columnProperties[1]);
		col.setWidth(400);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setItemCount(getModel().size());

		return tableViewer;
	}

	@Override
	protected void setInput() {
		tableViewer.setInput(getModel());
		tableViewer.setItemCount(getModel().size());

		fShell.setSize(300, 1000);
	}

	private void initModel() {
		this.rowcounter = 0;
		getModel().clear();
		addElementsToModel();
	}

	protected int addElementsToModel() {
		int approxRecordCount = 0;
		int itemsToAdd;

		if (getModel().size() + ADD_ENTRIES < MAX_ENTRIES) {
			itemsToAdd = ADD_ENTRIES;
		} else {
			itemsToAdd = MAX_ENTRIES - getModel().size();
		}

		for (int i = 0; i < itemsToAdd; i++) {
			getModel().add("Item " + this.rowcounter++);
		}

		if (getModel().size() == MAX_ENTRIES) {
			approxRecordCount = MAX_ENTRIES;
		} else {
			approxRecordCount = getModel().size() + ADD_ENTRIES;
		}

		return approxRecordCount;
	}

	private List getModel() {
		return this.model;
	}

	public void testBug256889() {
		table.selectAll();
		tableViewer.getSelection();

	}

}
