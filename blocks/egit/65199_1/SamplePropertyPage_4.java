package org.eclipse.egit.mylyn.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.eclipse.egit.internal.mylyn.ui.EGitMylynUI;
import org.eclipse.egit.ui.PreferenceConstants;


public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = EGitMylynUI.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.FORCE_TASK, false);
	}

}
