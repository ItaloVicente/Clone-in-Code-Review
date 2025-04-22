/*******************************************************************************
 * Copyright (c) 2019 SAP SE and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Christian Georgi (SAP SE) - Bug 540440
 *******************************************************************************/
package org.eclipse.ui.internal.keys.show;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;

/**
 * Toggles whether to show keyboard shortcuts
 */
public class ShowKeysToggleHandler extends AbstractHandler {

	private static ShowKeysUI showKeysUI;

	@Override
	public Object execute(ExecutionEvent event) {
		IPreferenceStore prefStore = WorkbenchPlugin.getDefault().getPreferenceStore();
		boolean newValue = toggleValue(IPreferenceConstants.SHOW_KEYS_ENABLED_FOR_KEYBOARD, prefStore);
		prefStore.setValue(IPreferenceConstants.SHOW_KEYS_ENABLED_FOR_MOUSE_EVENTS, newValue);
		if (newValue) {
			showPreview(prefStore);
		}
		return newValue;
	}

	private boolean toggleValue(String key, IPreferenceStore prefStore) {
		boolean newValue = !prefStore.getBoolean(key);
		prefStore.setValue(key, newValue);
		return newValue;
	}

	private void showPreview(IPreferenceStore prefStore) {
		if (showKeysUI == null) {
			showKeysUI = new ShowKeysUI(PlatformUI.getWorkbench(), prefStore);
		}
		showKeysUI.openForPreview(ShowKeysToggleHandler.COMMAND_ID, null);
	}

}
