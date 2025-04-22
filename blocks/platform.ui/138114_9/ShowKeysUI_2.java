package org.eclipse.ui.internal.keys.show;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class ShowKeysToggleHandler extends AbstractHandler {

	public static final String COMMAND_ID = "org.eclipse.ui.toggleShowKeys"; //$NON-NLS-1$
	private ShowKeysUI showKeysUI;

	@Override
	public Object execute(ExecutionEvent event) {
		IPreferenceStore prefStore = WorkbenchPlugin.getDefault().getPreferenceStore();
		boolean newValue = toggleValue(IPreferenceConstants.SHOW_KEYS_ENABLED, prefStore);
		if (newValue) {
			showPreview(event, prefStore);
		}
		return newValue;
	}

	private boolean toggleValue(String key, IPreferenceStore prefStore) {
		boolean newValue = !prefStore.getBoolean(key);
		prefStore.setValue(key, newValue);
		return newValue;
	}

	private void showPreview(ExecutionEvent event, IPreferenceStore prefStore) {
		if (showKeysUI == null) {
			showKeysUI = new ShowKeysUI(HandlerUtil.getActiveWorkbenchWindow(event), prefStore);
		}
		Display.getDefault().asyncExec(() -> showKeysUI.openForPreview(ShowKeysToggleHandler.COMMAND_ID, null));
	}

}
