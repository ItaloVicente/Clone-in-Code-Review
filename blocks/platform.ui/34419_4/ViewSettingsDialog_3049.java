package org.eclipse.ui.preferences;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.internal.WorkbenchMessages;

public abstract class ViewPreferencesAction extends Action {

	public ViewPreferencesAction() {
		super(WorkbenchMessages.OpenPreferences_text); 
	}
	
	@Override
	public void run() {
		openViewPreferencesDialog();
	}

	public abstract void openViewPreferencesDialog();

}
