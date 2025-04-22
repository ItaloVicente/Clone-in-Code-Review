package org.eclipse.ui.internal.monitoring.preferences;

import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;

public class ListFieldEditor extends ListEditor {
	private FilterInputDialog dialog;

	ListFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		super.getUpButton().setVisible(false);
		super.getDownButton().setVisible(false);
		dialog = new FilterInputDialog(super.getShell());
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
		dialog.open();
		String input = dialog.getInput();
		if (input != null && !input.isEmpty() && !input.contains(",")) { //$NON-NLS-1$
			input.trim();
			return dialog.getInput();
		}
		return null;
	}

	@Override
	protected String[] parseString(String stringList) {
		return stringList.split(","); //$NON-NLS-1$
	}
}
