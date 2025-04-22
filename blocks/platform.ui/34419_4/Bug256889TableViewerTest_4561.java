package org.eclipse.jface.tests.viewers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class Bug242231Test extends ViewerTestCase {

	public Bug242231Test(String name) {
		super(name);
	}

	protected static final String[] COMBO_ITEMS = new String[] { "default value", "some value", "value changed"};
	private TableViewer tableViewer;

	@Override
	protected StructuredViewer createViewer(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.NONE);
		tableViewer.setContentProvider(new TestModelContentProvider());

		TableViewerColumn tableColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		tableColumn.setEditingSupport(getEditingSupport());
		tableColumn.setLabelProvider(new ColumnLabelProvider());
		return tableViewer;
	}

	private EditingSupport getEditingSupport() {
		return new EditingSupport(tableViewer) {

			@Override
			protected void setValue(Object element, Object value) {
				((TestElement)element).setLabel("value set");
			}

			@Override
			protected Object getValue(Object element) {
				return 0;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new ComboBoxCellEditor(tableViewer.getControl().getParent(), COMBO_ITEMS);
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		};
	}

	public void testBug242231()
	{
		TestElement testElement = fRootElement.getChildAt(0);
		testElement.setLabel("default value");

		tableViewer.editElement(testElement, 0);

		tableViewer.applyEditorValue();

		assertEquals("value set", testElement.getLabel());
	}
}
