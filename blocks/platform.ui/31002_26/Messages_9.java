package org.eclipse.ui.internal.monitoring.preferences;

import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.monitoring.MonitoringPlugin;
import org.eclipse.ui.monitoring.PreferenceConstants;

public class ListFieldEditor extends ListEditor {
	private FilterInputDialog dialog;

	ListFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		super.getAddButton().setText(Messages.ListFieldEditor_add_filter_button_label);
		super.getUpButton().setVisible(false);
		super.getDownButton().setVisible(false);
		dialog = new FilterInputDialog(super.getShell());
		this.setEnabled(MonitoringPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.MONITORING_ENABLED), parent);
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
