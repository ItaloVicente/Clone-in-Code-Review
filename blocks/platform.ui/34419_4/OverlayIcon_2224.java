package org.eclipse.ui.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class OpenPreferencesAction extends Action implements ActionFactory.IWorkbenchAction {

	private IWorkbenchWindow workbenchWindow;

	public OpenPreferencesAction() {
		this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

	public OpenPreferencesAction(IWorkbenchWindow window) {
		super(WorkbenchMessages.OpenPreferences_text); 
		if (window == null) {
			throw new IllegalArgumentException();
		}
		this.workbenchWindow = window;
		setToolTipText(WorkbenchMessages.OpenPreferences_toolTip); 
		window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.OPEN_PREFERENCES_ACTION);
	}

	@Override
	public void run() {
		if (workbenchWindow == null) {
			return;
		}
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, null, null, null);
		dialog.open();
	}

	@Override
	public void dispose() {
		workbenchWindow = null;
	}

}
