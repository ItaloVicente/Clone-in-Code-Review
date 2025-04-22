package org.eclipse.egit.ui.internal.preferences;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class CommitDialogPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public CommitDialogPreferencePage() {
		super(GRID);
		setTitle(UIText.CommitDialogPreferencePage_title);
	}

	public void init(IWorkbench workbench) {
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	@Override
	protected void createFieldEditors() {
		BooleanFieldEditor hardWrap = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_HARD_WRAP_MESSAGE,
				UIText.CommitDialogPreferencePage_hardWrapMessage,
				getFieldEditorParent());
		hardWrap.getDescriptionControl(getFieldEditorParent()).setToolTipText(UIText.CommitDialogPreferencePage_hardWrapMessageTooltip);
		addField(hardWrap);
	}
}
