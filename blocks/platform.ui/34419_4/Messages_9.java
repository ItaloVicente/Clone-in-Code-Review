package org.eclipse.ui.internal.monitoring.preferences;

import java.util.Arrays;

import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

public class FilterListEditor extends ListEditor {
	private String dialogMessage;

	FilterListEditor(String name, String label, String addButtonLabel, String removeButtonLabel,
			String dialogMessage, Composite parent) {
		super(name, label, parent);
		this.dialogMessage = dialogMessage;
		getAddButton().setText(addButtonLabel);
		getRemoveButton().setText(removeButtonLabel);
		getUpButton().setVisible(false);
		getDownButton().setVisible(false);
	}

    @Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
    	super.doFillIntoGrid(parent, numColumns);
        List list = getListControl(parent);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, numColumns - 1, 1);
    	PixelConverter pixelConverter = new PixelConverter(parent);
        gd.widthHint = pixelConverter.convertWidthInCharsToPixels(75);
        list.setLayoutData(gd);
    }

	@Override
	protected String createList(String[] items) {
		StringBuilder mergedItems = new StringBuilder();

		for (String item : items) {
			item.trim();
			if (mergedItems.length() != 0) {
				mergedItems.append(',');
			}
			mergedItems.append(item);
		}

		return mergedItems.toString();
	}

	@Override
	protected String getNewInputObject() {
		FilterInputDialog dialog = new FilterInputDialog(getShell(), dialogMessage);
		if (dialog.open() == Window.OK) {
			String filter = dialog.getFilter();
			List list = getList();
			if (list.getItemCount() != 0) {
				int pos = Arrays.binarySearch(list.getItems(), filter);
				if (pos >= 0) {
					return null;  // Identical item already exists.
				}
				list.setSelection(-pos - 2);
			}
			return filter;
		}
		return null;
	}

	@Override
	protected String[] parseString(String stringList) {
		if (stringList.isEmpty()) {
			return new String[0];
		}
		String[] items = stringList.split(","); //$NON-NLS-1$
		Arrays.sort(items);;
		return items;
	}

	@Override
	protected void refreshValidState() {
		selectionChanged();
	}
}
