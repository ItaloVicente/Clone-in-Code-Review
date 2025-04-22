package org.eclipse.egit.ui.internal.preferences;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class RemoteConnectionPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public RemoteConnectionPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		IntegerFieldEditor editor = new IntegerFieldEditor(
				UIPreferences.REMOTE_CONNECTION_TIMEOUT,
				UIText.RemoteConnectionPreferencePage_TimeoutLabel,
				getFieldEditorParent());
		editor.getLabelControl(getFieldEditorParent()).setToolTipText(
				UIText.RemoteConnectionPreferencePage_ZeroValueTooltip);
		addField(editor);
	}

	public void init(IWorkbench workbench) {
	}
}
