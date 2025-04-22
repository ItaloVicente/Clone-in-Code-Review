package org.eclipse.ui.examples.undo.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.examples.undo.UndoExampleMessages;
import org.eclipse.ui.examples.undo.UndoPlugin;


public class UndoPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	public UndoPreferencePage() {
		super(GRID);
		setPreferenceStore(UndoPlugin.getDefault().getPreferenceStore());
		setDescription(UndoExampleMessages.UndoPreferences_Description);
	}

	@Override
	public void createFieldEditors() {

		addField(
				new IntegerFieldEditor(
					PreferenceConstants.PREF_UNDOLIMIT,
					UndoExampleMessages.UndoPreferences_HistoryLimit,
					getFieldEditorParent()));

		addField(
				new BooleanFieldEditor(
					PreferenceConstants.PREF_SHOWDEBUG,
					UndoExampleMessages.UndoPreferences_ShowDebug,
					getFieldEditorParent()));

		addField(
			new BooleanFieldEditor(
				PreferenceConstants.PREF_CONFIRMUNDO,
				UndoExampleMessages.UndoPreferences_ConfirmUndo,
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}

}
