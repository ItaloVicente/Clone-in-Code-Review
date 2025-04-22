package org.eclipse.egit.ui.internal.preferences;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class StagingViewPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public StagingViewPreferencePage() {
		super(GRID);
		setTitle(UIText.StagingViewPreferencePage_title);
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	@Override
	protected void createFieldEditors() {
		Composite main = getFieldEditorParent();

		IntegerFieldEditor historySize = new IntegerFieldEditor(
				UIPreferences.STAGING_VIEW_MAX_LIMIT_LIST_MODE,
				UIText.StagingViewPreferencePage_maxLimitListMode, main);
		addField(historySize);
	}


}
