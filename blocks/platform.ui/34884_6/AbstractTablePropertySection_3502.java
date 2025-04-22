package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.dialogs.HockeyleagueSetDefaultsDialog;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.editor.HockeyleagueEditor;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractTablePropertySection
	extends AbstractHockeyleaguePropertySection {

	protected Table table;

	protected List columns;

	protected Button addButton;

	protected Button removeButton;

	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		Composite composite = getWidgetFactory()
			.createFlatFormComposite(parent);
		FormData data;

		table = getWidgetFactory().createTable(composite,
			SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		List labels = getColumnLabelText();
		columns = new ArrayList();

		for (Iterator i = labels.iterator(); i.hasNext();) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText((String) i.next());
			columns.add(column);
		}

		Shell shell = new Shell();
		GC gc = new GC(shell);
		gc.setFont(shell.getFont());
		Point point = gc.textExtent("");//$NON-NLS-1$
		int buttonHeight = point.y + 11;
		gc.dispose();
		shell.dispose();

		addButton = getWidgetFactory().createButton(composite,
			MessageFormat.format("Add {0}...",//$NON-NLS-1$
				new String[] {getButtonLabelText()}), SWT.PUSH);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.top = new FormAttachment(100, -buttonHeight);
		addButton.setLayoutData(data);
		addButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				EditingDomain editingDomain = ((HockeyleagueEditor) getPart())
					.getEditingDomain();
				AddCommand addCommand = (AddCommand) AddCommand.create(
					editingDomain, eObject, getFeature(), getNewChild());
				HockeyleagueSetDefaultsDialog dialog = new HockeyleagueSetDefaultsDialog(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getShell(), addCommand);
				dialog.open();
				if (dialog.getReturnCode() == Window.CANCEL) {
					return;
				}
				editingDomain.getCommandStack().execute(addCommand);
			}
		});

		removeButton = getWidgetFactory().createButton(composite,
			MessageFormat.format("Delete {0}",//$NON-NLS-1$
				new String[] {getButtonLabelText()}), SWT.PUSH);
		data = new FormData();
		data.left = new FormAttachment(addButton,
			ITabbedPropertyConstants.VSPACE, SWT.BOTTOM);
		data.bottom = new FormAttachment(100, 0);
		data.top = new FormAttachment(100, -buttonHeight);
		removeButton.setLayoutData(data);
		removeButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				EditingDomain editingDomain = ((HockeyleagueEditor) getPart())
					.getEditingDomain();
				Object object = table.getSelection()[0].getData();
				editingDomain.getCommandStack().execute(
					RemoveCommand.create(editingDomain, object));
			}
		});

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		data.bottom = new FormAttachment(addButton,
			-ITabbedPropertyConstants.VSPACE);
		data.width = 400;
		table.setLayoutData(data);

		table.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				removeButton.setEnabled(true);
			}
		});
		table.addMouseListener(new MouseAdapter() {

			public void mouseDoubleClick(MouseEvent e) {
				Object object = table.getSelection()[0].getData();
				propertySheetPage.getEditor().getViewer().setSelection(
					new StructuredSelection(object), true);
				propertySheetPage.getEditor().setFocus();
			}
		});
	}

	public boolean shouldUseExtraSpace() {
		return true;
	}

	public void refresh() {
		table.removeAll();
		removeButton.setEnabled(false);

		for (Iterator i = getOwnedRows().iterator(); i.hasNext();) {
			Object next = i.next();
			String key = getKeyForRow(next);

			int k = 0;
			int size = table.getItemCount();
			while (k < size) {
				String currentKey = table.getItem(k).getText();
				if (key.compareToIgnoreCase(currentKey) < 0) {
					break;
				}
				k++;
			}

			TableItem item = new TableItem(table, SWT.NONE, k);
			String[] values = new String[columns.size()];
			List valuesForRow = getValuesForRow(next);
			for (int j = 0; j < columns.size(); j++) {
				values[j] = (String) valuesForRow.get(j);
			}
			item.setText(values);
			item.setData(next);
		}

		for (Iterator i = columns.iterator(); i.hasNext();) {
			((TableColumn) i.next()).pack();
		}
	}

	protected abstract String getButtonLabelText();

	protected abstract List getOwnedRows();

	protected abstract EReference getFeature();

	protected abstract String getKeyForRow(Object object);

	protected abstract List getValuesForRow(Object object);

	protected abstract List getColumnLabelText();

	protected abstract Object getNewChild();

}
