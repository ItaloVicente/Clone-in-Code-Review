
package org.eclipse.ui.tests.propertyPages;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.dialogs.PropertyPage;

public class TableResizePropertyPage extends PropertyPage {

	public TableResizePropertyPage() {
		super();
	}

	private void addFirstSection(Composite parent) {

		Composite enclosingComposite = new Composite(parent, SWT.NONE);
		enclosingComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		enclosingComposite.setLayout(layout);

		Table table = new Table(enclosingComposite, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.MULTI | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumnLayout tableLayout = new TableColumnLayout();
		
		GridData data = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(data);

		table.setHeaderVisible(true);
		TableColumn column = new TableColumn(table, SWT.NULL);
		column.setText("Column 1");
		tableLayout.setColumnData(column, new ColumnWeightData(50, 100, true));

		column = new TableColumn(table, SWT.NULL);
		column.setText("Column 2");
		
		tableLayout.setColumnData(column,new ColumnWeightData(50, 100, true));
		enclosingComposite.setLayout(tableLayout);

	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addFirstSection(composite);

		return composite;
	}

	@Override
	public boolean performOk() {
		return true;
	}

}
