
package org.eclipse.egit.mylyn.ui.preferences;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.PreferenceConstants;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class MylynWorkflowPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	BooleanFieldEditor booleanField;


	public MylynWorkflowPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Enable workflow enabled commit messages"); //$NON-NLS-1$
	}

	@Override
	public void createFieldEditors() {
		booleanField = new BooleanFieldEditor(PreferenceConstants.FORCE_TASK,
				"Force user to select task when commiting ", //$NON-NLS-1$
				getFieldEditorParent());

		addField(booleanField);

	}

	@Override
	public void init(IWorkbench workbench) {
	}


}
