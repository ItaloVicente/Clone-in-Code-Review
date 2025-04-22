
package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

public class Bug200337TableViewerTest extends ViewerTestCase {

	public Bug200337TableViewerTest(String name) {
		super(name);
	}

	@Override
	protected StructuredViewer createViewer(Composite parent) {
		final TableViewer tableViewer = new TableViewer(parent, SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setCellEditors(new CellEditor[] { new TextCellEditor(
				tableViewer.getTable()) });
		tableViewer.setColumnProperties(new String[] { "0" });
		tableViewer.setCellModifier(new ICellModifier() {
			@Override
			public boolean canModify(Object element, String property) {
				return true;
			}

			@Override
			public Object getValue(Object element, String property) {
				return "";
			}

			@Override
			public void modify(Object element, String property, Object value) {
			}

		});

	    new TableColumn(tableViewer.getTable(), SWT.NONE).setWidth(200);

		return tableViewer;
	}

	@Override
	protected void setUpModel() {
	}

	@Override
	protected void setInput() {
		String[] ar = new String[100];
		for( int i = 0; i < ar.length; i++ ) {
			ar[i] = i + "";
		}
		getTableViewer().setInput(ar);
	}

	private TableViewer getTableViewer() {
		return (TableViewer) fViewer;
	}

	public void testBug200337() {
		getTableViewer().editElement(getTableViewer().getElementAt(0), 0);
		getTableViewer().editElement(getTableViewer().getElementAt(90), 0);
	}
}
